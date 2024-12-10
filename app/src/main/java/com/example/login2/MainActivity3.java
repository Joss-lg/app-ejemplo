package com.example.login2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity3 extends AppCompatActivity {

    private ImageView imageView;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Habilitar la pantalla completa y gestionar las barras del sistema
        WindowInsetsControllerCompat windowInsetsController = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        windowInsetsController.setAppearanceLightStatusBars(true);

        // Inicializar el ImageView
        imageView = findViewById(R.id.imageView);
        button2= (Button)findViewById(R.id.button2);

        // Configurar el OnClickListener para el botón button2
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para iniciar la nueva actividad
                Intent intent = new Intent(MainActivity3.this, MainActivity6.class);
                startActivity(intent);
            }
        });


        // Configurar WindowInsetsCompat para ajustar los márgenes
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Botón para mostrar el diálogo
        Button btnShowDialog = findViewById(R.id.btn_show_dialog);
        btnShowDialog.setOnClickListener(v -> showRadioButtonDialog());
    }

    private void showRadioButtonDialog() {
        // Opciones del diálogo con RadioButtons
        final String[] options = {"Opción 1", "Opción 2", "Opción 3"};

        // Variable para guardar la opción seleccionada
        final int[] selectedOption = {0};  // Se guarda como array para ser mutable

        // Construcción del AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción");

        // Opciones de RadioButton
        builder.setSingleChoiceItems(options, selectedOption[0], (dialog, which) -> {
            selectedOption[0] = which;  // Actualiza la opción seleccionada
        });

        // Botón "OK"
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Mostrar un mensaje de la opción seleccionada
            Toast.makeText(this, "Has seleccionado: " + options[selectedOption[0]], Toast.LENGTH_SHORT).show();

            // Cambiar la imagen en el ImageView según la opción seleccionada
            switch (selectedOption[0]) {
                case 0:
                    imageView.setImageResource(R.drawable.subaru2);  // Cambiar a imagen 1
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.champions);  // Cambiar a imagen 2
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.corvette);  // Cambiar a imagen 3
                    break;
            }
        });

        // Botón "Cancelar"
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        // Mostrar el diálogo
        builder.show();



    }

}
