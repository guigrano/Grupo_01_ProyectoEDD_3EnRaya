package com.example.proyectoedd.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectoedd.R;
public class MainActivity extends AppCompatActivity {

    private Button btnJugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJugar = findViewById(R.id.btnJugar);

        btnJugar.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ConfiguracionActivity.class
            );

            startActivity(intent);
        });
    }
}