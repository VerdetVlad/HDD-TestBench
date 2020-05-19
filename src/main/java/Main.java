
import startInterface.*;
import bench.*;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {

        HDDBench test= new HDDBench();

        int[] b = {2,2};
        int[] f = {2,4};
        boolean[] o = {true,false};
        int dif = 4-2+1;

        test.initialize(b,f,o,dif);


        try {
            System.out.println(FileWriter.streamWriteFixedBuffer(test.fileSize.get(0), test.fileSize.get(1),test.bufferSize.get(0)));
            System.out.println(FileReader.streamReadFixedBuffer(test.fileSize.get(0), test.fileSize.get(1),test.bufferSize.get(0)));

//            System.out.println(FileWriter.streamWriteFixedSize(test.bufferSize.get(0), test.bufferSize.get(1),test.fileSize.get(0)));
//            System.out.println(FileReader.streamReadFixedSize(test.bufferSize.get(0), test.bufferSize.get(1),test.fileSize.get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //StartInterface.main(args);

    }
}
