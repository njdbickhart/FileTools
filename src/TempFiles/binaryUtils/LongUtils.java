/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TempFiles.binaryUtils;

import static TempFiles.binaryUtils.GeneralUtils.getByteSlice;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Derek.Bickhart
 */
public class LongUtils {
    public static List<Long> sectionByteArraysToLongList(byte[] b) throws Exception{
        if(b.length % 8 != 0)
            throw new Exception("[FILETOOLS] Input byte array is not a multiple of 64 bits!");
        List<Long> holder = Collections.synchronizedList(new ArrayList<Long>(b.length / 8));
        for(int i = 0; i < b.length; i += 8){
            long t = byteArrayToLong(getByteSlice(b, i, i + 8));
            holder.add(t);
        }
        return holder;
    }
    
    public static long byteArrayToLong(byte[] b){
        long value = 0l;
        for (int i = 0; i < b.length; i++) {
            int shift = (b.length - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }
    
    public static long byteArrayToLong(Byte[] b){
        long value = 0l;
        for (int i = 0; i < b.length; i++) {
            int shift = (b.length - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }
    
    public static byte[] longToByteArray(long l){
        return ByteBuffer.allocate(8).putLong(0, l).array();
    }
}
