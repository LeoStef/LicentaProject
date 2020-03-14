package com.example.directmed.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.directmed.R;

import java.util.List;

public class MedicamentAdapter extends ArrayAdapter<Medicament> {
    Context ctx;
    int res;

    public MedicamentAdapter(@NonNull Context context, int resource, @NonNull List<Medicament> objects) {
        super(context, resource, objects);
        res = resource;
        ctx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(ctx).inflate(res,null);
        TextView name  = view.findViewById(R.id.text_medicament_name);
        TextView type = view.findViewById(R.id.text_medicament_type);
        TextView price = view.findViewById(R.id.text_medicament_price);
        TextView farmacie = view.findViewById(R.id.text_medicament_farmacie);
        Medicament medicament = getItem(position);
        name.setText(medicament.getName());
        type.setText(medicament.getType());
        price.setText(medicament.getPrice() + "");
        farmacie.setText(medicament.getFarmacie());
        return view;
    }
}
