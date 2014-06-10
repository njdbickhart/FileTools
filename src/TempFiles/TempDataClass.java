/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TempFiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bickhart
 */
public abstract class TempDataClass implements TempDataStruct {
    private Path tempFile;
    private BufferedReader handle;
    
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
     */    
    @Override
    public void openTemp(){
        try{
            this.handle = Files.newBufferedReader(tempFile, Charset.defaultCharset());
        }catch(IOException | NullPointerException ex){
            Logger.getLogger(TempDataClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * This closes the temporary filehandle
     */
    @Override
    public void closeTemp(){
        try{
            this.handle.close();
        }catch(IOException | NullPointerException ex){
            Logger.getLogger(TempDataClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
