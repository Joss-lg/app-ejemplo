package com.example.login2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity{
    Button login,b2;
    EditText email;
    EditText contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        relacionarVista();
    }

    public void relacionarVista(){
        login=(Button) findViewById(R.id.buttonLogin);
        b2=(Button)findViewById(R.id.buttonRegister);
        email=(EditText)findViewById(R.id.editTextEmail);
        contraseña=(EditText)findViewById(R.id.editTextPassword);

        login.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view){
              String correo = email.getText().toString();
              String password = contraseña.getText().toString();
              if(correo.equals("jehu") && password.equals("1234")){
                  Intent intencion= new Intent(getApplicationContext(), MainActivity4.class);
                  intencion.putExtra("Email", correo);
                  intencion.putExtra("status", 1);
                  intencion.putExtra("Casado", true);
                  startActivity(intencion);


              }else{
                  Toast.makeText(getApplicationContext(), "Verifica el usuario", Toast.LENGTH_SHORT).show();
              }
          }

        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonregister(view); // Llamamos a la función buttonregister
            }
        });
    }

    // Mueve el método buttonregister fuera de relacionarVista
    public void buttonregister(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    }


