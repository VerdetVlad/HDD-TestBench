package bench;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class HDDBench implements IBenchmark {

    private Boolean running;
    private ArrayList<BufferedOutputStream> outputStream = new ArrayList<BufferedOutputStream>();
    private ArrayList<Integer> buffer = new ArrayList<Integer>();
    private ArrayList<Integer> file = new ArrayList<Integer>();
    private ArrayList<Boolean> options = new ArrayList<Boolean>();

    //creates files and opens them
    public void initialize(int[] buffer, int[] file, boolean[] options) {
        for(int i = buffer[0] ; i < buffer[1] ; ++i) {
            this.buffer.add(Integer.valueOf(i));
        }

        for(int i = file[0] ; i < file[1] ; ++i) {
            this.file.add(Integer.valueOf(i));
        }

        for(int i = 0 ; i < options.length ; ++i) {
            this.options.add(Boolean.valueOf(options[i]));
        }

        File folderObj = new File("./");

        if(folderObj.isDirectory() == false) {
            folderObj.mkdirs();
        }

        for(int i = 0 ; i < (file[1] - file[0] + 1) ; ++i) {
            FileOutputStream fileObj = null;

            try {
                fileObj = new FileOutputStream(new File("File_" + i));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            outputStream.add(new BufferedOutputStream(fileObj));
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
