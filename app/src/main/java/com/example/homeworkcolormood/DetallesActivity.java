package com.example.homeworkcolormood;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
public class DetallesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        //elementos xml
        TextView tituloMood = findViewById(R.id.titulo_mood);
        View colorMostrar = findViewById(R.id.color_mostrar); 
        TextView fechaTextView = findViewById(R.id.fecha); 
        Button btnOmitir = findViewById(R.id.btn_omitir);
        Button btnCamara = findViewById(R.id.btn_camara);
        Button btnGaleria = findViewById(R.id.btn_galeria);
        Button btnGuardar = findViewById(R.id.btn_guardar);
        TextInputEditText notaEditText = findViewById(R.id.nota);

        // configurar fecha
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("'Hoy,' dd 'de' MMMM", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        fechaTextView.setText(currentDate);

        //recibir datos de color seleccionado
        String selectedColorName = getIntent().getStringExtra("SELECTED_COLOR");
        int colorToDisplay = Color.LTGRAY; // Color por defecto

        //asignar color segun el nombre
        if (selectedColorName != null) {
            tituloMood.setText("Emoción: " + selectedColorName);

            switch (selectedColorName.toLowerCase()) {
                case "rojo":
                    colorToDisplay = Color.RED;
                    break;
                case "amarillo":
                    colorToDisplay = Color.YELLOW;
                    break;
                case "verde":
                    colorToDisplay = Color.GREEN;
                    break;
                case "azul":
                    colorToDisplay = Color.BLUE;
                    break;
                case "morado":
                    colorToDisplay = Color.rgb(128, 0, 128); 
                    break;
                case "naranja":
                    colorToDisplay = Color.rgb(255, 165, 0); 
                    break;
            }
        }

        // fondo circular con el color
        aplicarColorCircular(colorMostrar, colorToDisplay);

        // Configurar botones
        configurarBotones(btnCamara, btnGaleria, btnOmitir, btnGuardar, notaEditText, selectedColorName);
    }

    private void aplicarColorCircular(View colorMostrar, int colorToDisplay) {
        if (colorMostrar.getBackground() instanceof GradientDrawable) {
            GradientDrawable background = (GradientDrawable) colorMostrar.getBackground().mutate();
            background.setColor(colorToDisplay);
        } else {
            GradientDrawable newCircularBackground = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.circular_view_background).mutate();
            newCircularBackground.setColor(colorToDisplay);
            colorMostrar.setBackground(newCircularBackground);
        }
    }

    private void configurarBotones(Button btnCamara, Button btnGaleria, Button btnOmitir, Button btnGuardar, EditText notaEditText, String selectedColorName) {

        // Boton camara
        if (btnCamara != null) {
            btnCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetallesActivity.this, "Función de cámara próximamente", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Boton galeria
        if (btnGaleria != null) {
            btnGaleria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetallesActivity.this, "Función de galería próximamente", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Boton omitir
        if (btnOmitir != null) {
            btnOmitir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetallesActivity.this, "Omitiendo detalles...", Toast.LENGTH_SHORT).show();
                    volverAlInicio();
                }
            });
        }

        // Boton guardar
        if (btnGuardar != null) {
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nota = notaEditText != null ? notaEditText.getText().toString().trim() : "";
                    guardarEstadoDeAnimo(selectedColorName, nota);
                }
            });
        }
    }

    private void guardarEstadoDeAnimo(String colorName, String nota) {
        // base de datos mas adelante
        String mensaje;

        if (nota.isEmpty()) {
            mensaje = "¡Estado " + colorName + " guardado!";
        } else {
            mensaje = "¡Estado y nota guardados!\n\"" + nota + "\"";
        }

        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

        // para que el usuario lea el mensaje
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                volverAlInicio();
            }
        }, 1500);
    }

    private void volverAlInicio() {
        Intent intent = new Intent(this, Main_ColorsList_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Cerrar  Activity
    }


}