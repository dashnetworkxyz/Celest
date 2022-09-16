/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest;

import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.utils.AddressData;

import java.util.ArrayList;
import java.util.List;

public class Address {

    // TODO
    // Store in ram? -> Cache's job
    // Add and remove uuids from addresses on join

    private static final List<Address> addresses = new ArrayList<>();
    private final String address;
    private AddressData addressData;
    private long accessTime; // TODO: Remove Addresses from RAM after some amount of time.

    public Address(String address) {
        this.address = address;

        load();

        accessTime = System.currentTimeMillis();
    }

    public static Address getAddress(String address) {
        for (Address each : addresses)
            if (each.address.equals(address)) {
                each.accessTime = System.currentTimeMillis();
                return each;
            }
        return new Address(address);
    }

    private void load() {
        addressData = Storage.read(address, Storage.Directory.ADDRESSDATA, AddressData.class);

        if (addressData == null)
            addressData = new AddressData();

        // TODO: Check for and remove old address entries
    }

    public void save() { Storage.write(address, Storage.Directory.ADDRESSDATA, addressData); }

    public void remove() { addresses.remove(this); }

    public AddressData getAddressData() { return addressData; }

    public long getAccessTime() { return accessTime; }

}
