package com.devDim.GameDev.serialization;

import java.nio.ByteBuffer;

public class SerializationReader {

    /**
     * Receives a byte array and returns a byte.
     * Example : src =  { 0x27 }  =>  0x27  => 0x27 (HEX)
     * @param src - array of bytes
     * @param pointer - index of src array
     * @return bye representation of the hex decimal number received from the operation.
     */
    public static byte readByte(byte[] src, int pointer) {
        return (byte) src[pointer];
    }

    /**
     * Receives the original src byte array from the file and copies the data to the new dest byte array
     * while incrementing the pointer index.
     * @param src - original array of bytes.
     * @param pointer - index of array.
     * @param dest - byte array of which the bytes from src will be copied to.
     */
    public static void readBytes(byte[] src, int pointer, byte[] dest) {
        for (int i = 0; i < dest.length; i++)
            dest[i] = src[pointer + i];
    }
    public static void readShorts(byte[] src, int pointer, short[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readShort(src, pointer);
            pointer += Type.getSize(Type.SHORT);
        }
    }
    public static void readIntegers(byte[] src, int pointer, int[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readInt(src, pointer);
            pointer += Type.getSize(Type.INTEGER);
        }
    }
    public static void readChars(byte[] src, int pointer, char[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readChar(src, pointer);
            pointer += Type.getSize(Type.CHAR);
        }
    }
    public static void readLongs(byte[] src, int pointer, long[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readLong(src, pointer);
            pointer += Type.getSize(Type.LONG);
        }
    }
    public static void readFloats(byte[] src, int pointer, float[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readFloat(src, pointer);
            pointer += Type.getSize(Type.FLOAT);
        }
    }
    public static void readDoubles(byte[] src, int pointer, double[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readDouble(src, pointer);
            pointer += Type.getSize(Type.DOUBLE);
        }
    }
    public static void readBooleans(byte[] src, int pointer, boolean[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readBoolean(src, pointer);
            pointer += Type.getSize(Type.BOOLEAN);
        }
    }

    /**
     * Receives array of bytes (separated) and combining them into using shift bitwise operations and OR
     * logic into one HEX decimal number which will be cast to an Short.
     * Example : src =  { 0x27, 0x10 }  =>  0x27 | 0x10 => 0x2710 (HEX)
     * @param src - array of bytes
     * @param pointer - index of src array
     * @return short representation of the hex decimal number received from the operation.
     */
    public static short readShort(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, 2).getShort();
        //return (short) ((src[pointer] << 8) | src[pointer + 1]);
    }

    /**
     * Receives array of bytes (separated) and combining them into using shift bitwise operations and OR
     * logic into one HEX decimal number which will be cast to an Char.
     * Example : src =  { 0x27, 0x10 }  =>  0x27 | 0x10 => 0x2710 (HEX)
     * @param src - array of bytes
     * @param pointer - index of src array
     * @return char representation of the hex decimal number received from the operation.
     */
    public static char readChar(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, 2).getChar();
        //return (char) ((src[pointer] << 8) | src[pointer + 1]);
    }

    /**
     * Receives array of bytes (separated) and combining them into using shift bitwise operations and OR
     * logic into one HEX decimal number which will be cast to an Integer.
     * Example : src =  { 0x0, 0x0, 0x27, 0x10 }  => 0x00 | 0x00 | 0x27 | 0x10 => 0x2710 (HEX) => 10,000 (INT)
     * @param src - array of bytes
     * @param pointer - index of src array
     * @return int representation of the hex decimal number received from the operation.
     */
    public static int readInt(byte[] src, int pointer) {
        //return ByteBuffer.wrap(src, pointer, 4).getInt();
        return (int) (src[pointer] & 0xff) << 24
                | (src[pointer + 1] & 0xff) << 16
                | (src[pointer + 2] & 0xff) << 8
                |  src[pointer + 3] & 0xff << 0;
    }

    /**
     * Receives array of bytes (separated) and combining them into using shift bitwise operations and OR
     * logic into one HEX decimal number which will be cast to an Long.
     * Example : src =  { 0x1,0x2,0x3,0x4,0x5,0x6,0x7,0x8}  => 0x0|0x1|0x2|0x3|0x4...
     * @param src - array of bytes
     * @param pointer - index of src array
     * @return long representation of the hex decimal number received from the operation.
     */
    public static long readLong(byte[] src, int pointer) {
        return (long) (src[pointer] & 0xffL) << 56
                | (src[pointer + 1] & 0xffL) << 48
                | (src[pointer + 2] & 0xffL) << 40
                | (src[pointer + 3] & 0xffL) << 32
                | (src[pointer + 4] & 0xffL) << 24
                | (src[pointer + 5] & 0xffL) << 16
                | (src[pointer + 6] & 0xffL) << 8
                |  src[pointer + 7] & 0xffL << 0;
    }

    public static float readFloat(byte[] src, int pointer) {
       return Float.intBitsToFloat(readInt(src, pointer));
    }

    public static double readDouble(byte[] src, int pointer) {
        return Double.longBitsToDouble(readLong(src, pointer));
    }

    public static boolean readBoolean(byte[] src, int pointer) {
        assert (src[pointer] == 0 | src[pointer] == 1);
        return src[pointer] != 0;
    }

    public static String readString(byte[] src, int pointer, int length) {
        return new String(src, pointer, length);
    }
}
