package crud;

import java.io.*;


public abstract class IOUtils implements Serializable {

    public static void writeToFile(String fileName, StringBuffer strbuf)
            throws Exception {

        File f = null;

        try {
            f = new File(fileName);
        } catch (Exception e) {
            // GDD - output this exception stack trace for debugging purposes -
            // Start
            e.printStackTrace();
            // GDD - output this exception stack trace for debugging purposes -
            // End
        }

        if (f != null) {
            f.delete();
        }

        // GDD - if folder does not exist, don't stick your head into the
        // ground, create it - Start
        boolean folderCreated = checkAndCreateFolders(f.getCanonicalFile()
                .getParent());
        // GDD - if folder does not exist, don't stick your head into the
        // ground, create it - End

        FileOutputStream fout = new FileOutputStream(fileName);
        fout.write(strbuf.toString().getBytes());

        try {

            f = new File(fileName);
            //f.setReadOnly();

        } catch (Exception e) {
            // GDD - output this exception stack trace for debugging purposes -
            // Start
            e.printStackTrace();
            // GDD - output this exception stack trace for debugging purposes -
            // End
            return;

        } finally {

            if (fout != null) {
                fout.close();
            }
        }
    }

    // GDD - if folder does not exist, don't stick your head into the ground,
    // create it - Start

    /**
     * @param folderName The directory structure that must be check and created if
     *                   neccessary.
     * @return true only if succesfull in total.
     */
    public static boolean checkAndCreateFolders(String folderName) {
        File folder = new File(folderName);
        if (!folder.isDirectory()) {
            System.out.println("Creating folder " + folderName);
            return folder.mkdirs();
        }
        return false;
    }

    // GDD - if folder does not exist, don't stick your head into the ground,
    // create it - End

    public static void appendToFile(String fileName, StringBuffer strbuf)
            throws Exception {

        File f = null;
        FileOutputStream fout = null;

        try {

            f = new File(fileName);
            fout = new FileOutputStream(fileName, true);
            fout.write(strbuf.toString().getBytes());

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (fout != null) {
                fout.close();
            }
        }
    }

    public static String readFile(String filename) throws Exception {

        FileInputStream fileStream = null;
        BufferedReader reader = null;
        StringBuffer strbuf = null;

        try {

            fileStream = new FileInputStream(filename);
            reader = new BufferedReader(new InputStreamReader(fileStream));
            boolean endFlag = true;
            String line = null;
            strbuf = new StringBuffer(300);

            while (endFlag) {

                line = reader.readLine();

                if (line != null) {
                    strbuf.append(line).append("\n");
                }

                if (line == null) {
                    endFlag = false;
                }
            }

        } finally {

            if (fileStream != null) {
                fileStream.close();
            }

            if (reader != null) {
                reader.close();
            }
        }

        return strbuf.toString();

    }

    public static String readFile(ListOfFiles filelist) throws Exception {

        SequenceInputStream fileStream = null;
        BufferedReader reader = null;
        StringBuffer strbuf = null;

        try {

            fileStream = new SequenceInputStream(filelist);
            reader = new BufferedReader(new InputStreamReader(fileStream));
            boolean endFlag = true;
            String line = null;
            strbuf = new StringBuffer(300);

            while (endFlag) {

                line = reader.readLine();

                if (line != null) {
                    strbuf.append(line).append("\n");
                }

                if (line == null) {
                    endFlag = false;
                }
            }

        } finally {
            fileStream.close();
            reader.close();
        }
        return strbuf.toString();
    }

    public static void seralizeObjectToFile(Object obj, String fileName)
            throws Exception {

        try {
            File f = new File(fileName);
            f.delete();
        } catch (Exception e) {
            System.out.println("Error deleting file " + fileName);
            e.printStackTrace();
        }

        FileOutputStream fout = null;
        ObjectOutputStream oout = null;

        try {

            fout = new FileOutputStream(fileName);
            oout = new ObjectOutputStream(fout);
            oout.writeObject(obj);

        } finally {

            if (fout != null) {
                fout.close();
            }
            if (oout != null) {
                oout.close();
            }
        }
    }

    public static Object deseralizeObjectFromFile(String fileName)
            throws Exception {

        FileInputStream fin = null;
        ObjectInputStream oin = null;

        try {

            fin = new FileInputStream(fileName);
            oin = new ObjectInputStream(fin);
            return oin.readObject();

        } finally {

            if (fin != null) {
                fin.close();
            }
            if (oin != null) {
                oin.close();
            }
        }
    }

}