package com.example.proyectoedd.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoedd.R;

public class ConfiguracionActivity extends AppCompatActivity {

    private Button btnX;
    private Button btnO;
    private RadioGroup rgTurno;
    private RadioButton rbHumano;
    private RadioButton rbComputadora;
    private TextView tvQuienComienza;
    private TextView tvTituloConfig;
    private Button btnContinuar;
    private ImageButton btnRegresarInicio;
    private String simboloSeleccionado = "";
    private String modoJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        modoJuego = getIntent().getStringExtra("modoJuego");
        if (modoJuego == null) modoJuego = "PVC";

        btnX = findViewById(R.id.btnX);
        btnO = findViewById(R.id.btnO);
        rgTurno = findViewById(R.id.rgTurno);
        rbHumano = findViewById(R.id.rbHumano);
        rbComputadora = findViewById(R.id.rbComputadora);
        tvQuienComienza = findViewById(R.id.tvQuienComienza);
        tvTituloConfig = findViewById(R.id.tvTituloConfig);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnRegresarInicio = findViewById(R.id.btnRegresarInicio);

        if (modoJuego.equals("PVP")) {
            tvTituloConfig.setText("vs Jugador");
            rbHumano.setText("Jugador 1");
            rbComputadora.setText("Jugador 2");
        } else {
            tvTituloConfig.setText("vs Computadora");
            rbHumano.setText("Yo");
            rbComputadora.setText("Computadora");
        }

        btnRegresarInicio.setOnClickListener(v -> finish());

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
            boolean jugador1Inicia = radioTurno.getId() == R.id.rbHumano;

            Intent intent = new Intent(
                    ConfiguracionActivity.this,
                    JuegoActivity.class
            );

            intent.putExtra("simboloHumano", simboloSeleccionado);
            intent.putExtra("humanoInicia", jugador1Inicia);
            intent.putExtra("modoJuego", modoJuego);

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