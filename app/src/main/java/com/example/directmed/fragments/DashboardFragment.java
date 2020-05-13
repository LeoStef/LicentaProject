package com.example.directmed.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.room.Dao;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.directmed.MedicamentActivity;
import com.example.directmed.R;
import com.example.directmed.data.DataBase;
import com.example.directmed.data.LocalDatabase;
import com.example.directmed.data.Medicament;
import com.example.directmed.data.MedicamentAdapter;
import com.example.directmed.data.MedicamentDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    ListView listView;
    DataBase db = DataBase.getInstance(getActivity());
    List<Medicament> storedMeds = new ArrayList<>();
    Button resetButton;
    public DashboardFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        listView = view.findViewById(R.id.listViewHistory);
        storedMeds = db.getDatabase().medicamentDAO().getAll();
        MedicamentAdapter adapter = new MedicamentAdapter(getActivity(),R.layout.adapter_medicament,storedMeds);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MedicamentActivity.class);
                intent.putExtra("key",storedMeds.get(position));
                startActivity(intent);
            }
        });
        resetButton = view.findViewById(R.id.button_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.getDatabase().clearAllTables();
                storedMeds.clear();
                MedicamentAdapter adapter = new MedicamentAdapter(getActivity(),R.layout.adapter_medicament,storedMeds);
                listView.setAdapter(adapter);
            }
        });



        return view;
    }

}