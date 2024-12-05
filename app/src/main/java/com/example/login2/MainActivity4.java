package com.example.login2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity4 extends AppCompatActivity {

    private EditText editTextNombre, editTextApellido, editTextTelefono, editTextFechaNacimiento, editTextCorreo;
    private RadioGroup radioGroupSexo;
    private CheckBox cbDocente, cbEstudiante, checkBoxSesion;
    private Spinner spinnerEstados, spinnerCarrera;
    private RatingBar ratingBar;
    private TextView textViewNombreRecuperado;
    private Button buttonGuardarSharedPreferences, buttonGuardarFileStream, buttonIrOtraActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // Inicialización de vistas
        editTextNombre = findViewById(R.id.editTextNombre1);
        editTextApellido = findViewById(R.id.editTextApellido1);
        editTextTelefono = findViewById(R.id.editTextTelefono1);
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento);
        editTextCorreo = findViewById(R.id.editTextCorreo1);
        radioGroupSexo = findViewById(R.id.radioGroupSexo);
        cbDocente = findViewById(R.id.cbDocente);
        cbEstudiante = findViewById(R.id.cbEstudiante);
        spinnerEstados = findViewById(R.id.spinnerEstados);
        spinnerCarrera = findViewById(R.id.spinnerCarrera);
        ratingBar = findViewById(R.id.ratingBar);
        checkBoxSesion = findViewById(R.id.checkBoxSesion1);
        buttonGuardarSharedPreferences = findViewById(R.id.buttonGuardarSharedPreferences);
        buttonGuardarFileStream = findViewById(R.id.buttonGuardarFileStream);
        buttonIrOtraActividad = findViewById(R.id.buttonIrOtraActividad);

        // Configuración de adaptadores para los Spinners
        ArrayAdapter<CharSequence> adapterEstados = ArrayAdapter.createFromResource(
                this, R.array.estados_mexico, android.R.layout.simple_spinner_item);
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstados.setAdapter(adapterEstados);

        ArrayAdapter<CharSequence> adapterCarrera = ArrayAdapter.createFromResource(
                this, R.array.Carrera, android.R.layout.simple_spinner_item);
        adapterCarrera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarrera.setAdapter(adapterCarrera);

        // Cambia el color del texto inicial a negro
        spinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        spinnerCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        // Configuración del DatePickerDialog al hacer clic en editTextFechaNacimiento
        editTextFechaNacimiento.setOnClickListener(v -> mostrarDatePickerDialog());

        // Botón para guardar en SharedPreferences
        buttonGuardarSharedPreferences.setOnClickListener(v -> guardarEnSharedPreferences());

        // Botón para guardar en un archivo
        buttonGuardarFileStream.setOnClickListener(v -> guardarEnArchivo());

        // Botón para mostrar los datos en otra actividad
        buttonIrOtraActividad.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity4.this, MainActivity7.class);
            startActivity(intent);
        });
    }

    private void mostrarDatePickerDialog() {
        final Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, añoSeleccionado, mesSeleccionado, diaSeleccionado) -> {
                    String fecha = diaSeleccionado + "/" + (mesSeleccionado + 1) + "/" + añoSeleccionado;
                    editTextFechaNacimiento.setText(fecha);
                },
                año, mes, dia
        );

        datePickerDialog.show();
    }

    private void guardarEnSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MisDatos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Obtener datos de los campos
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String fechaNacimiento = editTextFechaNacimiento.getText().toString();
        String correo = editTextCorreo.getText().toString();
        String sexo = ((RadioButton) findViewById(radioGroupSexo.getCheckedRadioButtonId())).getText().toString();
        String rol = (cbDocente.isChecked() ? "Docente " : "") + (cbEstudiante.isChecked() ? "Estudiante" : "");
        String estadoSeleccionado = spinnerEstados.getSelectedItem().toString();
        String carreraSeleccionada = spinnerCarrera.getSelectedItem().toString();
        float calificacion = ratingBar.getRating();
        boolean sesionIniciada = checkBoxSesion.isChecked();

        // Guardar datos en SharedPreferences
        editor.putString("nombre", nombre);
        editor.putString("apellido", apellido);
        editor.putString("telefono", telefono);
        editor.putString("fechaNacimiento", fechaNacimiento);
        editor.putString("correo", correo);
        editor.putString("sexo", sexo);
        editor.putString("rol", rol);
        editor.putString("estado", estadoSeleccionado);
        editor.putString("carrera", carreraSeleccionada);
        editor.putFloat("calificacion", calificacion);
        editor.putBoolean("sesionIniciada", sesionIniciada);
        editor.apply();

        Toast.makeText(this, "Datos guardados en SharedPreferences", Toast.LENGTH_SHORT).show();
    }

    private void guardarEnArchivo() {
        // Obtener datos de los campos
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String fechaNacimiento = editTextFechaNacimiento.getText().toString();
        String correo = editTextCorreo.getText().toString();
        String sexo = ((RadioButton) findViewById(radioGroupSexo.getCheckedRadioButtonId())).getText().toString();
        String rol = (cbDocente.isChecked() ? "Docente " : "") + (cbEstudiante.isChecked() ? "Estudiante" : "");
        String estadoSeleccionado = spinnerEstados.getSelectedItem().toString();
        String carreraSeleccionada = spinnerCarrera.getSelectedItem().toString();
        float calificacion = ratingBar.getRating();
        boolean sesionIniciada = checkBoxSesion.isChecked();

        // Crear el contenido del archivo
        String contenido = "Nombre: " + nombre +
                "\nApellido: " + apellido +
                "\nTeléfono: " + telefono +
                "\nFecha de Nacimiento: " + fechaNacimiento +
                "\nCorreo: " + correo +
                "\nSexo: " + sexo +
                "\nRol: " + rol +
                "\nEstado: " + estadoSeleccionado +
                "\nCarrera: " + carreraSeleccionada +
                "\nCalificación: " + calificacion +
                "\nSesión Iniciada: " + sesionIniciada;

        // Guardar en el archivo
        try (FileOutputStream fos = openFileOutput("datos_usuario.txt", Context.MODE_PRIVATE)) {
            fos.write(contenido.getBytes());
            Toast.makeText(this, "Datos guardados en archivo", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar en archivo", Toast.LENGTH_SHORT).show();
        }
    }
}
