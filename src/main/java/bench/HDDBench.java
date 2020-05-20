package bench;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.io.*;
import java.util.ArrayList;

public class HDDBench implements IBenchmark {

    private Boolean running;
    private ArrayList<BufferedOutputStream> outputStream = new ArrayList<BufferedOutputStream>();
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


        try {

            if(options.get(0) == false) {

                writeOutput = FileWriter.streamWriteFixedSize(bufferSize.get(0), bufferSize.get(1), fileSize.get(0));
                readOutput = FileReader.streamReadFixedSize(bufferSize.get(0), bufferSize.get(1), fileSize.get(0));
            }
            else if(options.get(0) == true) {
                writeOutput = FileWriter.streamWriteFixedBuffer(fileSize.get(0), fileSize.get(1), bufferSize.get(0));
                readOutput = FileReader.streamReadFixedBuffer(fileSize.get(0), fileSize.get(1), bufferSize.get(0));
            }
            else
            throw new IllegalArgumentException("Argument "
                    + options.get(1).toString() + " is undefined");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }






    //run for smallest value
    public void warmUp() {

    }


    //delete files
    public void clean() {

    }


    //stops forever
    public void cancel() {
        running = false;
    }


    //The data to be put in the text box
    public String getResult() {
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
