package bench;

import java.io.*;
import java.util.Random;

import timer.Timer;

public class FileReader {

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

     /*   printStats( fileSize, myBufferSize);

        outputStream.close();
        if(clean)
            delete file on exit ?*/
    }
}
