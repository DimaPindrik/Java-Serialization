package com.devDim.GameDev.serialization;

public abstract class NCBase {

    protected short nameLength;
    protected byte[] name;

    protected int size = 2 + 4;

    public void setName(String name) {
        if (this.name != null) // remove the previous name length
            size -= this.name.length;
        nameLength = (short) name.length();
        this.name = name.getBytes();
        size += nameLength; // add the new name length
    }

    public String getName() {
        return new String(name, 0, nameLength);
    }

    public int getSize() {
        return size;
    }

}
