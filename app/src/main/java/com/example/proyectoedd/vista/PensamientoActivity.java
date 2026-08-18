package com.example.proyectoedd.vista;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoedd.R;
import com.example.proyectoedd.controlador.MotorJuego;
import com.example.proyectoedd.modelo.Simbolo;
import com.example.proyectoedd.modelo.Tablero;
import com.example.proyectoedd.modelo.TreeNode;

import java.util.List;

public class PensamientoActivity extends AppCompatActivity {

    private LinearLayout contenedorTableros;
    private TextView tvTituloPensamiento;
    private TextView tvInstruccionPensamiento;
    private Button btnVolverPensamiento;

    private MotorJuego motor;

    private boolean enSegundoNivel;
    private Tablero tableroActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pensamiento);

        contenedorTableros =
                findViewById(R.id.contenedorTableros);

        tvTituloPensamiento =
                findViewById(R.id.tvTituloPensamiento);

        tvInstruccionPensamiento =
                findViewById(R.id.tvInstruccionPensamiento);

        btnVolverPensamiento =
                findViewById(R.id.btnVolverPensamiento);

        btnVolverPensamiento.setOnClickListener(v -> {
            if (enSegundoNivel) {
                mostrarPrimerNivel(tableroActual);
            } else {
                finish();
            }
        });

        String[] estadoTablero =
                getIntent().getStringArrayExtra("tablero");

        String simboloHumano =
                getIntent().getStringExtra("simboloHumano");

        String simboloComputador =
                getIntent().getStringExtra("simboloComputador");

        if (estadoTablero == null
                || simboloHumano == null
                || simboloComputador == null) {

            finish();
            return;
        }

        motor = new MotorJuego();

        motor.iniciarJuego(
                simboloHumano,
                true
        );

        tableroActual =
                reconstruirTablero(estadoTablero);

        mostrarPrimerNivel(tableroActual);
    }

    private Tablero reconstruirTablero(String[] estado) {

        Tablero tablero = new Tablero();

        int posicion = 0;

        for (int fila = 0; fila < 3; fila++) {

            for (int col = 0; col < 3; col++) {

                Simbolo simbolo =
                        Simbolo.valueOf(estado[posicion++]);

                tablero.marcarCasilla(
                        fila,
                        col,
                        simbolo
                );
            }
        }

        return tablero;
    }

    private void mostrarPrimerNivel(Tablero tableroActual) {

        enSegundoNivel = false;

        tvTituloPensamiento.setText(
                "Pensamiento de la computadora"
        );

        tvInstruccionPensamiento.setText(
                "Selecciona un movimiento para ver sus posibilidades"
        );

        contenedorTableros.removeAllViews();

        TreeNode<Tablero> raiz =
                motor.generarArbolPensamiento(tableroActual);

        List<TreeNode<Tablero>> hijos =
                raiz.getChildren();

        for (int i = 0; i < hijos.size(); i++) {

            TreeNode<Tablero> hijo = hijos.get(i);

            agregarTablero(
                    hijo,
                    i + 1,
                    true
            );
        }
    }

    private void agregarTablero(
            TreeNode<Tablero> nodo,
            int numeroMovimiento,
            boolean mostrarUtilidad) {

        LinearLayout contenedor =
                new LinearLayout(this);

        contenedor.setOrientation(
                LinearLayout.VERTICAL
        );

        contenedor.setGravity(Gravity.CENTER);

        contenedor.setPadding(
                20,
                20,
                20,
                20
        );

        TextView titulo =
                new TextView(this);

        titulo.setText(
                "Movimiento " + numeroMovimiento
        );

        titulo.setTextSize(18);

        titulo.setGravity(Gravity.CENTER);

        titulo.setTextColor(Color.BLACK);

        contenedor.addView(titulo);

        GridLayout tableroVisual =
                crearTableroVisual(
                        nodo.getContent()
                );

        contenedor.addView(tableroVisual);

        if (mostrarUtilidad) {

            TextView utilidad =
                    new TextView(this);

            utilidad.setText(
                    "Utilidad: "
                            + nodo.getUtilidad()
            );

            utilidad.setTextSize(16);

            utilidad.setGravity(Gravity.CENTER);

            utilidad.setPadding(
                    0,
                    10,
                    0,
                    0
            );

            contenedor.addView(utilidad);
        }

        LinearLayout.LayoutParams parametros =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametros.setMargins(
                0,
                10,
                0,
                20
        );

        contenedor.setLayoutParams(parametros);

        if (!enSegundoNivel) {
            contenedor.setClickable(true);
            contenedor.setOnClickListener(
                    v -> mostrarSegundoNivel(nodo)
            );
        }

        contenedorTableros.addView(contenedor);
    }

    private GridLayout crearTableroVisual(Tablero tablero) {

        GridLayout grid = new GridLayout(this);

        grid.setColumnCount(3);
        grid.setRowCount(3);

        int tamano = 65;

        for (int fila = 0; fila < 3; fila++) {

            for (int col = 0; col < 3; col++) {

                TextView casilla = new TextView(this);

                Simbolo simbolo =
                        tablero.getCasilla(fila, col);

                if (simbolo == Simbolo.X) {
                    casilla.setText("X");
                } else if (simbolo == Simbolo.O) {
                    casilla.setText("O");
                } else {
                    casilla.setText("");
                }

                casilla.setTextSize(24);
                casilla.setGravity(Gravity.CENTER);
                casilla.setTextColor(Color.BLACK);

                android.graphics.drawable.GradientDrawable fondo =
                        new android.graphics.drawable.GradientDrawable();

                fondo.setColor(Color.LTGRAY);
                fondo.setStroke(2, Color.WHITE);

                casilla.setBackground(fondo);

                GridLayout.LayoutParams parametros =
                        new GridLayout.LayoutParams();

                parametros.width = tamano;
                parametros.height = tamano;

                parametros.setMargins(
                        2,
                        2,
                        2,
                        2
                );

                casilla.setLayoutParams(parametros);

                grid.addView(casilla);
            }
        }

        return grid;
    }

    private void mostrarSegundoNivel(
            TreeNode<Tablero> nodoSeleccionado) {

        enSegundoNivel = true;

        contenedorTableros.removeAllViews();

        tvTituloPensamiento.setText(
                "Movimientos derivados"
        );

        tvInstruccionPensamiento.setText(
                "Estos son los movimientos posibles a partir del tablero seleccionado"
        );

        List<TreeNode<Tablero>> hijos =
                nodoSeleccionado.getChildren();

        for (int i = 0; i < hijos.size(); i++) {

            TreeNode<Tablero> hijo =
                    hijos.get(i);

            agregarTablero(
                    hijo,
                    i + 1,
                    true
            );
        }
    }
}