package com.example.homeworkcolormood;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetallesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        TextView tituloMood = findViewById(R.id.titulo_mood);
        View colorMostrar = findViewById(R.id.color_mostrar); 
        TextView fechaTextView = findViewById(R.id.fecha); 
        Button btnOmitir = findViewById(R.id.btn_omitir);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("'Hoy,' dd 'de' MMMM", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        fechaTextView.setText(currentDate);

        String selectedColorName = getIntent().getStringExtra("SELECTED_COLOR");
        int colorToDisplay = Color.LTGRAY; // Color por defecto

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

        // fondo circular color
        if (colorMostrar.getBackground() instanceof GradientDrawable) {
            GradientDrawable background = (GradientDrawable) colorMostrar.getBackground().mutate(); // Usar mutate() para no afectar otros drawables
            background.setColor(colorToDisplay);
        } else {
            GradientDrawable newCircularBackground = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.circular_view_background).mutate();
            newCircularBackground.setColor(colorToDisplay);
            colorMostrar.setBackground(newCircularBackground);
        }

        // boton omitir
        if (btnOmitir != null) {
            btnOmitir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); 
                }
            });
        }
    }
}
