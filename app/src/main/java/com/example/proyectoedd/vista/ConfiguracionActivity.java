package com.example.proyectoedd.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoedd.R;

public class ConfiguracionActivity extends AppCompatActivity {

    private Button btnX;
    private Button btnO;
    private RadioGroup rgTurno;
    private Button btnContinuar;
    private ImageButton btnRegresarInicio;
    private String simboloSeleccionado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        btnX = findViewById(R.id.btnX);
        btnO = findViewById(R.id.btnO);
        rgTurno = findViewById(R.id.rgTurno);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnRegresarInicio = findViewById(R.id.btnRegresarInicio);

        btnRegresarInicio.setOnClickListener(v -> {
            finish(); // Cierra esta Activity y regresa a la anterior (MainActivity)
        });

        btnX.setOnClickListener(v -> seleccionarSimbolo("X"));
        btnO.setOnClickListener(v -> seleccionarSimbolo("O"));

        btnContinuar.setOnClickListener(v -> {

            if (simboloSeleccionado.isEmpty()) {
                Toast.makeText(this, "Por favor, selecciona un símbolo (X u O)", Toast.LENGTH_SHORT).show();
                return;
            }

            int turnoSeleccionado = rgTurno.getCheckedRadioButtonId();

            if (turnoSeleccionado == -1) {
                Toast.makeText(this, "Por favor, selecciona quién comienza", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton radioTurno = findViewById(turnoSeleccionado);
            boolean humanoInicia = radioTurno.getId() == R.id.rbHumano;

            Intent intent = new Intent(
                    ConfiguracionActivity.this,
                    JuegoActivity.class
            );


            intent.putExtra("simboloHumano", simboloSeleccionado);
            intent.putExtra("humanoInicia", humanoInicia);

            startActivity(intent);
        });
    }

    private void seleccionarSimbolo(String simbolo) {
        simboloSeleccionado = simbolo;

        if (simbolo.equals("X")) {
            btnX.setAlpha(1.0f);
            btnO.setAlpha(0.4f);
        } else {
            btnX.setAlpha(0.4f);
            btnO.setAlpha(1.0f);
        }
    }
}