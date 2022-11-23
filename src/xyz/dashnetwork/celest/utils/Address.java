/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import xyz.dashnetwork.celest.utils.data.AddressData;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Address {

    private static final List<Address> addresses = new CopyOnWriteArrayList<>();
    private final String address;
    private boolean manual;
    private AddressData addressData;
    private String inputServerAddress;
    private int inputServerPort;
    private long serverPingTime, accessTime;

    private Address(String address) {
        this.address = address;
        this.manual = false;

        addressData = Storage.read(address, Storage.Directory.ADDRESS, AddressData.class);

        if (addressData == null)
            addressData = new AddressData();

        inputServerAddress = null;
        inputServerPort = -1;
        serverPingTime = -1;
        accessTime = System.currentTimeMillis();
        addresses.add(this);
    }

    public static List<Address> getAddresses() { return addresses; }

    public static Address getAddress(String address) {
        for (Address each : addresses) {
            if (each.address.equals(address)) {
                each.accessTime = System.currentTimeMillis();
                return each;
            }
        }
        return new Address(address);
    }

    public static void removeOldEntries() {
        for (Address address : addresses) {
            if (!address.manual && TimeUtils.isRecent(address.getAccessTime(), TimeType.MINUTE.toMillis(5))) {
                addresses.remove(address);

                address.save();
            }
        }
    }

    public void removeUserIfPresent(UUID uuid) {
        List<PlayerProfile> queue = new ArrayList<>();
        PlayerProfile[] profiles = addressData.getProfiles();

        for (PlayerProfile profile : profiles)
            if (profile.getUuid().equals(uuid))
                queue.add(profile);

        if (!queue.isEmpty())
            addressData.setProfiles(ArrayUtils.removeAll(PlayerProfile[]::new, profiles, queue));
    }

    public void addUserIfNotPresent(UUID uuid, String username) {
        PlayerProfile[] profiles = addressData.getProfiles();

        for (PlayerProfile profile : profiles)
            if (profile.getUuid().equals(uuid))
                return;

        addressData.setProfiles(ArrayUtils.add(profiles, new PlayerProfile(uuid, username)));
    }

    public void save() {
        if (addressData.getProfiles().length > 0 || LazyUtils.anyTrue(PunishUtils::isValid, addressData.getMute(), addressData.getBan()))
            Storage.write(address, Storage.Directory.ADDRESS, addressData);
        else
            Storage.delete(address, Storage.Directory.ADDRESS); // Remove obsolete addresses.
    }

    public boolean isManual() { return manual; }

    public void setManual(boolean manual) { this.manual = manual; }

    public void setData(AddressData addressData) { this.addressData = addressData; }

    public AddressData getData() { return addressData; }

    public void setInputServerAddress(String inputServerAddress) { this.inputServerAddress = inputServerAddress; }

    public String getInputServerAddress() { return inputServerAddress; }

    public void setInputServerPort(int inputServerPort) { this.inputServerPort = inputServerPort; }

    public int getInputServerPort() { return inputServerPort; }

    public void setServerPingTime(long serverPingTime) { this.serverPingTime = serverPingTime; }

    public long getServerPingTime() { return serverPingTime; }

    public long getAccessTime() { return accessTime; }

}
