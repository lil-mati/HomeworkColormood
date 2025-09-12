package com.example.homeworkcolormood;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetallesActivity extends AppCompatActivity {

    // Códigos de request
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_STORAGE_PERMISSION = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    // Para saber si tiene foto
    private boolean tieneImagen = false;

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
                    abrirCamara();
                }
            });
        }

        // Boton galeria - NUEVA FUNCIONALIDAD
        if (btnGaleria != null) {
            btnGaleria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirGaleria();
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

    // ===== FUNCIONES DE CÁMARA =====

    private void abrirCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            iniciarCamara();
        }
    }

    private void iniciarCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {

            try {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            } catch (Exception e) {
                Toast.makeText(this, "No se pudo abrir la cámara", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ===== FUNCIONES DE GALERÍA =====

    private void abrirGaleria() {
        String permiso;

        // Para Android 13+ (API 33+) usar el nuevo permiso
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            permiso = Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permiso = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permiso},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            iniciarSeleccionImagen();
        }
    }

    private void iniciarSeleccionImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        } else {
            Toast.makeText(this, "No hay aplicación para seleccionar imágenes", Toast.LENGTH_SHORT).show();
        }
    }

    // ===== MANEJO DE PERMISOS =====

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarCamara();
            } else {
                Toast.makeText(this, "Permiso de cámara necesario", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarSeleccionImagen();
            } else {
                Toast.makeText(this, "Permiso de galería necesario para acceder a las imágenes", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ===== RESULTADOS DE CÁMARA Y GALERÍA =====

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                tieneImagen = true;
                Toast.makeText(this, "¡Foto capturada exitosamente!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Captura cancelada", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_IMAGE_PICK) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                tieneImagen = true;
                Uri selectedImage = data.getData();
                Toast.makeText(this, "¡Imagen seleccionada exitosamente!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selección cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ===== FUNCIONES=====

    private void guardarEstadoDeAnimo(String colorName, String nota) {
        String mensaje;

        if (nota.isEmpty()) {
            mensaje = "¡Estado " + colorName + " guardado!";
        } else {
            mensaje = "¡Estado y nota guardados!\n\"" + nota + "\"";
        }

        // Agregar info sobre imagen si la hay
        if (tieneImagen) {
            mensaje += "\n📸 ¡Con imagen incluida!";
        }

        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

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
        finish();
    }
}