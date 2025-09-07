package com.example.homeworkcolormood;

import android.content.Context; // Importar Context
import android.view.LayoutInflater; // Importar LayoutInflater
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView; // Importar TextView
import java.util.ArrayList; // Importar ArrayList

public class color_adapter extends BaseAdapter{
    private final Context context;
    private final int layoutResource;
    private final ArrayList<String> colores;

    public color_adapter(Context context, int layoutResource, ArrayList<String> colores) {
        this.context = context;
        this.layoutResource = layoutResource;
        this.colores = colores;
    }

    @Override
    public int getCount() {
        if (this.colores != null) {
            return this.colores.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (this.colores != null) {
            return this.colores.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            v = inflater.inflate(R.layout.list_colormood, parent, false);
        }

        String colorActual = null;
        if (this.colores != null && position < this.colores.size()) {
            colorActual = colores.get(position);
        }

        TextView txt_color = v.findViewById(R.id.txt_color);

        if (colorActual != null) {
            txt_color.setText(colorActual);
        } else {
            txt_color.setText("");
        }

        return v;
    }
}
