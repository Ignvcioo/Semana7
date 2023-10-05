package com.ignacio.uber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ignacio.uber.models.Usuario;

public class RegistrarActividad extends AppCompatActivity {


    SharedPreferences preferencias;
    FirebaseAuth autenticacion;
    DatabaseReference baseDatos;

    Button registrar;
    TextInputEditText inputEmail;
    TextInputEditText inputNombre;
    TextInputEditText inputContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrar);

        autenticacion = FirebaseAuth.getInstance();
        baseDatos = FirebaseDatabase.getInstance().getReference();
        preferencias = getApplicationContext().getSharedPreferences("tipoUsuario", MODE_PRIVATE);

        registrar = findViewById(R.id.btnRegistrar);
        inputEmail = findViewById(R.id.txtInputEmail);
        inputNombre = findViewById(R.id.txtInputNombre);
        inputContrasenia = findViewById(R.id.txtInputContrasenia);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });
    }

    void registrarUsuario(){
        final String email = inputEmail.getText().toString();
        final String nombre = inputNombre.getText().toString();
        final String contrasenia = inputContrasenia.getText().toString();

        if (!email.isEmpty() && !nombre.isEmpty() && !contrasenia.isEmpty()){
            if (contrasenia.length() >= 6){
                autenticacion.createUserWithEmailAndPassword(email, contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            guardarUsuario(nombre, email);
                            Toast.makeText(RegistrarActividad.this, "Registrado", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RegistrarActividad.this, "No se pudo registrar correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                Toast.makeText(this, "La contraseña debera contener 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show();
        }
    }

    void guardarUsuario(String nombre,String email){
        String seleccionarUsuario = preferencias.getString("usuario", "");
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        if (seleccionarUsuario.equals("Conductor")){
            baseDatos.child("Usuarios").child("Conductores").push().setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                    }
                    else {
                        Toast.makeText(RegistrarActividad.this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (seleccionarUsuario.equals("Cliente")) {
            baseDatos.child("Usuarios").child("Clientes").push().setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistrarActividad.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegistrarActividad.this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}