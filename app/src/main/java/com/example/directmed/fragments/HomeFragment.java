package com.example.directmed.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.directmed.MedicamentActivity;
import com.example.directmed.R;
import com.example.directmed.data.DataBase;
import com.example.directmed.data.Medicament;
import com.example.directmed.data.MedicamentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    SearchView searchView;
    ListView listView;
    ArrayList<Medicament> medicaments = new ArrayList<>();
    ArrayList<Medicament> medicamentsFiltered = new ArrayList<>();
    DataBase db;
    RequestQueue mQueue;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        db = DataBase.getInstance(getActivity());
        searchView = view.findViewById(R.id.search_view);
        listView = view.findViewById(R.id.listViewMed);
        mQueue = Volley.newRequestQueue(this.getContext());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MedicamentActivity.class);
                intent.putExtra("key",medicamentsFiltered.get(position));
                intent.putExtra("code",100);
               // db.getDatabase().medicamentDAO().insertMeds(medicamentsFiltered.get(position));
                startActivityForResult(intent,100);
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonListUpdate("http://192.168.194.249:5000/catena");
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText !=null){
                    if(newText.length() > 0 ){
                        medicamentsFiltered.clear();
                        for(int i = 0;i<medicaments.size();i++){
                            if(medicaments.get(i).getName().toLowerCase().contains(newText.toLowerCase())){
                                medicamentsFiltered.add(medicaments.get(i));                            }
                        }
                    }else {
                        medicamentsFiltered.clear();
                    }

                    MedicamentAdapter adapter = new MedicamentAdapter(getActivity(),R.layout.adapter_medicament,medicamentsFiltered);
                    listView.setAdapter(adapter);
                }
                return false;
            }
        });


        return view;
    }

    private void jsonListUpdate(String url) {
            final String switchURL = url;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("meds");

                    for (int i = 0;i < jsonArray.length();i++){
                        JSONObject med = jsonArray.getJSONObject(i);
                        String name = med.getString("name");
                        String type = med.getString("categorie");
                        String pret = med.getString("pret");
                        String farmacie = "farmacie";
                        switch (switchURL) {
                            case "http://192.168.194.249:5000/catena":
                                farmacie = "Catena";
                        }

                        String imageURL = med.getString("pic");
                        if (Double.parseDouble(pret) != 1.0) {
                            if(name.length()>25){
                                name = name.substring(0,25);
                                name = name + "...";
                            }
                            Medicament medicament = new Medicament(name, type, Double.parseDouble(pret), "catena", imageURL);
                            medicaments.add(medicament);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }


}
