package com.example.directmed.data;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class JSONParser extends AsyncTask<URL,Void,String> {


    @Override
    protected String doInBackground(URL... urls) {

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) urls[0].openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = reader.readLine())!= null){
                result.append(line);
            }
            reader.close();
            inputStreamReader.close();
            inputStream.close();
            return result.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
        }


        return null;
    }
    public List<Medicament> parseJSON(String json){

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Medicament> medicamentList = Arrays.asList(mapper.readValue(json,Medicament[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}

