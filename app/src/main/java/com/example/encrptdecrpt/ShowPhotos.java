package com.example.encrptdecrpt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.crypto.NoSuchPaddingException;

public class ShowPhotos extends AppCompatActivity {

    ListView listview;
    public static ArrayList<File> mListFiles;
    ImageView imageview;
    byte[] imgbyt;
    public static String FOLDER_NAME = "/encryptedImage";
    String my_key = "71o23FOlCtOqVD6v";
    String my_soc_key = "Yieo4CwhEOsJkZ6b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photos);
        listview=findViewById(R.id.listview);
        imageview=findViewById(R.id.imageview);

        initUI();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                decyptedimg(mListFiles.get(position).getName());
            }
        });

    }
    private void initUI() {
        mListFiles = FileUtils.getallfiles();
        Collections.sort(mListFiles, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.lastModified() > o2.lastModified()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

   if (mListFiles.size()>0) {
       ArrayAdapter<File> arrayAdapter = new ArrayAdapter<File>(ShowPhotos.this, android.R.layout.simple_list_item_1, mListFiles);
       listview.setAdapter(arrayAdapter);
   }
   else
   {
       Toast.makeText(this, "Encrpted Photos not found", Toast.LENGTH_SHORT).show();
   }



    }



    private  void decyptedimg(String imgname)
    {
        //create file
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + FOLDER_NAME;
        File outfutFileDec = new File(path,imgname+"."+"png");
        File encfile = new File(path,imgname);
        try {
          CryptoUtils.dencrytTofile(my_key,my_soc_key,new FileInputStream(encfile),new FileOutputStream(outfutFileDec));

            imageview.setImageURI(Uri.fromFile(outfutFileDec));
            outfutFileDec.delete();
            Toast.makeText(ShowPhotos.this, "Decypted", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(ShowPhotos.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Toast.makeText(ShowPhotos.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (InvalidKeyException e) {
            e.printStackTrace();
            Toast.makeText(ShowPhotos.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            Toast.makeText(ShowPhotos.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            Toast.makeText(ShowPhotos.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }
}