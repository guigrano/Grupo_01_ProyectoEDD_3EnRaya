package com.example.proyectoedd.vista;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoedd.R;
import com.example.proyectoedd.controlador.MotorJuego;
import com.example.proyectoedd.modelo.Simbolo;
import com.example.proyectoedd.modelo.Tablero;
import android.app.AlertDialog;

import java.util.List;
import android.content.Intent;

public class JuegoActivity extends AppCompatActivity {

    private MotorJuego motor;

    private GridLayout cuadriculaUI;
    private TextView tvTurno;
    private TextView tvResultado;
    private TextView tvRecomendacion;

    private Button btnJugarDeNuevo;
    private Button btnPensamiento;
    private Button btnDeshacer;
    private Button btnRehacer;
    private ImageButton btnRegresar;

    private Button[][] botones;

    private Handler handler;

    private boolean esModoPVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        motor = new MotorJuego();

        handler = new Handler(Looper.getMainLooper());

        cuadriculaUI = findViewById(R.id.cuadriculaUI);
        tvTurno = findViewById(R.id.tvTurno);
        tvResultado = findViewById(R.id.tvResultado);
        tvRecomendacion = findViewById(R.id.tvRecomendacion);

        btnJugarDeNuevo = findViewById(R.id.btnJugarDeNuevo);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnPensamiento = findViewById(R.id.btnPensamiento);
        btnDeshacer = findViewById(R.id.btnDeshacer);
        btnRehacer = findViewById(R.id.btnRehacer);

        inicializarBotones();

        String simboloHumano =
                getIntent().getStringExtra("simboloHumano");

        boolean humanoInicia =
                getIntent().getBooleanExtra("humanoInicia", true);

        String modoJuego =
                getIntent().getStringExtra("modoJuego");

        esModoPVP = "PVP".equals(modoJuego);

        if (esModoPVP) {
            motor.iniciarJuegoPvP(simboloHumano, humanoInicia);
            btnPensamiento.setVisibility(Button.GONE);
        } else {
            motor.iniciarJuego(simboloHumano, humanoInicia);
        }

        btnRegresar.setOnClickListener(v -> finish());

        btnJugarDeNuevo.setOnClickListener(v -> reiniciarJuego());

        btnPensamiento.setOnClickListener(v -> abrirPensamiento());

        btnDeshacer.setOnClickListener(v -> ejecutarDeshacer());

        btnRehacer.setOnClickListener(v -> ejecutarRehacer());

        actualizarPantalla();

