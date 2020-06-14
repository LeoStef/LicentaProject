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

public class ResultAdapter extends ArrayAdapter<ResultItem> {
    Context ctx;
    int res;

    public ResultAdapter(@NonNull Context context, int resource, @NonNull List<ResultItem> objects) {
        super(context, resource, objects);
        ctx = context;
        res = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(ctx).inflate(res,null);
        TextView name  = view.findViewById(R.id.textViewResultName);
        TextView Distance = view.findViewById(R.id.textViewResultDistance);
        ResultItem resultItem = getItem(position);
        name.setText(resultItem.getAddress());
        Distance.setText(resultItem.getDistance());
        return view;
    }
}
