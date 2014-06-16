/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TempFiles.TempBed;

import TempFiles.TempBuffer;
import TempFiles.TempDataClass;
import TempFiles.TempDataStruct;
import file.BedAbstract;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class will be far more useful for keeping track of the boundaries of
 * genomic locations held within a central repository
 * @author bickhart
 */
public abstract class BufferedBed extends BedAbstract implements TempDataStruct, TempBuffer<BedAbstract>{
    private Path tempFile;
    protected BufferedReader handle;
    protected BufferedWriter output;
    
    /**
     * Creates a temporary file that will be used to spill data to disk
     * In order to avoid file handle limitations in the OS, the file will 
     * not be kept constantly open unless otherwise instructed by a subclass
     * instantiation.
     * @param path the location of the temporary file
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
     * @param mode Designates write status. Either R (read), W (write) or A (append)
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
     * @param mode Designates write status. Either R (read), W (write) or A (append)
     */
    @Override
    public void closeTemp(char mode){
        try{
            switch(mode){
                case 'R' : this.handle.close(); break;
                case 'W' : this.output.close(); break;
                case 'A' : this.output.close(); break;
                default : throw new IOException("[TempFile] Must specify R, W, or A modes!");
            }
        }catch(IOException | NullPointerException ex){
            Logger.getLogger(TempDataClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean hasTemp(){
        File checker = this.tempFile.toFile();
        return (checker.isFile() && checker.canRead());
    }
    
}
