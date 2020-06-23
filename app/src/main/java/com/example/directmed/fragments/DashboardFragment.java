package com.example.directmed.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.directmed.MedicamentActivity;
import com.example.directmed.R;
import com.example.directmed.ResultsActivity;
import com.example.directmed.data.DataBase;
import com.example.directmed.data.JsonParser;
import com.example.directmed.data.Medicament;
import com.example.directmed.data.MedicamentAdapter;
import com.example.directmed.data.ResultItem;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    ListView listView;
    DataBase db = DataBase.getInstance(getActivity());
    List<Medicament> storedMeds = new ArrayList<>();
    ArrayList<ResultItem> results = new ArrayList<>();
    Button resetButton;
    Button routeButton;
    RequestQueue nQueue;
    ArrayList<Double> userCoordinates = new ArrayList<>();
    String distanceResult;
    TextView textViewTotal;

    public DashboardFragment() {
        distanceResult = "";

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        nQueue = Volley.newRequestQueue(this.getContext());


        GetUserLocation();
        GetResults("https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyDt4IhKiNRziVzTfjHPxa-dDEfWrS4ITMY&input=catena&inputtype=textquery&fields=formatted_address&location="+ userCoordinates.get(0) +
                "," + userCoordinates.get(1) +"&radius=1000",userCoordinates);
        results = GetDistances(results);
       // GETLocations("https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyDt4IhKiNRziVzTfjHPxa-dDEfWrS4ITMY&input=catena&inputtype=textquery&fields=formatted_address","catena");
        listView = view.findViewById(R.id.listViewHistory);
        storedMeds = db.getDatabase().medicamentDAO().getAll();
        textViewTotal = view.findViewById(R.id.textTotal);
        Double total = 0.0;
        for(Medicament medicament : storedMeds){
            total+= medicament.getPret();
        }
        textViewTotal.setText(Double.toString((total.shortValue())));
        MedicamentAdapter adapter = new MedicamentAdapter(getActivity(), R.layout.adapter_medicament, storedMeds);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MedicamentActivity.class);
                intent.putExtra("key", storedMeds.get(position));
                startActivity(intent);
            }
        });
        //routeButton
        routeButton = view.findViewById(R.id.button_route);
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ResultsActivity.class);
                intent.putParcelableArrayListExtra("results", results);
                intent.putExtra("lat",userCoordinates.get(0));
                intent.putExtra("lng",userCoordinates.get(1));
                startActivity(intent);
            }
        });
        //resetButton
        resetButton = view.findViewById(R.id.button_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.getDatabase().clearAllTables();
                storedMeds.clear();
                textViewTotal.setText("0");
                MedicamentAdapter adapter = new MedicamentAdapter(getActivity(), R.layout.adapter_medicament, storedMeds);
                listView.setAdapter(adapter);
            }
        });


        return view;
    }
    private void GetUserLocation() {
        LocationManager lm = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        userCoordinates.add(0,location.getLatitude());
        userCoordinates.add(1,location.getLongitude());
    }


    private void GetResults(String url,ArrayList<Double> userCoordinates){

        try {
            String json = new JsonParser(){
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                }
            }.execute(new URL(url)).get();
            results = new JsonParser().parseJSONLocations(json);




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private ArrayList<ResultItem> GetDistances(ArrayList<ResultItem> results){

        for(ResultItem resultItem:results){
            String json = null;
            try {

                json = new JsonParser(){
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                    }
                }.execute(new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" +userCoordinates.get(0) +
                        ","+ userCoordinates.get(1) + "&destinations="+resultItem.getLat() + "," + resultItem.getLng() +"&key=AIzaSyDt4IhKiNRziVzTfjHPxa-dDEfWrS4ITMY")).get();
                resultItem.setDistance(new JsonParser().parseJSONDistance(json));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        return results;

    }


}

//https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=&destinations=N&key=AIzaSyDt4IhKiNRziVzTfjHPxa-dDEfWrS4ITMY