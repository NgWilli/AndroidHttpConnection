package com.url_connection2.url_connection2;

        import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.BufferedReader;
        import java.io.FileOutputStream;
        import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 *
 * @Project: Android_MyDownload
 * @Desciption: 利用Http协议下载文件并存储到SDCard
1.创建一个URL对象
2.通过URL对象,创建一个HttpURLConnection对象
3.得到InputStream
4.从InputStream当中读取数据
存到SDCard
1.取得SDCard路径
2.利用读取大文件的IO读法，读取文件
 *
 * @Author: LinYiSong
 * @Date: 2011-3-25~2011-3-25
 */
public class MainActivity extends Activity {

    private Button downFileBtn;
    private Button downMP3Btn;
    private Context context = this;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        downFileBtn=(Button)this.findViewById(R.id.downFile);
        downMP3Btn=(Button)this.findViewById(R.id.downMP3);

        downFileBtn.setOnClickListener(new DownFileClickListener());
        downMP3Btn.setOnClickListener(new DownMP3ClickListener());
    }

    /**
     *
     * @Project: Android_MyDownload
     * @Desciption: 只能读取文本文件，读取mp3文件会出现内存溢出现象
     * @Author: LinYiSong
     * @Date: 2011-3-25~2011-3-25
     */
    class DownFileClickListener implements OnClickListener{
        @Override
        public void onClick(View v) {
            String urlStr="http://img.hoop8.com/attachments/1511/8953708168362.txt";
            try {
                /*
                 * 通过URL取得HttpURLConnection
                 * 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
                 * <uses-permission android:name="android.permission.INTERNET" />
                 */
                URL url=new URL(urlStr);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                //取得inputStream，并进行读取
                InputStream input=conn.getInputStream();
                BufferedReader in=new BufferedReader(new InputStreamReader(input));
                String line=null;
                StringBuffer sb=new StringBuffer();
                while((line=in.readLine())!=null){
                    sb.append(line);
                }
                System.out.println(sb.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     *
     * @Project: Android_MyDownload
     * @Desciption: 读取任意文件，并将文件保存到手机SDCard
     * @Author: LinYiSong
     * @Date: 2011-3-25~2011-3-25
     */
    void downAll(String usrId)
    {
        String urlOri="http://visg.nju.edu.cn:8080/responseData/objResults/";
        fileDownloader(urlOri+usrId+"/"+"FullBodyWithImage.mat","FullBodyWithImage.mat");
        fileDownloader(urlOri+usrId+"/"+"FullBodyWithImage.mtl","FullBodyWithImage.mtl");
        fileDownloader(urlOri+usrId+"/"+"FullBodyWithImage.obj","FullBodyWithImage.obj");
        fileDownloader(urlOri+usrId+"/"+"FullBodyWithImage.png","FullBodyWithImage.png");
    }
    void fileDownloader(String urlTemp , String fileTemp)
    {
        // String urlStr="http://visg.nju.edu.cn:8080/SCAPE_Body_Database/Female/Female_Body_Obj/Female_Body_Height150_ChestLine100_WaistLine70_HipLine101.obj";
        String urlStr=urlTemp;
        String filename = fileTemp;
        FileOutputStream out = null;
        try {
                /*
                 * 通过URL取得HttpURLConnection
                 * 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
                 * <uses-permission android:name="android.permission.INTERNET" />
                 */
            URL url=new URL(urlStr);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            //取得inputStream，并将流中的信息写入SDCard
            InputStream is = conn.getInputStream();
            out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            //   out.write(filecontent.getBytes("UTF-8"));

            //创建文件
            // boolean a=   file.createNewFile();
            // fos = new FileOutputStream(file);
            byte[] buf = new byte[1];
            while ((is.read(buf)) != -1) {
                out.write(buf);
                //同步更新数据
                out.flush();
            }
            is.close();





        } catch (Exception e) {

        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    class DownMP3ClickListener implements OnClickListener{

        @Override
        public void onClick(View v) {
           downAll("lp");
        }

    }

}