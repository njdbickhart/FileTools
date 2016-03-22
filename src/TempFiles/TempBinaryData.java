/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TempFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

/**
 * Sequential access temporary data file for binary implementation
 * @author Derek.Bickhart
 */
public abstract class TempBinaryData implements TempBinaryStruct{
    protected Path tempFile;
    protected RandomAccessFile dataFile;
    
    
    @Override
    public void CreateTemp(Path path) throws FileNotFoundException{
        tempFile = path;
        this.dataFile = new RandomAccessFile(tempFile.toFile(), "rw");
    }

    @Override
    public RandomAccessFile getFileForWriting() throws IOException{
        if(this.dataFile.getFilePointer() != this.dataFile.length()){
            this.dataFile.seek(this.dataFile.length());
        }
        
        return this.dataFile;
    }
    @Override
    public RandomAccessFile getFileForReading() throws IOException{
        if(this.dataFile.length() == 0l)
            throw new IOException("Error! Temporary file was unexpectedly empty!");
        if(this.dataFile.getFilePointer() != 0l){
            this.dataFile.seek(0l);
        }
        return this.dataFile;
    }
}
