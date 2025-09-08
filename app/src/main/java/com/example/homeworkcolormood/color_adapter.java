package com.example.homeworkcolormood;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class color_adapter extends BaseAdapter {

    private final Context context;
    private final int layoutResource;
    private final ArrayList<String> colores;

    public color_adapter(Context context, int layoutResource, ArrayList<String> colores) {
        this.context = context;
        this.layoutResource = layoutResource;
        this.colores = colores;
    }

    @Override public int getCount() { return colores.size(); }
    @Override public Object getItem(int position) { return colores.get(position); }
    @Override public long getItemId(int position) { return position; }

    static class ViewHolder {
        ImageView imgColor;
        TextView txtColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutResource, parent, false);
            h = new ViewHolder();
            h.imgColor = convertView.findViewById(R.id.img_color);
            h.txtColor = convertView.findViewById(R.id.txt_color);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        String nombre = colores.get(position);
        h.txtColor.setText(nombre);

        // Crear círculo pintado
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(colorForName(nombre));
        circle.setSize(dp(56), dp(56));         // tamaño del círculo
        circle.setStroke(dp(1), 0x66FFFFFF);    // borde suave opcional
        h.imgColor.setImageDrawable(circle);

        return convertView;
    }

    private int dp(int dp) {
        float d = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * d);
    }

    private int colorForName(String name) {
        String n = name == null ? "" : name.trim().toLowerCase(Locale.ROOT);
        switch (n) {
            case "rojo":      return Color.parseColor("#E53935");
            case "amarillo":  return Color.parseColor("#FDD835");
            case "verde":     return Color.parseColor("#43A047");
            case "azul":      return Color.parseColor("#1E88E5");
            case "morado":
            case "violeta":   return Color.parseColor("#8E24AA");
            case "naranja":   return Color.parseColor("#FB8C00");

            default:          return Color.parseColor("#9E9E9E"); // fallback
        }
    }
}

