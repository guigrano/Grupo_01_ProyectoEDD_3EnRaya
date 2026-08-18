package com.example.proyectoedd.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectoedd.R;

public class MainActivity extends AppCompatActivity {

    private Button btnJugarPVC;
    private Button btnJugarPVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJugarPVC = findViewById(R.id.btnJugarPVC);
        btnJugarPVP = findViewById(R.id.btnJugarPVP);

        btnJugarPVC.setOnClickListener(v -> abrirConfiguracion("PVC"));
        btnJugarPVP.setOnClickListener(v -> abrirConfiguracion("PVP"));
    }

    private void abrirConfiguracion(String modoJuego) {
        Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);

        intent.putExtra("modoJuego", modoJuego);

        startActivity(intent);
    }
}