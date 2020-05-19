package bench;

import java.io.*;
import java.util.Random;

import timer.Timer;

public class FileReader {

    private static final int KB_SIZE = 1024; // KB
    private static final int MB_SIZE = 1024 * 1024; // MB

    private static final int[] bufferSizes={KB_SIZE,  2*KB_SIZE,  4*KB_SIZE,  8*KB_SIZE,  16*KB_SIZE, 32*KB_SIZE, 64*KB_SIZE};

    private Timer timer = new Timer();

    public long readWithBufferSize(String fileName, int myBufferSize,
                                     long fileSize) throws IOException
    {

        InputStream folderPath = new FileInputStream(System.getProperty("user.dir") + "\\" + fileName);




        // create stream writer with given buffer size
        final BufferedInputStream inputStream = new BufferedInputStream(folderPath, myBufferSize);

        byte[] buffer = new byte[myBufferSize];
        int i = 0;
        long toRead = fileSize / myBufferSize;
        Random rand = new Random();

        timer.start();
        while (i < toRead) {
            // generate random content to write
            inputStream.read();

            i++;
        }

        return timer.stop();

    }

  /*  public void streamWriteFixedSize(String[] fileNames,
                                     int minIndex, int maxIndex,
                                     int fileIndex) throws IOException {

        System.out.println("Stream write benchmark with fixed file size");
        int currentBufferSize = MIN_BUFFER_SIZE;
        String fileName;
        int counter = 0;


        while(minIndex<=maxIndex)
        {

            minIndex++;
        }



        while (currentBufferSize <= MAX_BUFFER_SIZE
                && counter <= maxIndex - minIndex) {
            fileName = ... prefix + ? + suffix
            writeWithBufferSize(fileName, currentBufferSize, fileSize);
            // update buffer size
			...
            counter++;
        }

        benchScore /= (maxIndex - minIndex + 1);
        String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
        System.out.println("File write score on partition " + partition + ": "
                + String.format("%.2f", benchScore) + " MB/sec");
    }*/





}
