package com.devDim.GameDev.serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.devDim.GameDev.serialization.SerializationReader.*;
import static com.devDim.GameDev.serialization.SerializationWriter.writeBytes;

public class NCDatabase extends NCBase {

    public static final byte[] HEADER = "NCDB".getBytes();
    public static final short VERSION = 0x0100;
    public static final byte CONTAINER_TYPE = ContainerType.DATABASE;

    public short objectCount;
    public List<NCObject> objects = new ArrayList<NCObject>();

    private NCDatabase() { }

    public NCDatabase(String name) {
        size += HEADER.length + 2 + 1 + 2;
        setName(name);
    }

    public int getSize() {
        return size;
    }

    public void addObject(NCObject object) {
        objects.add(object);
        size += object.size;
        objectCount = (short) objects.size();
    }


    public int setBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, HEADER);
        pointer = writeBytes(dest, pointer, VERSION);
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);

        pointer = writeBytes(dest, pointer, objectCount);
        for (NCObject object : objects)
            pointer = object.setBytes(dest, pointer);

        return pointer;
    }

    public static NCDatabase Deserialize(byte[] data) {
        int pointer = 0;
        assert (readString(data, pointer, HEADER.length).equals(HEADER));
        pointer += HEADER.length;

        if (readShort(data, pointer) != VERSION) {
            System.err.println("Invalid NCDB version.");
            return null;
        }
        pointer += Type.getSize(Type.SHORT);

        byte containerType = readByte(data, pointer++);

        NCDatabase result = new NCDatabase();

        result.nameLength = readShort(data, pointer);
        pointer += Type.getSize(Type.SHORT);

        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.size = readInt(data, pointer);
        pointer += Type.getSize(Type.INTEGER);

        result.objectCount = readShort(data, pointer);
        pointer += Type.getSize(Type.SHORT);

        // Deserialize objects here
        for (int i = 0; i < result.objectCount; i++) {
            NCObject object = NCObject.Deserialize(data, pointer);
            result.objects.add(object);
            pointer += object.size;
        }
        return result;
    }

    public NCObject findObject(String name) {
        for (NCObject object : objects) {
            if( object.getName().equals(name))
                return object;
        }
        System.err.println("ERROR: Object \"" + name + "\" not found");
        return null;
    }

    public static NCDatabase DeserializeFromFile(String path) {
        byte[] buffer = null;
        System.out.println("Reading from file \""+ path +"\"...");
        System.out.println("--------------------------------------------------");
        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path));
            buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Deserialize(buffer);
    }

    public void serializeToFile(String path) {
        byte[] data = new byte[getSize()];
        setBytes(data, 0);
        System.out.println("Writing to file \""+ path +"\"...");
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
            stream.write(data);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
