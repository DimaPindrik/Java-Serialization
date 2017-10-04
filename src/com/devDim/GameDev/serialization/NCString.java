package com.devDim.GameDev.serialization;

import static com.devDim.GameDev.serialization.SerializationReader.*;
import static com.devDim.GameDev.serialization.SerializationWriter.writeBytes;

public class NCString extends NCBase {

    public static final byte CONTAINER_TYPE = ContainerType.STRING;
    public int count;
    public char[] characters;

    private NCString() {
        size += 1 + 4;
    }

    public String getString() {
        return new String(characters);
    }

    public void updateSize() {
        size += getArraySize();
    }

    public int getArraySize() {
        return count *  Type.getSize(Type.CHAR);
    }

    public int setBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);
        pointer = writeBytes(dest, pointer, count);
        pointer = writeBytes(dest, pointer, characters);

        return pointer;
    }

    public static NCString String(String name, String data) {
        NCString string = new NCString();
        string.setName(name);
        string.count = data.length();
        string.characters = data.toCharArray();
        string.updateSize();
        return string;
    }

    public static NCString Deserialize(byte[] data, int pointer) {
        // Container Type - 3
        byte containerType = data[pointer++];
        assert (containerType == CONTAINER_TYPE);

        NCString result = new NCString();
        // Name length & Name
        result.nameLength = readShort(data, pointer);
        pointer += Type.getSize(Type.SHORT);
        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;
        // Size
        result.size = readInt(data, pointer);
        pointer += Type.getSize(Type.INTEGER);
        // Number of characters
        result.count = readInt(data, pointer);
        pointer += Type.getSize(Type.INTEGER);
        // Read data (characters)
        result.characters = new char[result.count];
        readChars(data, pointer, result.characters);

        pointer += result.count * Type.getSize(Type.CHAR);
        return result;
    }
}
