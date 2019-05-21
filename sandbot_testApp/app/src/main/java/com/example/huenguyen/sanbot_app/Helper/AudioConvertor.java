package com.example.huenguyen.sanbot_app.Helper;

import android.media.AudioFormat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by HUE NGUYEN on 5/21/2019.
 * This class is used to convert raw audio file to .wav file
 */

public class AudioConvertor {

    private void writeWaveFileHeader(
            FileOutputStream out, int totalAudioLen,
            int totalDataLen, int sampleRate, short channels,
            short BPS) throws IOException
    {
        byte[] littleBytes = ByteBuffer
                        .allocate(22)
                        .order(ByteOrder.LITTLE_ENDIAN)
                        .putInt(totalAudioLen)
                        .putShort(channels)
                        .putInt(sampleRate)
                        .putInt(sampleRate * channels * (BPS / 8))
                        .putShort((short) (channels * (BPS / 8)))
                        .putShort(BPS)
                        .putInt(totalDataLen)
                        .array();
        byte[] header = new byte[]{
                'R', 'I', 'F', 'F', // ChunkID
                littleBytes[0], littleBytes[1], littleBytes[2], littleBytes[3], // ChunkSize
                'W', 'A', 'V', 'E', // Format
                'f', 'm', 't', ' ', // 'fmt ' chunk
                16, 0, 0, 0, // 4 bytes: size of 'fmt ' chunk
                1, 0, // AudioFormat
                littleBytes[4], littleBytes[5], // NumChannels
                littleBytes[6], littleBytes[7], littleBytes[8], littleBytes[9], // SampleRate
                littleBytes[10], littleBytes[11], littleBytes[12], littleBytes[13], // ByteRate
                littleBytes[14], littleBytes[15], // BlockAlign
                littleBytes[16], littleBytes[17], // BitsPerSample
                'd', 'a', 't', 'a',
                littleBytes[18], littleBytes[19], littleBytes[20], littleBytes[21]
        };
        out.write(header, 0, 44);
    }

    public void saveWaveFile(String inFilename,String outFilename, int sampleRate, short channels, short BPS, int minBufferSize){
        FileInputStream in = null;
        FileOutputStream out = null;
        int totalAudioLen = 0;
        int totalDataLen = totalAudioLen + 36;

        byte[] data = new byte[minBufferSize];

        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);
            totalAudioLen = (int) in.getChannel().size();
            totalDataLen = totalAudioLen + 36;


            writeWaveFileHeader(out, totalAudioLen, totalDataLen,
                    sampleRate, channels, BPS);

            while(in.read(data) != -1) {
                out.write(data);
            }

            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
