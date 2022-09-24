/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest;

import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.utils.AddressData;
import xyz.dashnetwork.celest.utils.ArrayUtils;
import xyz.dashnetwork.celest.utils.PlayerProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Address {

    private static final List<Address> addresses = new ArrayList<>();
    private final String address;
    private final boolean manual;
    private AddressData addressData;
    private long accessTime;

    public Address(String address, boolean manual) {
        this.address = address;
        this.manual = manual;

        addressData = Storage.read(address, Storage.Directory.ADDRESSDATA, AddressData.class);

        if (addressData == null)
            addressData = new AddressData();

        accessTime = System.currentTimeMillis();
        addresses.add(this);
    }

    public static List<Address> getAddresses() { return addresses; }

    public static Address getAddress(String address) {
        for (Address each : addresses)
            if (each.address.equals(address)) {
                each.accessTime = System.currentTimeMillis();
                return each;
            }
        return new Address(address, false);
    }

    public static void removeOldEntries() {
        addresses.removeIf(address -> !address.manual &&
                        System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1) >= address.getAccessTime());
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
        if (addressData.getProfiles().length > 0 || addressData.getMute() != null || addressData.getBan() != null)
            Storage.write(address, Storage.Directory.ADDRESSDATA, addressData);
        else
            Storage.delete(address, Storage.Directory.ADDRESSDATA); // Remove obsolete addresses.
    }

    public void remove() { addresses.remove(this); }

    public AddressData getAddressData() { return addressData; }

    public long getAccessTime() { return accessTime; }

}
