/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gziputils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author bickhart
 */
public class ReaderReturn {
    public static boolean isGZipped(File f) {
        int magic = 0;
        try {
         RandomAccessFile raf = new RandomAccessFile(f, "r");
         magic = raf.read() & 0xff | ((raf.read() << 8) & 0xff00);
         raf.close();
        } catch (Throwable e) {
         e.printStackTrace(System.err);
        }
        return magic == GZIPInputStream.GZIP_MAGIC;
   }
    public static String getBaseFileName(String file){
        String[] tokens = file.split("\\.(?=[^\\.]+$)");
        return tokens[0];
    }
    public static BufferedReader openFile(File file){
        BufferedReader output = null;
        try {
            if(isGZipped(file)){
                InputStream StrFq1 = new FileInputStream(file);
                InputStream gzFq1 = new GZIPInputStream(StrFq1);
                Reader decoder = new InputStreamReader(gzFq1, "UTF-8");
                output = new BufferedReader(decoder);
                return output;
            }else{
                InputStream StrFq1 = new FileInputStream(file);
                Reader decoder = new InputStreamReader(StrFq1, "UTF-8");
                output = new BufferedReader(decoder);
                return output;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return output;
    }
}
