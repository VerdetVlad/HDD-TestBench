package bench;

import java.io.*;
import java.util.Random;

import timer.Timer;

public class FileReader {



    // value = (fileSize/MB_Size) / (returnReadVal/10^9)



    private static final int KB_SIZE = 1024; // KB
    private static final int MB_SIZE = 1024 * 1024; // MB
    private static final int SecInNano = (int)Math.pow(10,9);


    private static final int[] bufferSizes={ 1*KB_SIZE,  2*KB_SIZE,  4*KB_SIZE,  8*KB_SIZE,  16*KB_SIZE, 32*KB_SIZE,
                                             64*KB_SIZE, 128*KB_SIZE, 256*KB_SIZE, 512*KB_SIZE, 1*MB_SIZE};

    private static final int[] fileSizes = {32*KB_SIZE, 64*KB_SIZE, 128*KB_SIZE, 256*KB_SIZE, 512*KB_SIZE, 1*MB_SIZE,
                                            2*MB_SIZE, 4*MB_SIZE};

    private static Timer timer = new Timer();






    private static double readWithBufferSize(String fileName, int myBufferSize,
                                     int fileSize) throws IOException
    {
        InputStream folderPath = new FileInputStream(System.getProperty("user.dir") + "\\" + fileName);


        // create stream writer with given buffer size
        final BufferedInputStream inputStream = new BufferedInputStream(folderPath, myBufferSize);

        byte[] buffer = new byte[myBufferSize];
        int i = 0;
        int toRead = fileSize / myBufferSize;

        timer.start();
        while (i < toRead) {

            inputStream.read();

            i++;
        }

        return timer.stop();

    }





    public static String streamReadFixedSize(
                                     int minIndex, int maxIndex,
                                     int fileIndex) throws IOException {


        String result = new String("");

        int fileSize = fileSizes[fileIndex];


        double benchScore = 0;
        int indexDif = maxIndex - minIndex + 1;
        int i=0;

        for(i=0; i< indexDif; i++) {

            int buffSize = bufferSizes[minIndex+i];

            double timeSec = readWithBufferSize(HDDBench.path.get(i),buffSize,fileSize);
            timeSec/=SecInNano;

            double mbPerSec = ((double)fileSize/MB_SIZE) / timeSec;
            benchScore += mbPerSec;

            result +=  String.format("%.2f",mbPerSec) + " MB/S;";
        }

        benchScore = benchScore/indexDif;
        result+=String.format("%.2f",benchScore)+ " MB/S";


        return result;

    }



    public static String streamReadFixedBuffer(
                                              int minIndex, int maxIndex,
                                              int buffIndex) throws IOException {


        String result = new String("");

        int buffSize = bufferSizes[buffIndex];

        double benchScore = 0;
        int indexDif = maxIndex - minIndex + 1;
        int i=0;

        for(i=0; i< indexDif; i++) {

            int fileSize = fileSizes[minIndex+i];

            double timeSec = readWithBufferSize(HDDBench.path.get(i),buffSize,fileSize);
            timeSec/=SecInNano;
            double mbPerSec = ((double)fileSize/MB_SIZE) / timeSec;
            benchScore += mbPerSec;

            result +=  String.format("%.2f",mbPerSec) + " MB/S;";
        }

        benchScore = benchScore/indexDif;
        result+=String.format("%.2f",benchScore) + " MB/S";


        return result;

    }






}
