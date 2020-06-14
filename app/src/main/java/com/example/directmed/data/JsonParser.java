package com.example.directmed.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.directmed.data.ResultItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonParser extends AsyncTask<URL,Void,String> {


    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) urls[0].openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader =new BufferedReader(inputStreamReader);
            StringBuilder result= new StringBuilder();
            String line ="";
            while ((line = reader.readLine()) !=null) {
                result.append(line);
            }
            reader.close();
            inputStreamReader.close();
            inputStream.close();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection !=null){
                connection.disconnect();
            }
        }
        return null;
    }

    public ArrayList<ResultItem> parseJSONLocations(String json){
        try {
            ArrayList<ResultItem> results = new ArrayList<>();
            JSONObject response = new JSONObject(json);
            JSONArray jsonArray = response.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);
                String name = result.getString("formatted_address");
                JSONObject geometry = result.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                String lat = location.getString("lat");
                String lng = location.getString("lng");
                results.add(new ResultItem(name,lat,lng));
            }
            return results;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String parseJSONDistance(String json){
        try {
            String distanceResult = "0km";
            JSONObject response = new JSONObject(json);
            String responseStatus = response.getString("status");
            if(responseStatus.equals("ZERO_RESULTS")){
                Log.v("DISTANCE","ZERO RESULTS");
                return distanceResult + "error" ;
            }else{
                    JSONArray rows = response.getJSONArray("rows");
                    JSONObject container = rows.getJSONObject(0);
                    JSONArray distanceContainer = container.getJSONArray("elements");
                    JSONObject distanceContainer2 = distanceContainer.getJSONObject(0);
                    JSONObject distanceContainer3 = distanceContainer2.getJSONObject("distance");
                     distanceResult = distanceContainer3.getString("text");
                    return distanceResult;
                }
            } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return "0 km";
    }

    public String parseJSONPolyline(String json){
        try {
            JSONObject response = new JSONObject(json);
            JSONArray routes = response.getJSONArray("routes");
            JSONObject routes0 = routes.getJSONObject(0);
            JSONObject poly = routes0.getJSONObject("overview_polyline");
            String points = poly.getString("points");
            return points;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "BIGERROR";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
