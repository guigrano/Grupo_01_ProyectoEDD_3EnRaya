package com.example.proyectoedd.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoedd.R;

public class ConfiguracionActivity extends AppCompatActivity {

    private RadioGroup rgSimbolo;
    private RadioGroup rgTurno;
    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        rgSimbolo = findViewById(R.id.rgSimbolo);
        rgTurno = findViewById(R.id.rgTurno);
        btnContinuar = findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(v -> {

            int simboloSeleccionado =
                    rgSimbolo.getCheckedRadioButtonId();

            int turnoSeleccionado =
                    rgTurno.getCheckedRadioButtonId();

            if (simboloSeleccionado == -1 || turnoSeleccionado == -1) {
                return;
            }

            RadioButton radioSimbolo =
                    findViewById(simboloSeleccionado);

            RadioButton radioTurno =
                    findViewById(turnoSeleccionado);

            String simbolo =
                    radioSimbolo.getText().toString();

            boolean humanoInicia =
                    radioTurno.getId() == R.id.rbHumano;

            Intent intent = new Intent(
                    ConfiguracionActivity.this,
                    JuegoActivity.class
            );

            intent.putExtra("simboloHumano", simbolo);
            intent.putExtra("humanoInicia", humanoInicia);

            startActivity(intent);
        });
    }
}
