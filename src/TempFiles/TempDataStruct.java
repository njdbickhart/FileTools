/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TempFiles;

import java.nio.file.Path;

/**
 *
 * @author bickhart
 */
public interface TempDataStruct {
    public void createTemp(Path path);
    
    public void deleteTemp();
    
    public void readSequentialFile();
    
    public void closeTemp();
    
    public void openTemp();
    
    public void dumpDataToDisk();
}
