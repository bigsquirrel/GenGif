package com.ivanchou.gengif;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ant.liao.GifView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;


public class MyActivity extends Activity {

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(MyActivity.this, "Gen OK!", Toast.LENGTH_SHORT).show();
            String path = Environment.getExternalStorageDirectory() + "/GifFolder";
            InputStream is = null;
            try {
                if (msg.what == 1) {
                    is = new FileInputStream(path + "/demondk.gif");
                } else if (msg.what == 2) {
                    is = new FileInputStream(path + "/demojava.gif");

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (is != null) {
                gifView.setGifImage(is);
            }
        }
    };

    GifUtil gifUtil = new GifUtil();

    GifView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        findViewById(R.id.ndkgif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path = Environment.getExternalStorageDirectory() + "/GifFolder";
                        gifUtil.Encode(path + "/demondk.gif", genBitmaps(path), 35);

                        Message msg = new Message();
                        msg.what = 1;
                        handle.sendMessage(msg);
                    }
                }).start();
            }
        });

        findViewById(R.id.javagif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path = Environment.getExternalStorageDirectory() + "/GifFolder";
                        generateGif(path);
                        Message msg = new Message();
                        msg.what = 2;
                        handle.sendMessage(msg);
                    }
                }).start();

            }
        });

        gifView = (GifView) findViewById(R.id.gifview);

    }

    private Bitmap[] genBitmaps(String path) {
        Bitmap[] bitmaps = new Bitmap[6];
        File file = new File(path);
        if (file.list() != null) {
            String[] fileNames = file.list();
            ArrayList<String> gifFileNames = new ArrayList<String>();
            for (int i = 0; i < fileNames.length; i++) {
                if (fileNames[i].contains("animation")) {
                    gifFileNames.add(fileNames[i]);
                }
            }


            Bitmap bmap = null;
            for (int i = 0; i < gifFileNames.size(); i++) {
//                bmap = PhotoUtil.compressBySize(path + "/" + gifFileNames.get(i));
                bmap = BitmapFactory.decodeFile(path + "/" + gifFileNames.get(i));
                bitmaps[i] = bmap;
            }
        }
        return bitmaps;
    }

    private void generateGif(String path) {
        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
        File file = new File(path);
        if (file.list() != null) {
            String[] fileNames = file.list();
            ArrayList<String> gifFileNames = new ArrayList<String>();
            for (int i = 0; i < fileNames.length; i++) {
                if (fileNames[i].contains("animation")) {
                    gifFileNames.add(fileNames[i]);
                }
            }


            Bitmap bmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inDither = false;
            for (int i = 0; i < gifFileNames.size(); i++) {
//                bmap = PhotoUtil.compressBySize(path + "/" + gifFileNames.get(i));
                bmap = BitmapFactory.decodeFile(path + "/" + gifFileNames.get(i));
                Log.d("Bitmap Test", "--->" + bmap.getConfig());  //ARGB_8888
                bitmaps.add(bmap);
            }

//            int index = 0;
//            for (Bitmap bitmap : bitmaps) {
//                PhotoUtil.saveBitmapToSDCard(path + "/" + index + ".jpg", bitmap);
//                index++;
//            }
//            index = 0;

//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            GifEncoder encoder = new GifEncoder();
//            encoder.setDelay(350);
//            encoder.start(bos);
//            encoder.setQuality(100);
//            for (Bitmap bitmap : bitmaps) {
//                encoder.addFrame(bitmap);
//            }
//            encoder.finish();
//            bitmaps.clear();
//
//            FileOutputStream outStream = null;
//            try {
//                outStream = new FileOutputStream(path + "/demojava.gif");
//                outStream.write(bos.toByteArray());
//                outStream.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

    }
}
