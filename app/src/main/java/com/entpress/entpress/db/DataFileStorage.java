package com.entpress.entpress.db;

import android.content.Context;
import android.util.Log;

import com.entpress.entpress.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ray on 14/05/2018.
 * class for storing posts, comments data
 */

public class DataFileStorage {

    private Context context;

    public DataFileStorage(Context context){
        this.context = context;
        //createFiles();
    }


    private String readFile(String fileName){
        String content = "";
        BufferedReader input = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(fileName);
            input = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }
            content = buffer.toString();
            Log.e(fileName, buffer.toString());
        } catch (IOException e) {
            Log.e(fileName,e.getMessage());
            e.printStackTrace();
        }
        return content;
    }

    public void writeFile(String fileName, String content){
        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearData(String fileName){
        //set an empty string to clear file contents
        writeFile(fileName,"");
    }

    public void deleteData(String fileName, int tag){
        String content = readFile(fileName);
        if(!content.equalsIgnoreCase("")){
            JSONArray uploadsArray = null;
            try {
                uploadsArray = new JSONArray(content);
                for(int i=0; i<uploadsArray.length(); i++){
                    JSONObject data = uploadsArray.getJSONObject(i);
                    if(data.getString("tag").equalsIgnoreCase(String.valueOf(tag))){
                        uploadsArray.remove(i);
                    }
                }
                writeFile(fileName,uploadsArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
