package com.example.directmed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.directmed.data.DataBase;
import com.example.directmed.data.Medicament;
import com.example.directmed.fragments.DashboardFragment;
import com.squareup.picasso.Picasso;

public class MedicamentActivity extends AppCompatActivity {
    TextView nume;
    TextView categorie;
    TextView pret;
    TextView farmacie;
    ImageView poza;
    Button addButton;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament);
        db = new DataBase(this);
        nume = findViewById(R.id.textViewNume);
        categorie = findViewById(R.id.textViewCategorie);
        pret = findViewById(R.id.textViewPret);
        farmacie = findViewById(R.id.textViewFarmacie);
        poza = findViewById(R.id.imageViewMed);
        final Medicament medicament = getIntent().getParcelableExtra("key");
        addButton = findViewById(R.id.addButton);
        nume.setText(medicament.getName());
        categorie.setText(medicament.getType());
        pret.setText(Double.toString(medicament.getPret()));
        farmacie.setText(medicament.getPharmacy());
        Picasso.get().load(medicament.getImageURL()).into(poza);
        if(getIntent().getExtras().getInt("code") ==100) {
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.getDatabase().medicamentDAO().insertMeds(medicament);

                }
            });
        }else{
           
            addButton.setVisibility(View.GONE);
        }
        }
    }

