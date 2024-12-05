package com.example.login2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity7 extends AppCompatActivity {

    private TextView textViewMostrarDatos;
    private Button buttonMostrarSharedPreferences, buttonMostrarFileStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        textViewMostrarDatos = findViewById(R.id.textViewMostrarDatos);
        buttonMostrarSharedPreferences = findViewById(R.id.buttonMostrarSharedPreferences);
        buttonMostrarFileStream = findViewById(R.id.buttonMostrarFileStream);

        buttonMostrarSharedPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatosSharedPreferences();
            }
        });

        buttonMostrarFileStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatosFileStream();
            }
        });
    }

    private void mostrarDatosSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MisDatos", MODE_PRIVATE);
        String nombre = sharedPreferences.getString("nombre", "");
        String apellido = sharedPreferences.getString("apellido", "");
        String telefono = sharedPreferences.getString("telefono", "");
        String fechaNacimiento = sharedPreferences.getString("fechaNacimiento", "");
        String correo = sharedPreferences.getString("correo", "");
        String sexo = sharedPreferences.getString("sexo", "");
        String rol = sharedPreferences.getString("rol", "");
        String estado = sharedPreferences.getString("estado", "");
        String carrera = sharedPreferences.getString("carrera", "");
        float calificacion = sharedPreferences.getFloat("calificacion", 0);
        boolean sesionIniciada = sharedPreferences.getBoolean("sesionIniciada", false);

        String datos = "Nombre: " + nombre +
                "\nApellido: " + apellido +
                "\nTeléfono: " + telefono +
                "\nFecha de Nacimiento: " + fechaNacimiento +
                "\nCorreo: " + correo +
                "\nSexo: " + sexo +
                "\nRol: " + rol +
                "\nEstado: " + estado +
                "\nCarrera: " + carrera +
                "\nCalificación: " + calificacion +
                "\nSesión Iniciada: " + (sesionIniciada ? "Sí" : "No");

        textViewMostrarDatos.setText(datos);
        Toast.makeText(this, "Datos de SharedPreferences mostrados", Toast.LENGTH_SHORT).show();
    }

    private void mostrarDatosFileStream() {
        try (FileInputStream fis = openFileInput("datos_usuario.txt")) {
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            String datos = new String(buffer);

            textViewMostrarDatos.setText(datos);
            Toast.makeText(this, "Datos de FileStream mostrados", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al leer archivo", Toast.LENGTH_SHORT).show();
        }
    }
}
