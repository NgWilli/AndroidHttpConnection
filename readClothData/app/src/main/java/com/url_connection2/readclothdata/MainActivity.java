package com.url_connection2.readclothdata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;
class dataBlock
{   Vector<String> Gender;
    Vector<String> Name;
    Vector<String> Size;
    Vector<String> Color;
    Vector<String> NumberOfFigures;

    dataBlock()
    {

        Gender=new Vector<>();
        Name=new Vector<>();
        Size=new Vector<>();
        Color=new Vector<>();
        NumberOfFigures=new Vector<>();
    }

}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         String filename="/data/data/com.url_connection2.readclothdata/cloth.txt";
        textReader(filename);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    Vector<dataBlock> textReader(String filePath)//Gender,Name,Size,Color,NumberOfFigures
    {

        Vector<dataBlock> result=new Vector<>();

        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    if(lineTxt.equals("#"))/* data begin */
                    {
                        //new data
                        dataBlock newCloth=new dataBlock();

                        lineTxt = bufferedReader.readLine();
                        //gender
                        lineTxt = lineTxt.replaceAll("Gender:", "");//去掉关键字
                        while(!lineTxt.equals("")) {
                            String aPara = lineTxt.substring(0, lineTxt.indexOf(";"));//取出第一个关键字
                            newCloth.Gender.add(aPara);
                            lineTxt = lineTxt.replaceFirst(aPara + ";", "");//去掉关键字
                        }

                        lineTxt = bufferedReader.readLine();
                        //Name:
                        lineTxt = lineTxt.replaceAll("Name:", "");//去掉关键字
                        while(!lineTxt.equals("")) {
                            String aPara = lineTxt.substring(0, lineTxt.indexOf(";"));//取出第一个关键字
                            newCloth.Name.add(aPara);
                            lineTxt = lineTxt.replaceFirst(aPara + ";", "");//去掉关键字
                        }
                        lineTxt = bufferedReader.readLine();
                        //Size:
                        lineTxt = lineTxt.replaceAll("Size:", "");//去掉关键字
                        while(!lineTxt.equals("")) {
                            String aPara = lineTxt.substring(0, lineTxt.indexOf(";"));//取出第一个关键字
                            newCloth.Size.add(aPara);
                            lineTxt = lineTxt.replaceFirst(aPara + ";", "");//去掉关键字
                        }
                        lineTxt = bufferedReader.readLine();
                        //Color:
                        lineTxt = lineTxt.replaceAll("Color:", "");//去掉关键字
                        while(!lineTxt.equals("")) {
                            String aPara = lineTxt.substring(0, lineTxt.indexOf(";"));//取出第一个关键字
                            newCloth.Color.add(aPara);
                            lineTxt = lineTxt.replaceFirst(aPara + ";", "");//去掉关键字
                        }
                        lineTxt = bufferedReader.readLine();
                        //NumberOfFigures:
                        lineTxt = lineTxt.replaceAll("NumberOfFigures:", "");//去掉关键字
                        while(!lineTxt.equals("")) {
                            String aPara = lineTxt.substring(0, lineTxt.indexOf(";"));//取出第一个关键字
                            newCloth.NumberOfFigures.add(aPara);
                            lineTxt = lineTxt.replaceFirst(aPara + ";", "");//去掉关键字
                        }
                        //finished
                        lineTxt = bufferedReader.readLine();
                        if(lineTxt.equals("##"))
                        {
                            result.add(newCloth);
                        }
                        else
                        {
                            System.out.println("reading error:no enough lines of data!");
                            break;
                        }
                    }

                }
                //testing
                for(int l=0;l<result.size();l++)
                {
                    for(int m=0;m<(result.elementAt(l)).Gender.size();m++)
                    {
                        System.out.println(result.elementAt(l).Gender.elementAt(m));
                    }
                    for(int m=0;m<(result.elementAt(l)).Name.size();m++)
                    {
                        System.out.println(result.elementAt(l).Name.elementAt(m));
                    }
                    for(int m=0;m<(result.elementAt(l)).Size.size();m++)
                    {
                        System.out.println(result.elementAt(l).Size.elementAt(m));
                    }
                    for(int m=0;m<(result.elementAt(l)).Color.size();m++)
                    {
                        System.out.println(result.elementAt(l).Color.elementAt(m));
                    }
                    for(int m=0;m<(result.elementAt(l)).NumberOfFigures.size();m++)
                    {
                        System.out.println(result.elementAt(l).NumberOfFigures.elementAt(m));
                    }
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return result;
    }
}
