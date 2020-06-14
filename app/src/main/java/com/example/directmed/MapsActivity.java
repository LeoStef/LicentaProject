package com.example.directmed;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.directmed.data.JsonParser;
import com.example.directmed.data.Medicament;
import com.example.directmed.data.ResultItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double latitude;
    Double longitude;
    LatLng myLocation;
    LatLng destination;
    ResultItem resultItem;
    List<LatLng> polyPath;
    Polyline line;
    PolylineOptions options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        latitude =  getIntent().getExtras().getDouble("lat");
        longitude = getIntent().getExtras().getDouble("lng");
        resultItem = getIntent().getExtras().getParcelable("result");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myLocation = new LatLng(latitude,longitude);
        destination = new LatLng(Double.parseDouble(resultItem.getLat()),Double.parseDouble(resultItem.getLng()));
        polyPath = GetPath(myLocation,destination);
        PolylineOptions options = new PolylineOptions().width(15).color(Color.BLUE).geodesic(true);
        for(int i =0;i<polyPath.size();i++){
            LatLng point = polyPath.get(i);
            options.add(point);
        }

        LatLng cameraLocation = new LatLng((myLocation.latitude + destination.latitude)/2,(myLocation.longitude + destination.longitude)/2);


        mMap.addMarker(new MarkerOptions().position(myLocation).title("Start"));
        mMap.addMarker(new MarkerOptions().position(destination));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraLocation,15 ));
        line = mMap.addPolyline(options);
        Log.v("POINTS",line.getPoints().toString());




    }


    private String getURL(LatLng origin,LatLng destination,String directionMode){
        //Origin
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        //Destination
        String str_destination = "destination=" + destination.latitude + "," + destination.longitude;
        //Mode
        String mode = "mode=" + directionMode;
        //String parameters
        String parameters = str_origin + "&" + str_destination + "&" + mode;
        //Format
        String output = "json";
        //Final URL
        String url = "https://maps.googleapis.com/maps/api/directions/"+output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;

    }

    private List<LatLng> GetPath(LatLng origin,LatLng destination){
       String  url = getURL(origin,destination,"driving");
        try {
            String json = new JsonParser().execute(new URL(url)).get();
            String parsedJson = new JsonParser().parseJSONPolyline(json);
            List<LatLng> decodedPath = PolyUtil.decode(parsedJson);
            return decodedPath;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
