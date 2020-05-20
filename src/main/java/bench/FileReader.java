package bench;

import java.io.*;
import java.util.Random;

import timer.Timer;

public class FileReader {
    private static Timer timer = new Timer();






    public static double readWithBufferSize(String fileName, int myBufferSize,
                                     int fileSize) throws IOException
    {
        InputStream folderPath = new FileInputStream(System.getProperty("user.dir") + "\\filesCreated\\" + fileName);


        // create stream writer with given buffer size
        final BufferedInputStream inputStream = new BufferedInputStream(folderPath);

        byte[] buffer = new byte[myBufferSize];
        int i = 0;
        int toRead = fileSize / myBufferSize;

        timer.start();
        while (i < toRead) {

            inputStream.read(buffer,0,myBufferSize);

            i++;
        }

        inputStream.close();
        return timer.stop();

    }





    public static String streamReadFixedSize(int minIndex, int maxIndex, int fileIndex) throws IOException {
        String result = new String("");

        int fileSize = ParametersSizes.fileSizes[fileIndex];


        double benchScore = 0;
        double timeAvg = 0.0;
        int indexDif = maxIndex - minIndex + 1;
        int i=0;

        for(i=0; i< indexDif; i++) {

            int buffSize = ParametersSizes.bufferSizes[minIndex+i];

            int repeat = ParametersSizes.repeatPerSize;

            while(repeat-- > 0 && HDDBench.running) {
                double timeSec = readWithBufferSize(HDDBench.path.get(i), buffSize, fileSize);
                timeAvg += timeSec / ParametersSizes.SecInNano;
            }

            if(!HDDBench.running) {
                return null;
            }

            timeAvg/=ParametersSizes.repeatPerSize;

            double mbPerSec = ((double)fileSize/ParametersSizes.MB_SIZE) / timeAvg;
            benchScore += mbPerSec;

            if(mbPerSec >= 1024) {
                result +=  String.format("%.2f",mbPerSec / 1024.0) + " GB/sec;";
            }
            else {
                result +=  String.format("%.2f",mbPerSec) + " MB/sec;";
            }
        }

        benchScore = benchScore/indexDif;

        if(benchScore >= 1024) {
            result+=String.format("%.2f",benchScore / 1024.0)+ " GB/sec";
        }
        else {
            result+=String.format("%.2f",benchScore)+ " MB/sec";
        }


        return result;

    }



    public static String streamReadFixedBuffer(int minIndex, int maxIndex, int buffIndex) throws IOException {
        String result = new String("");

        int buffSize = ParametersSizes.bufferSizes[buffIndex];

        double benchScore = 0;
        double timeAvg = 0.0;
        int indexDif = maxIndex - minIndex + 1;
        int i=0;

        for(i=0; i< indexDif; i++) {

            int fileSize = ParametersSizes.fileSizes[minIndex+i];
            int repeat = ParametersSizes.repeatPerSize;

            while(repeat-- > 0 && HDDBench.running) {
                double timeSec = readWithBufferSize(HDDBench.path.get(i), buffSize, fileSize);
                timeAvg += timeSec / ParametersSizes.SecInNano;
            }

            if(!HDDBench.running) {
                return null;
            }

            timeAvg /= ParametersSizes.repeatPerSize;

            double mbPerSec = ((double)fileSize/ParametersSizes.MB_SIZE) / timeAvg;
            benchScore += mbPerSec;

            if(mbPerSec >= 1024) {
                result +=  String.format("%.2f",mbPerSec / 1024.0) + " GB/sec;";
            }
            else {
                result +=  String.format("%.2f",mbPerSec) + " MB/sec;";
            }
        }

        benchScore = benchScore/indexDif;
        if(benchScore >= 1024) {
            result+=String.format("%.2f",benchScore / 1024.0)+ " GB/sec";
        }
        else {
            result+=String.format("%.2f",benchScore)+ " MB/sec";
        }


        return result;

    }






}
