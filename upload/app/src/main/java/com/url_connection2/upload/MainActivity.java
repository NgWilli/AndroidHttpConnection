﻿package com.url_connection2.upload;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity
{

    private String newName = "GLJTEST2.mp3";
    //要上传的本地文件路径
    private String uploadFile = "/data/data/com.url_connection2.upload/bangbangbang.mp3";
    //上传到服务器的指定位置
    private String actionUrl = "yourURL/cgi-bin/uploadcgi.py";
    private TextView mTextView1;
    private TextView mTextView2;
    private Button mButton1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mTextView1 = (TextView) findViewById(R.id.myText2);
        mTextView1.setText("FilePath：\n" + uploadFile);//显示我的文件在哪里
        mTextView2 = (TextView) findViewById(R.id.myText3);
        mTextView2.setText("UploadPath：\n" + actionUrl);//显示上传的文件路径
    /* 设定mButton的onClick事件处理 */
        mButton1 = (Button) findViewById(R.id.myButton);
        mButton1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String testdata="gljtesting2";
               uploadAll(actionUrl,uploadFile,testdata,testdata,testdata,testdata,testdata,testdata);

            }
        });
    }

    private void uploadAll(String dataUrl,String uploadFile1,String gender,String height ,String bust,String hip,String waistline,String ID)
    {
        String end = "\r\n";
        String Hyphens = "--";
        String boundary = "*****";
        try
        {
            URL url = new URL(dataUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();//建立连接
      /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
      /* 设定传送的method=POST */
            con.setRequestMethod("POST");
      /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            String testdata="gljtesting2";
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());

            writethings(ds,Hyphens + boundary + end);
            writethings(ds,"Content-Disposition: form-data; name=\"gender\""  + end + end +testdata
                    + "\r\n");
            writethings(ds, Hyphens + boundary + end);
            writethings(ds, "Content-Disposition: form-data; name=\"height\"" + end + end + testdata
                    + "\r\n");
            writethings(ds, Hyphens + boundary + end);
            writethings(ds, "Content-Disposition: form-data; name=\"bust\"" + end + end + testdata
                    + "\r\n");
            writethings(ds, Hyphens + boundary + end);
            writethings(ds, "Content-Disposition: form-data; name=\"hip\"" + end + end + testdata
                    + "\r\n");
            writethings(ds, Hyphens + boundary + end);
            writethings(ds, "Content-Disposition: form-data; name=\"waistline\"" + end + end + testdata
                    + "\r\n");
            writethings(ds, Hyphens + boundary + end);
            writethings(ds, "Content-Disposition: form-data; name=\"ID\"" + end + end + testdata
                    + "\r\n");



        /*
            ds.writeBytes(Hyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"height\"" + "\"\r\n"+testdata
                    + "\"\r\n");
            ds.writeBytes(Hyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"bust\"" + "\"\r\n"+testdata
                    + "\"\r\n");
            ds.writeBytes(Hyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"hip\"" +"\"\r\n"+ testdata
                    + "\"\r\n");
            ds.writeBytes(Hyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"waistline\"" + "\"\r\n"+testdata
                    + "\"\r\n");
            ds.writeBytes(Hyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"" +"ID"
                    + "\"\r\n");
            ds.writeBytes("\r\n");
            ds.writeBytes( "GLJTEST"+ "\r\n");

*/
            writethings(ds, Hyphens + boundary + end);
            writethings(ds, "Content-Disposition: form-data; " +
                    "name=\"filename\";filename=\"" +
                    newName + "\"" + end + end);
            //设定DataOutputStream

            //\"是转义字符 表示此双引号不是彼双引号


            //取得文件的FileInputStream
            FileInputStream fStream = new FileInputStream(uploadFile1);

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;

            while ((length = fStream.read(buffer)) != -1)
            {
                ds.write(buffer, 0, length);
            }
            writethings(ds,end);
            writethings(ds, Hyphens + boundary + Hyphens + end);
            fStream.close();
            ds.flush();
      /* 取得Response内容 */
            // 在调用下边的getInputStream()函数时才将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1)
            {
                b.append((char) ch);
            }
            System.out.println("上传成功");
            Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_LONG)
                    .show();
            ds.close();
        } catch (Exception e)
        {
            System.out.println("上传失败" + e.getMessage());
            Toast.makeText(MainActivity.this, "上传失败" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    public static void writethings(DataOutputStream ds,String input)
    {
        //System.out.print(input);//see the things for http post payload
        try{
            ds.write(input.getBytes("UTF-8"));
        }
        catch (Exception e)
        {
            System.out.println("error " + e.getMessage());
        }
    }
    public static void main(String[] args) throws Exception {


    }
}