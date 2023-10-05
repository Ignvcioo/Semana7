package com.example.semana7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        String nombre = getIntent().getStringExtra("Nombre_de_la_foto");
        TextView textView = findViewById(R.id.textView);
        textView.setText("Nombre de la foto: " + nombre);
    }
}