        if (!esModoPVP && !humanoInicia) {
            prepararTurnoComputador();
        }
    }

    private void inicializarBotones() {

        botones = new Button[3][3];

        botones[0][0] = findViewById(R.id.casilla00);
        botones[0][1] = findViewById(R.id.casilla01);
        botones[0][2] = findViewById(R.id.casilla02);

        botones[1][0] = findViewById(R.id.casilla10);
        botones[1][1] = findViewById(R.id.casilla11);
        botones[1][2] = findViewById(R.id.casilla12);

        botones[2][0] = findViewById(R.id.casilla20);
        botones[2][1] = findViewById(R.id.casilla21);
        botones[2][2] = findViewById(R.id.casilla22);

        for (int fila = 0; fila < 3; fila++) {

            for (int col = 0; col < 3; col++) {

                final int filaFinal = fila;
                final int colFinal = col;

                botones[fila][col].setOnClickListener(v ->
                        procesarToque(filaFinal, colFinal)
                );
            }
        }
    }

    private void procesarToque(int fila, int col) {

        if (motor.verificarFinJuego()) {
            return;
        }

        if (!esModoPVP && motor.getTurnoActual() != motor.getSimboloHumano()) {
            return;
        }

        boolean jugadaValida =
                motor.jugarTurnoHumano(fila, col);

        if (!jugadaValida) {
            return;
        }

        actualizarPantalla();

        if (motor.verificarFinJuego()) {
            mostrarResultado();
            return;
        }

        if (!esModoPVP) {
            prepararTurnoComputador();
        }
    }

    private void prepararTurnoComputador() {

        bloquearTablero();

        tvTurno.setText("Turno de la PC...");

        handler.postDelayed(() -> {

            if (!motor.verificarFinJuego()) {

                motor.jugarTurnoComputador();

                actualizarPantalla();

                if (motor.verificarFinJuego()) {
                    mostrarResultado();
                }
            }

        }, 700);
    }

    private void actualizarPantalla() {

        Tablero tablero = motor.getTablero();

        for (int fila = 0; fila < 3; fila++) {

            for (int col = 0; col < 3; col++) {

                Simbolo simbolo =
                        tablero.getCasilla(fila, col);

                if (simbolo == Simbolo.VACIO) {

                    botones[fila][col].setText("");

                } else {

                    botones[fila][col].setText(simbolo.toString());

                    if (simbolo.toString().equals("X")) {
                        botones[fila][col].setTextColor(Color.parseColor("#E74C3C"));
                    } else if (simbolo.toString().equals("O")) {
                        botones[fila][col].setTextColor(Color.parseColor("#2980B9"));
                    }
                }
            }
        }

        if (!motor.verificarFinJuego()) {

            if (esModoPVP) {

                Simbolo turno = motor.getTurnoActual();
                if (turno == motor.getSimboloJugador1()) {
                    tvTurno.setText("Turno de Jugador 1 (" + turno + ")");
                } else {
                    tvTurno.setText("Turno de Jugador 2 (" + turno + ")");
                }

                desbloquearTablero();

                mostrarRecomendacion();

            } else {

                if (motor.getTurnoActual() == motor.getSimboloHumano()) {

                    tvTurno.setText("Tu turno");

                    desbloquearTablero();

                    mostrarRecomendacion();

                } else {

                    tvTurno.setText("Turno de la PC...");

                    bloquearTablero();

                    tvRecomendacion.setVisibility(TextView.GONE);
                }
            }
        }

        actualizarBotonesUndoRedo();
    }

    private void mostrarRecomendacion() {

        int[] movimiento = motor.recomendarMovimiento();

        if (movimiento[0] == -1 || movimiento[1] == -1) {
            tvRecomendacion.setVisibility(TextView.GONE);
            return;
        }

        int fila = movimiento[0] + 1;
        int columna = movimiento[1] + 1;

        tvRecomendacion.setText(
                "Te recomendamos jugar en Fila "
                        + fila
                        + ", Columna "
                        + columna
        );

        tvRecomendacion.setVisibility(TextView.VISIBLE);
    }

    private void mostrarResultado() {

        bloquearTablero();

        tvTurno.setText("");

        int resultado = motor.obtenerResultado();

        if (resultado == 0) {

            tvResultado.setText("EMPATE");

        } else if (esModoPVP) {

            if (resultado == 1) {
                tvResultado.setText("Jugador 1 (" + motor.getSimboloJugador1() + ") ganó");
            } else {
                tvResultado.setText("Jugador 2 (" + motor.getSimboloJugador2() + ") ganó");
            }

        } else {

            Simbolo ganador;

            if (resultado == 1) {
                ganador = motor.getSimboloHumano();
            } else {
                ganador = motor.getSimboloComputador();
            }

            if (ganador == motor.getSimboloHumano()) {

                tvResultado.setText("Usuario ganó");

            } else {

                tvResultado.setText("PC ganó");
            }
        }

        tvResultado.setVisibility(TextView.VISIBLE);
        btnJugarDeNuevo.setVisibility(Button.VISIBLE);

        actualizarBotonesUndoRedo();
    }

    private void ejecutarDeshacer() {

        if (!motor.puedeDeshacer()) return;

        if (!esModoPVP) {
            motor.deshacer();
            if (motor.puedeDeshacer()) {
                motor.deshacer();
            }
        } else {
            motor.deshacer();
        }

        tvResultado.setVisibility(TextView.GONE);
        btnJugarDeNuevo.setVisibility(Button.GONE);

        actualizarPantalla();
    }

    private void ejecutarRehacer() {

        if (!motor.puedeRehacer()) return;

        if (!esModoPVP) {
            motor.rehacer();
            if (motor.puedeRehacer()) {
                motor.rehacer();
            }
        } else {
            motor.rehacer();
        }

        actualizarPantalla();

        if (motor.verificarFinJuego()) {
            mostrarResultado();
        }
    }

    private void actualizarBotonesUndoRedo() {

        btnDeshacer.setEnabled(motor.puedeDeshacer());
        btnRehacer.setEnabled(motor.puedeRehacer());
    }

    private void bloquearTablero() {

        for (int fila = 0; fila < 3; fila++) {

            for (int col = 0; col < 3; col++) {

                botones[fila][col].setEnabled(false);
            }
        }
    }

    private void desbloquearTablero() {

        if (motor.verificarFinJuego()) {
            return;
        }

        for (int fila = 0; fila < 3; fila++) {

            for (int col = 0; col < 3; col++) {

                if (motor.getTablero()
                        .getCasilla(fila, col)
                        == Simbolo.VACIO) {

                    botones[fila][col].setEnabled(true);
                }
            }
        }
    }

    private void reiniciarJuego() {

        tvResultado.setVisibility(TextView.GONE);
        btnJugarDeNuevo.setVisibility(Button.GONE);

        String simboloHumano =
                getIntent().getStringExtra("simboloHumano");

        boolean humanoInicia =
                getIntent().getBooleanExtra("humanoInicia", true);

        if (esModoPVP) {
            motor.iniciarJuegoPvP(simboloHumano, humanoInicia);
        } else {
            motor.iniciarJuego(simboloHumano, humanoInicia);
        }

        actualizarPantalla();

        if (!esModoPVP && !humanoInicia) {
            prepararTurnoComputador();
        }
    }

    @Override
    protected void onDestroy() {

        handler.removeCallbacksAndMessages(null);

        super.onDestroy();
    }

    private String[] obtenerEstadoTablero(Tablero tablero) {

        String[] estado = new String[9];

        int posicion = 0;

        for (int fila = 0; fila < 3; fila++) {

            for (int col = 0; col < 3; col++) {

                estado[posicion] =
                        tablero.getCasilla(fila, col).name();

                posicion++;
            }
        }

        return estado;
    }

    private void abrirPensamiento() {

        Intent intent = new Intent(
                JuegoActivity.this,
                PensamientoActivity.class
        );

        intent.putExtra(
                "tablero",
                obtenerEstadoTablero(motor.getTableroAnterior())
        );

        intent.putExtra(
                "simboloHumano",
                motor.getSimboloHumano().name()
        );

        intent.putExtra(
                "simboloComputador",
                motor.getSimboloComputador().name()
        );

        startActivity(intent);
    }

}
