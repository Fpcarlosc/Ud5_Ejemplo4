package com.example.ud5_ejemplo4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentPrincipal extends ListFragment {

    private OnPrincipalSelectedListener onPrincipalSelectedListener = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] robots = new String[]{"R2D2", "Mazinger Z", "Arale", "Robocop", "Johnny 5", "Blender", "T-800"};

        //Cramos el ListAdapter y se lo asignamos al ListFragment
        ListAdapter robotsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, robots);

        setListAdapter(robotsAdapter);

        // Obtenemos la ListView y le asignamos el evento
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View
                    view, int position, long id) {
                if (onPrincipalSelectedListener != null) {
                    onPrincipalSelectedListener.onItemSelected(((TextView) view).getText().toString());
                }
            }
        });
    }

    /**
     * Método para asignar el Listener al pulsar un elemento de FramentPrincipal.
     * @param listener
     */
    public void setOnPrincipalSelectedListener(OnPrincipalSelectedListener listener) {
        onPrincipalSelectedListener = listener;
    }

    /**
     * Interfaz para controlar los elementos pulsados de FragmentPrincipal
     */
    public interface OnPrincipalSelectedListener {
        void onItemSelected(String nombre);
    }
}
