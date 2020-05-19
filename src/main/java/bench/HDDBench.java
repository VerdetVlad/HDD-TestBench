package bench;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class HDDBench implements IBenchmark {

    private Boolean running;
    private ArrayList<BufferedOutputStream> outputStream = new ArrayList<BufferedOutputStream>();
    public static ArrayList<Integer> bufferSize = new ArrayList<Integer>();
    public static ArrayList<Integer> fileSize = new ArrayList<Integer>();
    public static ArrayList<Boolean> options = new ArrayList<Boolean>();
    public static ArrayList<String> path = new ArrayList<>();

    //creates files and opens them
    public void initialize(int[] buffer, int[] file, boolean[] options, int indexDif) {
        for(int i = 0 ; i < 2 ; ++i) {
            this.bufferSize.add(Integer.valueOf(buffer[i]));
        }

        for(int i = 0 ; i < 2 ; ++i) {
            this.fileSize.add(Integer.valueOf(file[i]));
        }

        for(int i = 0 ; i < options.length ; ++i) {
            this.options.add(Boolean.valueOf(options[i]));
        }


        for(int i = 0 ; i < indexDif ; ++i) {
            String tempPath;

            tempPath = "File_" + i;

            path.add(tempPath);

        }
    }


    //run for the parameters given in initailize
    public void run() {

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
        return String.valueOf(0);
    }
}
