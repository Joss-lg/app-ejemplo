package com.example.login2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;  // <-- Importa Intent
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login2.R;

public class MainActivity6 extends AppCompatActivity {

    EditText etId, etName, etAge, etEmail;
    Button btnAdd, btnUpdate, btnDelete, btnView;
    TextView tvResults;
    com.example.login2.DBaseSQL dbHelper = new com.example.login2.DBaseSQL(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etEmail = findViewById(R.id.etEmail);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);
        tvResults = findViewById(R.id.tvResults);

        // Botón para agregar usuario
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String ageStr = etAge.getText().toString();
                String email = etEmail.getText().toString();

                if (name.isEmpty() || ageStr.isEmpty()) {
                    Toast.makeText(MainActivity6.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(ageStr);
                boolean isInserted = dbHelper.insertUser(name, age, email);
                if (isInserted) {
                    Toast.makeText(MainActivity6.this, "Usuario Agregado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity6.this, "Error al agregar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón para actualizar usuario (sin cambios)
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idStr = etId.getText().toString();
                String name = etName.getText().toString();
                String ageStr = etAge.getText().toString();
                String email = etEmail.getText().toString();

                if (idStr.isEmpty() || name.isEmpty() || ageStr.isEmpty()) {
                    Toast.makeText(MainActivity6.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = Integer.parseInt(idStr);
                int age = Integer.parseInt(ageStr);

                boolean isUpdated = dbHelper.updateUser(id, name, age, email);
                if (isUpdated) {
                    Toast.makeText(MainActivity6.this, "Usuario Actualizado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity6.this, "Error al actualizar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón para eliminar usuario (sin cambios)
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idStr = etId.getText().toString();

                if (idStr.isEmpty()) {
                    Toast.makeText(MainActivity6.this, "Por favor, ingrese el ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = Integer.parseInt(idStr);
                boolean isDeleted = dbHelper.deleteUser(id);
                if (isDeleted) {
                    Toast.makeText(MainActivity6.this, "Usuario Borrado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity6.this, "Error al borrar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón para ver usuarios
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dbHelper.getAllUsers();
                if (cursor.getCount() == 0) {
                    tvResults.setText("No Existen Registros!");
                    return;
                }

                StringBuilder result = new StringBuilder();
                while (cursor.moveToNext()) {
                    result.append("ID: ").append(cursor.getInt(0)).append("\n");
                    result.append("Nombre: ").append(cursor.getString(1)).append("\n");
                    result.append("Edad: ").append(cursor.getInt(2)).append("\n");
                    result.append("Email: ").append(cursor.getString(3)).append("\n\n");
                }

                // Pasar los datos a la nueva actividad
                Intent intent = new Intent(MainActivity6.this, UserListActivity.class);
                intent.putExtra("userData", result.toString());  // Pasar los datos como string
                startActivity(intent);
            }
        });
    }
}
