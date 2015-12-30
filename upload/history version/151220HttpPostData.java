package nju.ee.beanliu;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostData {


    private static String newName = "GLJTEST.mp3";

    private static String uploadFile = "D:/Piano - Final - Bilibili.mp3";

    private static String actionUrl = "http://visg.nju.edu.cn:8080/cgi-bin/upload_lp.py";
    
    private static void uploadFile()
    {
        String end = "\r\n";
        String Hyphens = "--";
        String boundary = "*****";
        try
        {
            URL url = new URL(actionUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);

            con.setRequestMethod("POST");
            /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

           String testdata="gljtesting";
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());

            writethings(ds,Hyphens + boundary + end);
            writethings(ds,"Content-Disposition: form-data; name=\"gender\""  + end + end +testdata
                    + "\r\n");
            writethings(ds,Hyphens + boundary + end);
            writethings(ds,"Content-Disposition: form-data; name=\"height\"" + end + end+testdata
                    + "\r\n");
            writethings(ds,Hyphens + boundary + end);
            writethings(ds,"Content-Disposition: form-data; name=\"bust\"" + end + end +testdata
                    + "\r\n");
            writethings(ds,Hyphens + boundary + end);
            writethings(ds,"Content-Disposition: form-data; name=\"hip\"" + end + end + testdata
                    + "\r\n");
            writethings(ds,Hyphens + boundary + end);
            writethings(ds,"Content-Disposition: form-data; name=\"waistline\"" + end + end +testdata
                    + "\r\n");
            writethings(ds,Hyphens + boundary + end);
            writethings(ds,"Content-Disposition: form-data; name=\"ID\"" + end + end + testdata
                    + "\r\n");

            writethings(ds,Hyphens + boundary + end);
            writethings(ds,"Content-Disposition: form-data; " +
                    "name=\"filename\";filename=\"" +
                    newName +"\"" + end+end);
            
            FileInputStream fStream = new FileInputStream(uploadFile);

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;

            while ((length = fStream.read(buffer)) != -1)
            {
                ds.write(buffer, 0, length);
            }
            writethings(ds,end);
            writethings(ds,Hyphens + boundary + Hyphens + end);
            fStream.close();
            ds.flush();

            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1)
            {
                b.append((char) ch);
            }
            System.out.println("end  "+b.toString());
            
            ds.close();
        } catch (Exception e)
        {
            System.out.println("error " + e.getMessage());
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
        uploadFile();
    }
}