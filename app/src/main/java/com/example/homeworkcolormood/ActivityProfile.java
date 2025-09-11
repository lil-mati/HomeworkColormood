package com.example.homeworkcolormood;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityProfile extends AppCompatActivity {

    public TextView verNombre, verAños, verFav, verEmocion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        ImageButton cancelar = findViewById(R.id.btnCancelar);
        Button editar = findViewById(R.id.btnEditar);
        verNombre = findViewById(R.id.txtNombre);
        verAños = findViewById(R.id.txtAños);
        verFav = findViewById(R.id.txtFavorito);
        verEmocion = findViewById(R.id.txtEmocion);

        cancelar.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityProfile.this, Main_ColorsList_Activity.class);
            startActivity(intent);
        });

        editar.setOnClickListener(view -> {
            Intent x = new Intent(ActivityProfile.this, FormularioActivity.class);
            startActivity(x);
        });

        mostrarCambios();

    }

    private void mostrarCambios() {
        Bundle cambios = this.getIntent().getExtras();
        if (cambios != null) {
            String nombre = cambios.getString("name");
            String años = cambios.getString("age");
            String fav = cambios.getString("color");
            String emocion = cambios.getString("emotion");

            verNombre.setText(nombre);
            verAños.setText(años);
            verFav.setText(fav);
            verEmocion.setText(emocion);
        }
    }
}