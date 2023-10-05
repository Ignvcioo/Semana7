package com.ignacio.uber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogearseActividad extends AppCompatActivity {

    // Declaración de variables para los botones y input
    TextInputEditText email;
    TextInputEditText contrasenia;
    Button loguearse;
    Button registrarse;

    FirebaseAuth autenticacion; // Autenticación de Firebase
    DatabaseReference basedatos; // Referencia a la base de datos de Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_logearse);

        // Inicialización los input y botones mediante sus IDs
        email = findViewById(R.id.txtEmail);
        contrasenia = findViewById(R.id.txtContrasenia);
        loguearse = findViewById(R.id.btnIniciarSesion);
        registrarse = findViewById(R.id.btnRegistrarse);

        // Inicialización de la autenticación de Firebase
        autenticacion = FirebaseAuth.getInstance();

        // Inicialización de la referencia a la base de datos de Firebase
        basedatos = FirebaseDatabase.getInstance().getReference();

        // Configuración del OnClickListener para el botón de inicio de sesión
        loguearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion(); // Llamada al método para iniciar sesión
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantallaRegistro();
            }
        });
    }

    // Método para iniciar sesión
    private void iniciarSesion() {
        String obtenerEmail = email.getText().toString();
        String obtenerPassword = contrasenia.getText().toString();

        // Verificar si los campos de email y contraseña no están vacíos
        if (!obtenerEmail.isEmpty() && !obtenerPassword.isEmpty()){
            if (obtenerPassword.length() >= 6) { // Verificar que la contraseña tenga al menos 6 caracteres
                autenticacion.signInWithEmailAndPassword(obtenerEmail, obtenerPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FancyToast.makeText(LogearseActividad.this,"Inicio sesión correctamente",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        }
                        else {
                            FancyToast.makeText(LogearseActividad.this,"La contraseña o email son incorrectos",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                        }
                    }
                });
            }
        }
        else {
            FancyToast.makeText(LogearseActividad.this, "Faltan rellenar los campos vacios",FancyToast.LENGTH_LONG,FancyToast.ERROR, false).show();
        }
    }

    private void pantallaRegistro() {
        Intent pantallaRegistro= new Intent(LogearseActividad.this, RegistrarActividad.class);
        startActivity(pantallaRegistro); // Iniciar la nueva actividad
    }
}