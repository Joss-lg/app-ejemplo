package com.example.login2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ToggleButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity2 extends AppCompatActivity {

    private Spinner spinnerEstados;
    private Switch switchNotificaciones;
    private ToggleButton toggleButton;
    private CalendarView calendarView;
    private SeekBar seekBar;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        spinnerEstados = findViewById(R.id.spinnerEstados);
        switchNotificaciones = findViewById(R.id.switchNotificaciones);
        calendarView = findViewById(R.id.calendarView);
        toggleButton = findViewById(R.id.toggleButton);
        seekBar = findViewById(R.id.seekBar);
        ratingBar = findViewById(R.id.ratingBar);

        // Cargar las preferencias guardadas
        cargarPreferencias();

        // Configurar el adaptador para el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.estados_mexico, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstados.setAdapter(adapter);

        // Configurar SeekBar y RatingBar
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(getApplicationContext(), "Nivel de volumen: " + progress, Toast.LENGTH_SHORT).show();
                guardarPreferencia("nivel_volumen", progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            Toast.makeText(getApplicationContext(), "Calificación seleccionada: " + rating, Toast.LENGTH_SHORT).show();
            guardarPreferencia("calificacion", rating);
        });

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            guardarPreferencia("modo_oscuro", isChecked);
        });

        switchNotificaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mostrarMensajeNotificaciones(isChecked ? "Notificaciones activadas" : "Notificaciones desactivadas");
            guardarPreferencia("notificaciones", isChecked);
        });

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(getApplicationContext(), "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
            guardarPreferencia("fecha_seleccionada", fechaSeleccionada);
        });
    }

    private void cargarPreferencias() {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        // Cargar preferencias de SeekBar, RatingBar, ToggleButton, etc.
        seekBar.setProgress(sharedPreferences.getInt("nivel_volumen", 50));
        ratingBar.setRating(sharedPreferences.getFloat("calificacion", 3.0f));
        toggleButton.setChecked(sharedPreferences.getBoolean("modo_oscuro", false));
        switchNotificaciones.setChecked(sharedPreferences.getBoolean("notificaciones", false));

        // Cargar fecha del CalendarView
        String fechaSeleccionada = sharedPreferences.getString("fecha_seleccionada", null);
        if (fechaSeleccionada != null) {
            // Aquí podrías convertir la fecha guardada y aplicarla al CalendarView si lo deseas.
        }
    }

    private void guardarPreferencia(String clave, Object valor) {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (valor instanceof Boolean) {
            editor.putBoolean(clave, (Boolean) valor);
        } else if (valor instanceof Integer) {
            editor.putInt(clave, (Integer) valor);
        } else if (valor instanceof Float) {
            editor.putFloat(clave, (Float) valor);
        } else if (valor instanceof String) {
            editor.putString(clave, (String) valor);
        }
        editor.apply();
    }

    private void mostrarMensajeNotificaciones(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}
