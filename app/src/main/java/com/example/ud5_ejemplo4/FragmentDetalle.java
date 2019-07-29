package com.example.ud5_ejemplo4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentDetalle extends Fragment {
    // Clave para crear el bundle.
    public static String CLAVE_NOMBRE = "CLAVE_NOMBRE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenemos el bundle y si es correcto mostramos el robot seleccionado
        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(CLAVE_NOMBRE)) {
            // Obtenemos el nombre en función de la clave y lo pasamos al método
            mostrarRobotSeleccionado(bundle.getString(CLAVE_NOMBRE));
        }
    }

    public void mostrarRobotSeleccionado(String nombre) {
        ((TextView)getView().findViewById(R.id.textViewNombre)).setText(nombre);
    }
}
