package com.example.homeworkcolormood;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView; // Importar TextView
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.Random; // Importar Random

public class Main_ColorsList_Activity extends AppCompatActivity {

    private ListView color_view;
    private TextView titleTextView; // Añadir el TextView para el título

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

        // Inicializar el TextView del título
        titleTextView = findViewById(R.id.title_textview);

        // Cargar los mensajes de bienvenida y seleccionar uno aleatoriamente
        String[] welcomeMessages = getResources().getStringArray(R.array.welcome_messages);
        if (welcomeMessages.length > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(welcomeMessages.length);
            titleTextView.setText(welcomeMessages[randomIndex]);
        }

        color_view = findViewById(R.id.color_view);

        ArrayList<String> colores = new ArrayList<>();
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
