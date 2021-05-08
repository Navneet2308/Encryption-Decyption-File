package com.example.encrptdecrpt;

import android.media.MediaCodec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {

    private static final String ALGO_IMAGE_ENCRYPTOR = "AES/CBC/PKCS5Padding";
    private static final String ALGO_SECRET_KEY = "AES";

    private static final  int READ_WRITE_BLOCK_BUTTER = 1024;


    public  static void encrytTofile(String keyStr, String specStr, InputStream in, OutputStream out)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException {

        try
        {

            IvParameterSpec iv = new IvParameterSpec(specStr.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(keyStr.getBytes("UTF-8"),ALGO_SECRET_KEY);

            Cipher c = Cipher.getInstance(ALGO_IMAGE_ENCRYPTOR);
            c.init(Cipher.ENCRYPT_MODE,keySpec,iv);
            out = new CipherOutputStream(out,c);
            int count =0;
            byte[] buffer = new byte[READ_WRITE_BLOCK_BUTTER];
            while ((count = in.read(buffer))>0)
            {
                out.write(buffer,0,count);

            }



        }
        finally
        {
            out.close();
        }



    }

    public  static void dencrytTofile(String keyStr, String specStr, InputStream in, OutputStream out)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException {

        try
        {

            IvParameterSpec iv = new IvParameterSpec(specStr.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(keyStr.getBytes("UTF-8"),ALGO_SECRET_KEY);

            Cipher c = Cipher.getInstance(ALGO_IMAGE_ENCRYPTOR);
            c.init(Cipher.DECRYPT_MODE,keySpec,iv);
            out = new CipherOutputStream(out,c);
            int count =0;
            byte[] buffer = new byte[READ_WRITE_BLOCK_BUTTER];
            while ((count = in.read(buffer))>0)
            {
                out.write(buffer,0,count);

            }



        }
        finally
        {
            out.close();
        }



    }



//    public static byte[] encrypt(String key, File inputFile, File outputFile)
//            throws MediaCodec.CryptoException {
//      return doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
//    }
//
//    public static byte[] decrypt(String key, File inputFile, File outputFile)
//            throws MediaCodec.CryptoException {
//      return  doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
//    }
//
//    private static byte[] doCrypto(int cipherMode, String key, File inputFile,
//                                 File outputFile) throws MediaCodec.CryptoException {
//        try {
//            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
//            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
//            cipher.init(cipherMode, secretKey);
//
//            FileInputStream inputStream = new FileInputStream(inputFile);
//            byte[] inputBytes = new byte[(int) inputFile.length()];
//            inputStream.read(inputBytes);
//
//            byte[] outputBytes = cipher.doFinal(inputBytes);
//
//            FileOutputStream outputStream = new FileOutputStream(outputFile);
//            outputStream.write(outputBytes);
//
//            inputStream.close();
//            outputStream.close();
//
//
//            return  outputBytes;
//
//        } catch (NoSuchPaddingException | NoSuchAlgorithmException
//                | InvalidKeyException | BadPaddingException
//                | IllegalBlockSizeException | IOException ex) {
//
//        }
//
//        return  null;
//    }
//

}
