package com.devDim.GameDev.serialization;

public class SerializationWriter {

    public static int writeBytes(byte[] dest, int pointer, byte[] src) {
        for (int i = 0; i < src.length; i++)
            dest[pointer++] = src[i];
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, int[] src) {
        for (int i = 0; i < src.length; i++)
            pointer = writeBytes(dest, pointer, src[i]);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, short[] src) {
        for (int i = 0; i < src.length; i++)
            pointer = writeBytes(dest, pointer, src[i]);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, char[] src) {
        for (int i = 0; i < src.length; i++)
            pointer = writeBytes(dest, pointer, src[i]);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, long[] src) {
        for (int i = 0; i < src.length; i++)
            pointer = writeBytes(dest, pointer, src[i]);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, double[] src) {
        for (int i = 0; i < src.length; i++)
            pointer = writeBytes(dest, pointer, src[i]);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, float[] src) {
        for (int i = 0; i < src.length; i++)
            pointer = writeBytes(dest, pointer, src[i]);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, boolean[] src) {
        for (int i = 0; i < src.length; i++)
            pointer = writeBytes(dest, pointer, src[i]);
        return pointer;
    }

        //**************************************************//
      //******************* PRIMITIVES *******************//
    //**************************************************//
    /**
     *  Writes a byte value to the destination array at the pointer index
     * @param dest - destination array into which the function writes
     * @param pointer - the index of dest array
     * @param value - byte value
     * @return int pointer
     */
    public static int writeBytes(byte[] dest, int pointer, byte value) {
        assert (dest.length > pointer + Type.getSize(Type.BYTE));
        dest[pointer++] = value;
        return pointer;
    }

    /**
     *  Writes a short value to the destination array at the pointer index.
     * @param dest - destination array into which the function writes
     * @param pointer - the index of dest array
     * @param value - short value (2 bytes)
     * @return int pointer
     */
    public static int writeBytes(byte[] dest, int pointer, short value) {
        assert (dest.length > pointer + Type.getSize(Type.SHORT));
        dest[pointer++] = (byte)((value >> 8) & 0xff); // 0x12 0x34 >> 8 => 0x00 0x12 & 0xff => 0x12
        dest[pointer++] = (byte)((value >> 0) & 0xff);//  0x12 0x34 >> 0 => 0x12 0x34 & 0xff => 0x34
        return pointer;
    }

    /**
     *  Writes a char value to the destination array at the pointer index.
     * @param dest - destination array into which the function writes
     * @param pointer - the index of dest array
     * @param value - char value (2 bytes)
     * @return int pointer
     */
    public static int writeBytes(byte[] dest, int pointer, char value) {
        assert (dest.length > pointer + Type.getSize(Type.CHAR));
        dest[pointer++] = (byte)((value >> 8) & 0xff);
        dest[pointer++] = (byte)((value >> 0) & 0xff);
        return pointer;
    }

    /**
     *  Writes a int value to the destination array at the pointer index.
     * @param dest - destination array into which the function writes
     * @param pointer - the index of dest array
     * @param value - int value (4 bytes) => ( 8 * 4 = 32 bits )
     * @return int pointer
     */
    public static int writeBytes(byte[] dest, int pointer, int value) {
        assert (dest.length > pointer + Type.getSize(Type.INTEGER));
        dest[pointer++] = (byte)((value >> 24) & 0xff);
        dest[pointer++] = (byte)((value >> 16) & 0xff);
        dest[pointer++] = (byte)((value >> 8) & 0xff);
        dest[pointer++] = (byte)((value >> 0) & 0xff);
        return pointer;
    }

    /**
     *  Writes a long value to the destination array at the pointer index.
     * @param dest - destination array into which the function writes
     * @param pointer - the index of dest array
     * @param value - long value (8 bytes)
     * @return int pointer
     */
    public static int writeBytes(byte[] dest, int pointer, long value) {
        assert (dest.length > pointer + Type.getSize(Type.LONG));
        dest[pointer++] = (byte)((value >> 56) & 0xff);
        dest[pointer++] = (byte)((value >> 48) & 0xff);
        dest[pointer++] = (byte)((value >> 40) & 0xff);
        dest[pointer++] = (byte)((value >> 32) & 0xff);
        dest[pointer++] = (byte)((value >> 24) & 0xff);
        dest[pointer++] = (byte)((value >> 16) & 0xff);
        dest[pointer++] = (byte)((value >> 8) & 0xff);
        dest[pointer++] = (byte)((value >> 0) & 0xff);
        return pointer;
    }

    /**
     *  Writes a float value to the destination array at the pointer index.
     * @param dest - destination array into which the function writes
     * @param pointer - the index of dest array
     * @param value - float value (8 bytes)
     * @return int pointer
     */
    public static int writeBytes(byte[] dest, int pointer, float value) {
        assert (dest.length > pointer + Type.getSize(Type.FLOAT));
        int data = Float.floatToIntBits(value);
        return writeBytes(dest, pointer, data);
    }

    /**
     *  Writes a double value (by casting to long) to the destination array at the pointer index.
     * @param dest - destination array into which the function writes
     * @param pointer - the index of dest array
     * @param value - double value (8 bytes)
     * @return int pointer
     */
    public static int writeBytes(byte[] dest, int pointer, double value) {
        assert (dest.length > pointer + Type.getSize(Type.DOUBLE));
        long data = Double.doubleToLongBits(value);
        return writeBytes(dest, pointer, data);
    }

    /**
     *  Writes a boolean value to the destination array at the pointer index.
     * @param dest - destination array into which the function writes
     * @param pointer - the index of dest array
     * @param value - boolean value (1 byte) - true => 00000001 (0x1 HEX) | false => 00000000 (0x0 HEX)
     * @return int pointer
     */
    public static int writeBytes(byte[] dest, int pointer, boolean value) {
        assert (dest.length > pointer + Type.getSize(Type.BOOLEAN));
        dest[pointer++] = (byte)(value ? 1 : 0);
        return pointer;
    }

        //**************************************************//
      //***************** NON-PRIMITIVES *****************//
    //**************************************************//

    /**
     *  Writes the string length in the first two bytes then the string to the destination array at the pointer index.
     * @param dest - destination array into which the function writes
     * @param pointer - the index of dest array
     * @param string - String (2 bytes for size + each char a byte)
     * @return int pointer
     */
    public static int writeBytes(byte[] dest, int pointer, String string) {
        pointer = writeBytes(dest, pointer, (short) string.length());
        return writeBytes(dest, pointer, string.getBytes());
    }
}
