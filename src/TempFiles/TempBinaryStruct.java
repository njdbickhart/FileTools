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
 *
 * @author Derek.Bickhart
 */
public interface TempBinaryStruct {
    public void CreateTemp(Path path) throws FileNotFoundException;
    public RandomAccessFile getFileForWriting() throws IOException;
    public RandomAccessFile getFileForReading() throws IOException;
    public void DumpToTemp();
}
