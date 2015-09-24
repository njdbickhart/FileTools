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
public class GeneralUtils {
    public static byte[] getByteSlice(byte[] block, int startpos, int len){
        byte[] temp = new byte[len];
        int counter = 0;
        for(int i = startpos; i < startpos + len; i++){
            temp[counter] = block[i];
        }
        return temp;
    }
}
