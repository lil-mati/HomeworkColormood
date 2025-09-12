package com.example.homeworkcolormood;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityProfile extends AppCompatActivity {

    public TextView verNombre, verAños, verFav, verEmocion;
    private static final int REQUEST_PERMISSION = 100;

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
        Button galeria = findViewById(R.id.btnGaleria);

        galeria.setOnClickListener(view -> {
            Intent y = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(y, 3);
        });

        cancelar.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityProfile.this, Main_ColorsList_Activity.class);
            startActivity(intent);
        });

        editar.setOnClickListener(view -> {
            Intent z = new Intent(ActivityProfile.this, FormularioActivity.class);
            startActivity(z);
        });

        mostrarCambios();
    }
    private void mostrarCambios() {
        Bundle cambios = this.getIntent().getExtras();
        String nombre = null;
        if (cambios != null) {
            nombre = cambios.getString("name");
        }
        String años = null;
        if (cambios != null) {
            años = cambios.getString("age");
        }
        String fav = null;
        if (cambios != null) {
            fav = cambios.getString("color");
        }
        String emocion = null;
        if (cambios != null) {
            emocion = cambios.getString("emotion");
        }

        verNombre.setText(nombre);
        verAños.setText(años);
        verFav.setText(fav);
        verEmocion.setText(emocion);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            Uri seleccion = data.getData();
            ImageView imagen = findViewById(R.id.imgIcon);
            imagen.setImageURI(seleccion);
        }
    }

}