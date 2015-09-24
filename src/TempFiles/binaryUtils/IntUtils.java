/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TempFiles.binaryUtils;

/**
 *
 * @author Derek.Bickhart
 */
public class IntUtils {
    public static int byteArrayToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < b.length; i++) {
            int shift = (b.length - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }
    
    // TODO: check to see if this is an endian issue
    public static byte[] Int32ToByteArray(int a){
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);   
        ret[2] = (byte) ((a >> 8) & 0xFF);   
        ret[1] = (byte) ((a >> 16) & 0xFF);   
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }
    
    public static byte[] Int16ToTwoByteArray(int a){
        byte[] ret = new byte[2];
        ret[0] = (byte) ((a >> 8) & 0xFF);
        ret[1] = (byte) (a & 0xFF);
        return ret;
    }
}
