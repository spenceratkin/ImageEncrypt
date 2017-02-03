package com.atkin_labs.imageencrypt;

import java.io.Serializable;

/**
 * Created by Spencer on 1/27/17.
 */

public class Contact implements Serializable {
    private String name;
    private byte[] publicKey;

    public Contact(String name, byte[] publicKey) {
        this.name = name;
        this.publicKey = publicKey;
    }

    public String getContactName() {
        return name;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }
}
