/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.nio.file.Path;

/**
 *
 * @author bickhart
 */
public class GetBaseName {
    public static String getBaseName(Path path){
        String[] tokens = path.toString().split("[\\/]");
        return tokens[tokens.length - 1];
    }
    public static String getBaseName(String path){
        String[] tokens = path.split("[\\/]");
        return tokens[tokens.length - 1];
    }
}
