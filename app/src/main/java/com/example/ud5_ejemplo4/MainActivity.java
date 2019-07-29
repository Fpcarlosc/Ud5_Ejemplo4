package com.example.ud5_ejemplo4;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private boolean rotado; // Atributo para ver si el dispositivo está rotado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentPrincipal fragmentPrincipal;

        FrameLayout frameLayout = findViewById(R.id.frameLayout);

        // Si framLayout tiene valor es porque el dispositivo no está rotado
        if (frameLayout != null) {
            rotado = false;

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            //Buscamos el Fragment por el tag "PRINCIPAL"
            fragmentPrincipal = (FragmentPrincipal) getSupportFragmentManager().findFragmentByTag("PRINCIPAL");

            // Si no lo encontramos lo añadimos a la transacción con el tag "PRINCIPAL"
            if (fragmentPrincipal == null) {
                fragmentPrincipal = new FragmentPrincipal();
                fragmentTransaction.add(R.id.frameLayout, fragmentPrincipal, "PRINCIPAL");
            }

            //Buscamos el Fragment por el id
            FragmentDetalle fragmentDetalle = (FragmentDetalle)getSupportFragmentManager().findFragmentById(R.id.frameLayoutDetalle);

            // Si está mostrado lo borramos
            if (fragmentDetalle != null) {
                fragmentTransaction.remove(fragmentDetalle);
            }

            fragmentTransaction.commit();
        }
        else {

            rotado = true;

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            //Buscamos el Fragment por el id
            fragmentPrincipal = (FragmentPrincipal)getSupportFragmentManager().findFragmentById(R.id.frameLayoutPrincipal);

            // Si no lo encontramos lo añadimos a la transacción
            if (fragmentPrincipal == null) {
                fragmentPrincipal = new FragmentPrincipal();
                fragmentTransaction.add(R.id.frameLayoutPrincipal, fragmentPrincipal);
            }

            //Buscamos el Fragment por el id
            FragmentDetalle fragmentDetalle= (FragmentDetalle) getSupportFragmentManager().findFragmentById(R.id.frameLayoutDetalle);

            // Si no lo encontramos lo añadimos a la transacción
            if (fragmentDetalle == null) {
                fragmentDetalle = new FragmentDetalle();
                fragmentTransaction.add(R.id.frameLayoutDetalle, fragmentDetalle);
            }

            fragmentTransaction.commit();
        }

        // Asignamos el Listener creado en la clase FragmentPrincipal
        fragmentPrincipal.setOnPrincipalSelectedListener(new FragmentPrincipal.OnPrincipalSelectedListener() {
            @Override
            public void onItemSelected(String nombre) {
                enviarNombre(nombre);
            }
        });

    }

    /**
     * Método para enviar el nombre a los Fragments y mostrarlo
     * @param nombre
     */
    private void enviarNombre(String nombre) {

        FragmentDetalle fragmentDetalle;

        if (rotado) {
            //Mostramos también el detalle
            fragmentDetalle = (FragmentDetalle) getSupportFragmentManager().findFragmentById(R.id.frameLayoutDetalle);

            fragmentDetalle.mostrarRobotSeleccionado(nombre);
        }
        else {
            // Obtenemos el nombre a partir de la clave y reemplazamos FragmentPrincipal por FragmentDetalle
            fragmentDetalle = new FragmentDetalle();

            // Creamos el bundle con la clave y el nombre pasado
            Bundle bundle = new Bundle();
            bundle.putString(FragmentDetalle.CLAVE_NOMBRE, nombre);

            fragmentDetalle.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragmentDetalle);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}



