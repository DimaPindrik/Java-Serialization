package com.devDim.GameDev.serialization;

import static com.devDim.GameDev.serialization.SerializationReader.*;
import static com.devDim.GameDev.serialization.SerializationWriter.writeBytes;

public class NCArray extends NCBase {

    public static final byte CONTAINER_TYPE = ContainerType.ARRAY;
    public byte type;
    public int count;
    public byte[] data;

    private short[] sdata;
    private char[] cdata;
    private int[] idata;
    private long[] ldata;
    private float[] fdata;
    private double[] ddata;
    private boolean[] boodata;

    private NCArray() {
        size += 1 + 1 + 4;
    }

    public int getArraySize() {
        return count *  Type.getSize(type);
    }

    public void updateSize() {
        size += getArraySize();
    }


    public int setBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);
        pointer = writeBytes(dest, pointer, type);
        pointer = writeBytes(dest, pointer, count);
        switch (type) {
            case Type.BYTE : return writeBytes(dest, pointer, data);
            case Type.SHORT : return writeBytes(dest, pointer, sdata);
            case Type.CHAR : return writeBytes(dest, pointer, cdata);
            case Type.INTEGER : return writeBytes(dest, pointer, idata);
            case Type.LONG : return writeBytes(dest, pointer, ldata);
            case Type.FLOAT : return writeBytes(dest, pointer, fdata);
            case Type.DOUBLE : return writeBytes(dest, pointer, ddata);
            case Type.BOOLEAN : return writeBytes(dest, pointer, boodata);
        }
        return pointer;
    }

    //*******************************//
    //********* ARRAY TYPES *********//
    //*******************************//

    public static NCArray Byte(String name, byte[] data) {
        NCArray array = new NCArray();
        array.setName(name);
        array.type = Type.BYTE;
        array.count = data.length;
        array.data = data;
        array.updateSize();

        return array;
    }
    public static NCArray Short(String name, short[] data) {
        NCArray array = new NCArray();
        array.setName(name);
        array.type = Type.SHORT;
        array.count = data.length;
        array.sdata = data;
        array.updateSize();
        return array;
    }
    public static NCArray Char(String name, char[] data) {
        NCArray array = new NCArray();
        array.setName(name);
        array.type = Type.CHAR;
        array.count = data.length;
        array.cdata = data;
        array.updateSize();
        return array;
    }
    public static NCArray Integer(String name, int[] data) {
        NCArray array = new NCArray();
        array.setName(name);
        array.type = Type.INTEGER;
        array.count = data.length;
        array.idata = data;
        array.updateSize();
        return array;
    }
    public static NCArray Float(String name, float[] data) {
        NCArray array = new NCArray();
        array.setName(name);
        array.type = Type.FLOAT;
        array.count = data.length;
        array.fdata = data;
        array.updateSize();
        return array;
    }
    public static NCArray Long(String name, long[] data) {
        NCArray array = new NCArray();
        array.setName(name);
        array.type = Type.LONG;
        array.count = data.length;
        array.ldata = data;
        array.updateSize();
        return array;
    }
    public static NCArray Double(String name, double[] data) {
        NCArray array = new NCArray();
        array.setName(name);
        array.type = Type.DOUBLE;
        array.count = data.length;
        array.ddata = data;
        array.updateSize();
        return array;
    }
    public static NCArray Boolean(String name, boolean[] data) {
        NCArray array = new NCArray();
        array.setName(name);
        array.type = Type.BOOLEAN;
        array.count = data.length;
        array.boodata = data;
        array.updateSize();
        return array;
    }


    public static NCArray Deserialize(byte[] data, int pointer) {
        byte  containerType = data[pointer++];
        assert (containerType == CONTAINER_TYPE);

        NCArray result = new NCArray();
        result.nameLength = readShort(data, pointer);
        pointer += Type.getSize(Type.SHORT);
        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.size = readInt(data, pointer);
        pointer += Type.getSize(Type.INTEGER);

        result.type = data[pointer++];

        result.count = readInt(data, pointer);
        pointer += Type.getSize(Type.INTEGER);

        switch (result.type) {
            case Type.BYTE :
                result.data = new byte[result.count];
                readBytes(data, pointer, result.data);
                break;
            case Type.SHORT :
                result.sdata = new short[result.count];
                readShorts(data, pointer, result.sdata);
                break;
            case Type.CHAR :
                result.cdata = new char[result.count];
                readChars(data, pointer, result.cdata);
                break;
            case Type.INTEGER :
                result.idata = new int[result.count];
                readIntegers(data, pointer, result.idata);
                break;
            case Type.LONG :
                result.ldata = new long[result.count];
                readLongs(data, pointer, result.ldata);
                break;
            case Type.FLOAT :
                result.fdata = new float[result.count];
                readFloats(data, pointer, result.fdata);
                break;
            case Type.DOUBLE :
                result.ddata = new double[result.count];
                readDoubles(data, pointer, result.ddata);
                break;
            case Type.BOOLEAN :
                result.boodata = new boolean[result.count];
                readBooleans(data, pointer, result.boodata);
                break;
        }

        pointer += result.count * Type.getSize(result.type);

        return result;
    }

}
