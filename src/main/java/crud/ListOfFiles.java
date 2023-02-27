package crud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 *
 */
public class ListOfFiles implements Enumeration, Serializable {
    private String[] listOfFiles;
    private int current = 0;

    /**
     * Creates a new instance of ListOfFiles
     */
    public ListOfFiles(String[] listOfFiles) {
        this.listOfFiles = listOfFiles;
    }


    public Object nextElement() {
        InputStream in = null;

        if (!hasMoreElements())
            throw new NoSuchElementException("No more files.");
        else {
            String nextElement = listOfFiles[current];
            current++;
            try {
                in = new FileInputStream(nextElement);
            } catch (FileNotFoundException e) {
                System.err.println("ListOfFiles: Can't open " + nextElement);
            }
        }
        return in;
    }


    public boolean hasMoreElements() {
        return current < listOfFiles.length;
    }


}
