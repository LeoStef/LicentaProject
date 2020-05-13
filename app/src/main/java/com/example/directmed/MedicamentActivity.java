package com.example.directmed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.directmed.data.Medicament;
import com.squareup.picasso.Picasso;

public class MedicamentActivity extends AppCompatActivity {
    TextView nume;
    TextView categorie;
    TextView pret;
    TextView farmacie;
    ImageView poza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament);
        nume = findViewById(R.id.textViewNume);
        categorie = findViewById(R.id.textViewCategorie);
        pret = findViewById(R.id.textViewPret);
        farmacie = findViewById(R.id.textViewFarmacie);
        poza = findViewById(R.id.imageViewMed);
        Medicament medicament = getIntent().getParcelableExtra("key");
        nume.setText(medicament.getName());
        categorie.setText(medicament.getType());
        pret.setText(Double.toString(medicament.getPret()));
        farmacie.setText(medicament.getPharmacy());
        Picasso.get().load(medicament.getImageURL()).into(poza);

    }
}
