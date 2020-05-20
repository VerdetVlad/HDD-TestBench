package bench;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.io.*;
import java.util.ArrayList;

public class HDDBench implements IBenchmark {

    public static Boolean running;
    public static ArrayList<Integer> bufferSize = new ArrayList<Integer>();
    public static ArrayList<Integer> fileSize = new ArrayList<Integer>();
    public static ArrayList<Boolean> options = new ArrayList<Boolean>();
    public static ArrayList<String> path = new ArrayList<>();

    private static String writeOutput = new String("");
    private static String readOutput = new String("");

    //creates files and opens them
    public void initialize(int[] buffer, int[] file, boolean[] opt, int indexDif) {

        bufferSize.clear();
        fileSize.clear();
        options.clear();
        path.clear();



        for(int i = 0 ; i < 2 ; ++i) {
            this.bufferSize.add(Integer.valueOf(buffer[i]));
        }

        for(int i = 0 ; i < 2 ; ++i) {
            this.fileSize.add(Integer.valueOf(file[i]));
        }

        for(int i = 0 ; i < opt.length ; ++i) {
            this.options.add(Boolean.valueOf(opt[i]));
        }


        for(int i = 0 ; i < indexDif ; ++i) {
            String tempPath;

            tempPath = "File_" + i;

            path.add(tempPath);

        }
    }


    //run for the parameters given in initailize
    public void run() {

        running = true;

        try {

            if(options.get(0) == false) {

                writeOutput = FileWriter.streamWriteFixedSize(bufferSize.get(0), bufferSize.get(1), fileSize.get(0));
                if(writeOutput == null) { return ; }
                readOutput = FileReader.streamReadFixedSize(bufferSize.get(0), bufferSize.get(1), fileSize.get(0));
                if(readOutput == null) { return ; }
            }
            else if(options.get(0) == true) {
                writeOutput = FileWriter.streamWriteFixedBuffer(fileSize.get(0), fileSize.get(1), bufferSize.get(0));
                if(writeOutput == null) { return ; }
                readOutput = FileReader.streamReadFixedBuffer(fileSize.get(0), fileSize.get(1), bufferSize.get(0));
                if(readOutput == null) { return ; }
            }
            else
            throw new IllegalArgumentException("Argument "
                    + options.get(1).toString() + " is undefined");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(options.get(1)) clean();


    }






    //run for smallest value
    public void warmUp() {
        for(int i = 0;i<10;i++)
        {
            try {
                FileWriter.writeWithBufferSize("WarmUp_" + i, 1024, 16 * 1024);
                FileReader.readWithBufferSize("WarmUp_" + i, 1024, 16 * 1024);
            }
            catch (Exception e)
            {
                //stop program
            }
        }

        clean();
    }


    //delete files
    public void clean() {
        File index = new File(System.getProperty("user.dir") + "\\filesCreated");
        String[]entries = index.list();
        for(String s: entries){
            File currentFile = new File(index.getPath(),s);
            currentFile.delete();
        }
        index.delete();


    }


    //stops forever
    public void cancel() {
        running = false;
    }


    //The data to be put in the text box
    public String getResult() {

        if((writeOutput==null) || (readOutput==null)) return null;

        String[] writeArr = new String[30];
        String[] readArr = new String[30];

        writeArr = writeOutput.split(";");
        readArr = readOutput.split(";");

        String combinedString = new String("");

        int i;
        for(i=0; i<writeArr.length; i++)
        {

            combinedString+= writeArr[i] + "," + readArr[i] + ";";
        }

        return combinedString;


    }
}
