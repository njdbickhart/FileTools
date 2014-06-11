/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TempFiles;

/**
 *
 * @author bickhart
 */
public interface TempBuffer {
    
    /**
     * This method adds objects to a collection for this class until a buffer
     * threshold is reached. Then the data is spilled to disk as part of a temp
     * file
     * @param <T> Generic class
     * @param a A single instance of the class to be added to the collection
     */
    public <T> void bufferedAdd(T a);
    
    /*
     * This method restores all objects to the buffered collection
     */
    public void restoreAll();
    
    /*
     * This method empties the specificed collection(s) to disk
     */
    public void pushAllToDisk();
}
