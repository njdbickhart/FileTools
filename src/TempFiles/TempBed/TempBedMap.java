/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TempFiles.TempBed;

import TempFiles.TempDataClass;
import TempFiles.TempDataStruct;
import file.BedAbstract;
import file.BedMap;
import file.BedSimple;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A BedMap class that is capable of storing mapped data in a temporary file
 * for easier retrieval later. Currently only works on "BedSimple" entries
 * @author bickhart
 */
public class TempBedMap <T extends BedAbstract> extends BedMap<T> implements TempDataStruct{
    protected Path tempFile;
    protected BufferedReader handle = null;  
    protected BufferedWriter output = null;
    
    public TempBedMap(){
        super();
    }
    
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
    
    /**
     * This method reads from the temporary file if the data is available
     */
    @Override
    public void readSequentialFile() {
        try{
            if(this.handle == null){
                this.openTemp('R');
            }
            
            String line;
            while((line = handle.readLine()) != null){
                line = line.trim();
                String[] segs = line.split("\t");
                
                int bin = Integer.parseInt(segs[1]);
                int start = Integer.parseInt(segs[2]);
                int end = Integer.parseInt(segs[3]);
                
                if(!this.bedFile.containsKey(segs[0])){
                    this.bedFile.put(segs[0], new ConcurrentHashMap<Integer, ArrayList<T>>());
                    this.bedFile.get(segs[0]).put(bin, new ArrayList<T>());
                }
                
                if(!this.bedFile.get(segs[0]).containsKey(bin)){
                    this.bedFile.get(segs[0]).put(bin, new ArrayList<T>());
                }
                
                this.bedFile.get(segs[0]).get(bin).add((T) new BedSimple(segs[0], start, end, segs[4]));
            }
        }catch(IOException | NullPointerException ex){
            Logger.getLogger(TempDataClass.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.closeTemp('R');
        }
    }

    /**
     * This method dumps the contents of the main BedMap container to disk
     * so that memory can be conserved for later
     */
    @Override
    public void dumpDataToDisk() {
        try(BufferedWriter output = Files.newBufferedWriter(tempFile, Charset.defaultCharset())){
            
            for(String chr : this.bedFile.keySet()){
                for(Integer b : this.bedFile.get(chr).keySet()){
                    for(BedAbstract working : this.bedFile.get(chr).get(b)){
                        BedSimple bed = (BedSimple) working;
                        output.write(chr + "\t" + b + "\t" + bed.Start() + "\t"
                                + bed.End() + "\t" + bed.Name());
                        output.newLine();
                    }                    
                }
            }
            
            // Empty out the main container so that the rest can be GC'ed if necessary
            this.bedFile = new ConcurrentHashMap<>();
        }catch(IOException | NullPointerException ex){
            Logger.getLogger(TempDataClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
