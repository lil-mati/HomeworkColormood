package com.example.homeworkcolormood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormularioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_formulario);

        EditText nombre = findViewById(R.id.editNombre);
        EditText edad = findViewById(R.id.editEdad);
        EditText color = findViewById(R.id.editColor);
        EditText emocion = findViewById(R.id.editEmocion);
        Button cambio = findViewById(R.id.cambio);

        cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nombre.getText().toString();
                String age = edad.getText().toString();
                String colorin = color.getText().toString();
                String emociones = emocion.getText().toString();

                Intent i = new Intent(FormularioActivity.this, ActivityProfile.class);

                i.putExtra("nombre", name);
                i.putExtra("edad", age);
                i.putExtra("color", colorin);
                i.putExtra("emocion", emociones);

                startActivity(i);
            }
        });

    }
}