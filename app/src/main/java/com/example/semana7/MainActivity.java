package com.example.semana7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText nombreFoto;
    private Button botonAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreFoto = findViewById(R.id.txtNombre);
        botonAceptar = findViewById(R.id.btnAceptar);

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreFotografia = nombreFoto.getText().toString();
                Intent intent = new Intent(MainActivity.this, Inicio.class);
                intent.putExtra("Nombre_de_la_foto", nombreFotografia);
                startActivity(intent);
            }
        });
    }
}