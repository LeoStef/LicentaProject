package com.example.directmed.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.example.directmed.MedicamentActivity;
import com.example.directmed.R;
import com.example.directmed.data.Medicament;
import com.example.directmed.data.MedicamentAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    SearchView searchView;
    ListView listView;
    ArrayList<Medicament> medicaments = new ArrayList<>();
    ArrayList<Medicament> medicamentsFiltered = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = view.findViewById(R.id.search_view);
        listView = view.findViewById(R.id.listViewMed);

        medicaments.add(new Medicament("Nurofen","cap",25.50,"Catena"));
        medicaments.add(new Medicament("Algocalmin","calmare",69.69,"Tei"));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MedicamentActivity.class);
                intent.putExtra("key",medicamentsFiltered.get(position));
                startActivity(intent);
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

}
