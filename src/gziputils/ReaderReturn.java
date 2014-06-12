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
    /**
     * This Static function returns the state of the file.
     * True = file is zipped. False = file is not zipped.
     * @param f File to check for zip status
     * @return
     */
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
    /**
     * This function returns the base name of a file.
     * Similar to basename functions in other languages.
     * @param file is a string that will be dissected 
     * @return
     */
    public static String getBaseFileName(String file){
        String[] tokens = file.split("\\.(?=[^\\.]+$)");
        return tokens[0];
    }
    /**
     * This function returns a buffered reader for zipped and unzipped files.
     * The appropriate reader is returned based on the file's zip status.
     * @param file is the file to be opened
     * @return
     */
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
