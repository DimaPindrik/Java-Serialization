package com.devDim.GameDev.serialization;

import static com.devDim.GameDev.serialization.SerializationReader.*;
import static com.devDim.GameDev.serialization.SerializationWriter.writeBytes;

public class NCField extends NCBase {

    public static final byte CONTAINER_TYPE = ContainerType.FIELD;
    public byte type;
    public byte[] data;

    private NCField() {
    }

    public int getSize() {
        return Type.getSize(Type.BYTE) + Type.getSize(Type.SHORT)
                + name.length + Type.getSize(Type.BYTE) + data.length;
    }

    /**
     * Template function which will return the value according to the type of the field.
     * @param <T>
     * @return
     */
    public <T> T value() {
        switch (this.type) {
            case Type.BYTE:
                return (T)(Byte)readByte(data, 0);
            case Type.CHAR:
                return (T)(Character)readChar(data, 0);
            case Type.SHORT:
                return (T)(Short)readShort(data, 0);
            case Type.INTEGER:
                return (T)(Integer)readInt(data, 0);
            case Type.LONG:
                return (T)(Long)readLong(data, 0);
            case Type.FLOAT:
                return (T)(Float)readFloat(data, 0);
            case Type.DOUBLE:
                return (T)(Double)readDouble(data, 0);
            case Type.BOOLEAN:
                return (T)(Boolean)readBoolean(data, 0);
        }
        System.err.println("ERROR: Undefined type");
        return null;
    }

    /**
     * using the SerializationWriter class implementations writes the NCField members ordered as the
     * format of this class.
     * @param dest - destination array into which the function writes.
     * @param pointer - index poiner to the array.
     * @return a pointer to the last element inserted to the array.
     */
    public int setBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        //pointer = writeBytes(dest, pointer, size);
        pointer = writeBytes(dest, pointer, type);
        pointer = writeBytes(dest, pointer, data);
        return pointer;
    }
        //*******************************//
      //********* FIELD TYPES *********//
    //*******************************//

    public static NCField Byte(String name, byte value) {
        NCField field = new NCField();
        field.setName(name);
        field.type = Type.BYTE;
        field.data = new byte[Type.getSize(Type.BYTE)];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static NCField Short(String name, short value) {
        NCField field = new NCField();
        field.setName(name);
        field.type = Type.SHORT;
        field.data = new byte[Type.getSize(Type.SHORT)];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static NCField Char(String name, char value) {
        NCField field = new NCField();
        field.setName(name);
        field.type = Type.CHAR;
        field.data = new byte[Type.getSize(Type.CHAR)];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static NCField Integer(String name, int value) {
        NCField field = new NCField();
        field.setName(name);
        field.type = Type.INTEGER;
        field.data = new byte[Type.getSize(Type.INTEGER)];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static NCField Long(String name, long value) {
        NCField field = new NCField();
        field.setName(name);
        field.type = Type.LONG;
        field.data = new byte[Type.getSize(Type.LONG)];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static NCField Float(String name, float value) {
        NCField field = new NCField();
        field.setName(name);
        field.type = Type.FLOAT;
        field.data = new byte[Type.getSize(Type.FLOAT)];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static NCField Double(String name, double value) {
        NCField field = new NCField();
        field.setName(name);
        field.type = Type.DOUBLE;
        field.data = new byte[Type.getSize(Type.DOUBLE)];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static NCField Boolean(String name, boolean value) {
        NCField field = new NCField();
        field.setName(name);
        field.type = Type.BOOLEAN;
        field.data = new byte[Type.getSize(Type.BOOLEAN)];
        writeBytes(field.data, 0, value);
        return field;
    }


    public static NCField Deserialize(byte[] data, int pointer) {
        byte containerType = data[pointer++];
        assert (containerType == CONTAINER_TYPE);

        NCField result = new NCField();
        result.nameLength = readShort(data, pointer);
        pointer += Type.SHORT;
        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;
        result.type = data[pointer++];

        result.data = new byte[Type.getSize(result.type)];
        readBytes(data, pointer, result.data);
        pointer += Type.getSize(result.type);
        return result;
    }

}
