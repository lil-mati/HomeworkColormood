package com.example.homeworkcolormood;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast; // Added import for Toast
import android.widget.AdapterView; // Added import for AdapterView
import android.view.View; // Added import for View
import java.util.ArrayList;

public class Main_ColorsList_Activity extends AppCompatActivity {

    private ListView color_view;
    private ArrayList<String> colores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_colors);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // funcion lista de canciones en el album
        color_view = findViewById(R.id.color_view);

        colores = new ArrayList<>();
        colores.add("Rojo");
        colores.add("Amarillo");
        colores.add("Verde");
        colores.add("Azul");
        colores.add("Morado");
        colores.add("Naranja");

        color_adapter adapter = new color_adapter(this, R.layout.list_colormood, colores);
        color_view.setAdapter(adapter);
    }
}