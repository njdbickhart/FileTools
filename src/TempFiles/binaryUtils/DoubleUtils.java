/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TempFiles.binaryUtils;

import java.nio.ByteBuffer;

/**
 *
 * @author Derek.Bickhart
 */
public class DoubleUtils {
    public static byte[] Dbl64toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    public static double BytetoDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
}
