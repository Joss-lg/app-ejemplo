package com.example.login2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity5 extends AppCompatActivity {

    private EditText editTextNombre2, editTextApellido2, editTextTelefono2, editTextEdad2, editTextCorreo2;
    private RadioGroup radioGroupSexo2;
    private CheckBox cbDocente2, cbEstudiante2;
    private Spinner spinnerEstados2, spinnerCarrera2;
    private RatingBar ratingBar2;
    private Button buttonGuardar2, buttonRecuperar2;
    private TextView textViewNombreRecuperado2;

    private static final String FILE_NAME = "miArchivo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        // Inicializar vistas con IDs terminados en "2"
        editTextNombre2 = findViewById(R.id.editTextNombre2);
        editTextApellido2 = findViewById(R.id.editTextApellido2);
        editTextTelefono2 = findViewById(R.id.editTextTelefono2);
        editTextEdad2 = findViewById(R.id.editTextEdad2);
        editTextCorreo2 = findViewById(R.id.editTextCorreo2);
        radioGroupSexo2 = findViewById(R.id.radioGroupSexo2);
        cbDocente2 = findViewById(R.id.cbDocente2);
        cbEstudiante2 = findViewById(R.id.cbEstudiante2);
        spinnerEstados2 = findViewById(R.id.spinnerEstados2);
        spinnerCarrera2 = findViewById(R.id.spinnerCarrera2);
        ratingBar2 = findViewById(R.id.ratingBar2);
        buttonGuardar2 = findViewById(R.id.buttonGuardar2);
        buttonRecuperar2 = findViewById(R.id.buttonRecuperar2);
        textViewNombreRecuperado2 = findViewById(R.id.textViewNombreRecuperado2);

        // Configurar adaptadores para los Spinners
        configurarSpinnerEstados();
        configurarSpinnerCarrera();

        // Configurar botón de guardar
        buttonGuardar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarArchivo();
            }
        });

        // Configurar botón de recuperar
        buttonRecuperar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarArchivo();
            }
        });
    }

    private void configurarSpinnerEstados() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.estados_mexico,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstados2.setAdapter(adapter);
    }

    private void configurarSpinnerCarrera() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Carrera,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarrera2.setAdapter(adapter);
    }

    // Método para guardar datos en un archivo usando FileOutputStream
    private void guardarArchivo() {
        String nombre = editTextNombre2.getText().toString();
        String apellido = editTextApellido2.getText().toString();
        String telefono = editTextTelefono2.getText().toString();
        String edad = editTextEdad2.getText().toString();
        String correo = editTextCorreo2.getText().toString();

        // Obtener sexo
        String sexo = ((RadioButton) findViewById(radioGroupSexo2.getCheckedRadioButtonId())).getText().toString();

        // Obtener carrera y estado
        String carrera = spinnerCarrera2.getSelectedItem().toString();
        String estado = spinnerEstados2.getSelectedItem().toString();
        String calificacion = String.valueOf(ratingBar2.getRating());

        // Determinar si es docente o estudiante
        String tipo = (cbDocente2.isChecked() ? "Docente " : "") + (cbEstudiante2.isChecked() ? "Estudiante" : "").trim();

        String contenido = nombre + "\n" + apellido + "\n" + telefono + "\n" + edad + "\n" + correo + "\n" +
                sexo + "\n" + tipo + "\n" + estado + "\n" + carrera + "\n" + calificacion;

        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(contenido.getBytes());
            Toast.makeText(this, "Archivo guardado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar archivo", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para cargar datos desde un archivo usando FileInputStream
    private void cargarArchivo() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            int size;
            StringBuilder stringBuilder = new StringBuilder();

            while ((size = fis.read()) != -1) {
                stringBuilder.append((char) size);
            }

            // Separar los datos cargados
            String[] datos = stringBuilder.toString().split("\n");
            String nombre = datos.length > 0 ? datos[0] : "";
            String apellido = datos.length > 1 ? datos[1] : "";
            String telefono = datos.length > 2 ? datos[2] : "";
            String edad = datos.length > 3 ? datos[3] : "";
            String correo = datos.length > 4 ? datos[4] : "";
            String sexo = datos.length > 5 ? datos[5] : "";
            String tipo = datos.length > 6 ? datos[6] : "";
            String estado = datos.length > 7 ? datos[7] : "";
            String carrera = datos.length > 8 ? datos[8] : "";
            String calificacion = datos.length > 9 ? datos[9] : "0";

            // Mostrar los datos en los campos de texto
            editTextNombre2.setText(nombre);
            editTextApellido2.setText(apellido);
            editTextTelefono2.setText(telefono);
            editTextEdad2.setText(edad);
            editTextCorreo2.setText(correo);
            // Seleccionar el radio button correspondiente
            if (sexo.equals("Hombre")) {
                radioGroupSexo2.check(R.id.radioButtonHombre2);
            } else {
                radioGroupSexo2.check(R.id.radioButtonMujer2);
            }
            // Marcar los checkboxes
            cbDocente2.setChecked(tipo.contains("Docente"));
            cbEstudiante2.setChecked(tipo.contains("Estudiante"));
            // Seleccionar el estado y carrera
            int estadoPosition = ((ArrayAdapter<String>) spinnerEstados2.getAdapter()).getPosition(estado);
            spinnerEstados2.setSelection(estadoPosition);
            int carreraPosition = ((ArrayAdapter<String>) spinnerCarrera2.getAdapter()).getPosition(carrera);
            spinnerCarrera2.setSelection(carreraPosition);
            // Configurar la calificación
            ratingBar2.setRating(Float.parseFloat(calificacion));

            // Mostrar el contenido recuperado
            textViewNombreRecuperado2.setText("Nombre: " + nombre + "\nApellido: " + apellido + "\nTeléfono: " + telefono + "\nEdad: " + edad + "\nCorreo: " + correo +
                    "\nSexo: " + sexo + "\nTipo: " + tipo + "\nEstado: " + estado + "\nCarrera: " + carrera + "\nCalificación: " + calificacion);

            Toast.makeText(this, "Archivo cargado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar archivo", Toast.LENGTH_SHORT).show();
        }
    }
}
