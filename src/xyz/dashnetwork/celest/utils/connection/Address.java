/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.connection;

import xyz.dashnetwork.celest.utils.ArrayUtils;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.PunishUtils;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.limbo.Savable;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.AddressData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Address implements Savable {

    private final String address;
    private AddressData addressData;
    private long serverPingTime;

    private Address(String address, boolean limbo) {
        this.address = address;

        addressData = Storage.read(address, Storage.Directory.ADDRESS, AddressData.class);

        if (addressData == null)
            addressData = new AddressData();

        serverPingTime = -1;

        if (limbo)
            new Limbo<>(this);
    }

    public static Address getAddress(String name, boolean shouldLimbo) {
        for (User user : User.getUsers()) {
            Address address = user.getAddress();

            if (address.address.equals(name))
                return address;
        }

        Limbo<Address> limbo = Limbo.get(Address.class, each -> each.address.equals(name));

        if (limbo != null) {
            if (shouldLimbo)
                limbo.reset();
            else
                limbo.cancel();

            return limbo.getObject();
        }

        return new Address(name, shouldLimbo);
    }

    public void removeUserIfPresent(UUID uuid) {
        List<PlayerProfile> list = new ArrayList<>();
        PlayerProfile[] profiles = addressData.getProfiles();

        for (PlayerProfile profile : profiles)
            if (!profile.getUuid().equals(uuid))
                list.add(profile);

        System.out.println("Remove: " + ListUtils.convertToString(list, PlayerProfile::getUsername, ", "));

        addressData.setProfiles(list.toArray(PlayerProfile[]::new));
    }

    public void addUserIfNotPresent(UUID uuid, String username) {
        PlayerProfile[] profiles = addressData.getProfiles();

        for (PlayerProfile profile : profiles)
            if (profile.getUuid().equals(uuid))
                return;

        addressData.setProfiles(ArrayUtils.add(profiles, new PlayerProfile(uuid, username)));
    }

    @Override
    public void save() {
        if (addressData.getProfiles().length > 0 || LazyUtils.anyTrue(PunishUtils::isValid, addressData.getMute(), addressData.getBan()))
            Storage.write(address, Storage.Directory.ADDRESS, addressData);
        else
            Storage.delete(address, Storage.Directory.ADDRESS); // Remove obsolete addresses.
    }

    public String getString() { return address; }

    public void setData(AddressData addressData) { this.addressData = addressData; }

    public AddressData getData() { return addressData; }

    public void setServerPingTime(long serverPingTime) { this.serverPingTime = serverPingTime; }

    public long getServerPingTime() { return serverPingTime; }

}