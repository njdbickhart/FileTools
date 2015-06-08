/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TempFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bickhart
 */
public abstract class TempDataClass implements TempDataStruct {
    protected Path tempFile;
    protected BufferedReader handle;
    protected BufferedWriter output;
    
    /**
     * Creates a temporary file that will be used to spill data to disk
     * In order to avoid file handle limitations in the OS, the file will 
     * not be kept constantly open unless otherwise instructed by a subclass
     * instantiation.
     * @param path
     */
    @Override
    public void createTemp(Path path){
        try {
            this.tempFile = Files.createTempFile(path.toString(), ".tmp");
            this.tempFile.toFile().deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(TempDataClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * If the end user wants to delete a temp file before the termination of the 
     * program, this method will delete the file. Otherwise, temp files are 
     * automatically deleted at the termination of the JVM instance.
     */
    @Override
    public void deleteTemp(){
        try{
            Files.deleteIfExists(tempFile);
        }catch(IOException ex){
            Logger.getLogger(TempDataClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This opens the temporary filehandle for reading
     * @param mode Designates the input or output mode as follows: 'R', 'W', and 'A' are for reading, writing and appending
     * respectively.
     */    
    @Override
    public void openTemp(char mode){
        try{
            switch(mode){
                case 'R' : this.handle = Files.newBufferedReader(tempFile, Charset.defaultCharset()); break;
                case 'W' : this.output = Files.newBufferedWriter(tempFile, Charset.defaultCharset(), StandardOpenOption.WRITE); break;
                case 'A' : this.output = Files.newBufferedWriter(tempFile, Charset.defaultCharset(), StandardOpenOption.APPEND); break;
                default : throw new IOException("[TempFile] Must specify R, W, or A modes!");
            }
        }catch(IOException | NullPointerException ex){
            Logger.getLogger(TempDataClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * This closes the temporary filehandle
     * @param mode Designates the input or output mode as follows: 'R', 'W', and 'A' are for reading, writing and appending
     * respectively.
     */
    @Override
    public void closeTemp(char mode){
        try{
            // change to automatically flush output when closing.
            switch(mode){
                case 'R' : this.handle.close(); break;
                case 'W' : 
                case 'A' : this.output.flush(); this.output.close(); break;
                default : throw new IOException("[TempFile] Must specify R, W, or A modes!");
            }
        }catch(IOException | NullPointerException ex){
            Logger.getLogger(TempDataClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
