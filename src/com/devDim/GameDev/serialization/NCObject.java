package com.devDim.GameDev.serialization;

import java.util.ArrayList;
import java.util.List;

import static com.devDim.GameDev.serialization.SerializationReader.*;
import static com.devDim.GameDev.serialization.SerializationWriter.writeBytes;

public class NCObject extends NCBase{

    public static final byte CONTAINER_TYPE = ContainerType.OBJECT;

    private short fieldCount;
    public List<NCField> fields = new ArrayList<NCField>();
    private short stringCount;
    public List<NCString> strings = new ArrayList<NCString>();
    private short arrayCount;
    public List<NCArray> arrays = new ArrayList<NCArray>();

    private NCObject() {
    }

    public NCObject(String name) {
        size += 1 + 2 + 2 + 2;
        setName(name);
    }

    public int getSize() {
        return size;
    }

    public void addField(NCField field) {
        fields.add(field);
        size += field.getSize();
        fieldCount = (short) fields.size();
    }

    public void addArray(NCArray array) {
        arrays.add(array);
        size += array.getSize();
        arrayCount = (short) arrays.size();
    }

    public void addString(NCString string) {
        strings.add(string);
        size += string.getSize();
        stringCount = (short) strings.size();
    }

    public int setBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);

        pointer = writeBytes(dest, pointer, fieldCount);
        for (NCField field : fields)
            pointer = field.setBytes(dest, pointer);

        pointer = writeBytes(dest, pointer, stringCount);
        for (NCString string : strings)
            pointer = string.setBytes(dest, pointer);

        pointer = writeBytes(dest, pointer, arrayCount);
        for (NCArray array : arrays)
            pointer = array.setBytes(dest, pointer);

        return pointer;
    }


    public NCField findField(String name) {
        for (NCField field : fields) {
            if( field.getName().equals(name))
                return field;
        }
        System.err.println("ERROR: Field \"" + name + "\" not found");
        return null;
    }

    public NCArray findArray(String name) {
        for (NCArray array : arrays) {
            if( array.getName().equals(name))
                return array;
        }
        System.err.println("ERROR: Field \"" + name + "\" not found");
        return null;
    }

    public NCString findString(String name) {
        for (NCString string : strings) {
            if( string.getName().equals(name))
                return string;
        }
        System.err.println("ERROR: Field \"" + name + "\" not found");
        return null;
    }

    public static NCObject Deserialize(byte[] data, int pointer) {

        byte containerType = data[pointer++];
        assert (containerType == CONTAINER_TYPE);

        NCObject result = new NCObject();
        result.nameLength = readShort(data, pointer);
        pointer += Type.getSize(Type.SHORT);
        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;
        result.size = readInt(data, pointer);
        pointer += Type.getSize(Type.INTEGER);

        // fields
        result.fieldCount = readShort(data, pointer);
        pointer += Type.getSize(Type.SHORT);
        for (int i = 0; i < result.fieldCount; i++) {
            NCField field = NCField.Deserialize(data, pointer);
            result.fields.add(field);
            pointer += field.getSize();
        }
        // strings
        result.stringCount = readShort(data, pointer);
        pointer += Type.getSize(Type.SHORT);
        for (int i = 0; i < result.stringCount; i++) {
            NCString string = NCString.Deserialize(data, pointer);
            result.strings.add(string);
            pointer += string.size;
        }
        // arrays
        result.arrayCount = readShort(data, pointer);
        pointer += Type.SHORT;
        for (int i = 0; i < result.arrayCount; i++) {
            NCArray array = NCArray.Deserialize(data, pointer);
            result.arrays.add(array);
            pointer += array.size;
        }

        return result;
    }

}
