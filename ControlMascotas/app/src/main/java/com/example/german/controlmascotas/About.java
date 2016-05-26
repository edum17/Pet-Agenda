package com.example.german.controlmascotas;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by German on 26/05/2016.
 */
public class About extends Fragment {

    Context context;

    View rootView;
    TextView mensaje;
    private CharSequence mTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        rootView = inflater.inflate(R.layout.lay_about, container, false);
        mTitle = "Agenda";
        mensaje = (TextView) rootView.findViewById(R.id.textViewAbout);
        mensaje.setText("Mensaje de about");
        setHasOptionsMenu(true);

        return rootView;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        //Eliminamos el titulo de la app en todos los fragments
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        restoreActionBar();
        inflater.inflate(R.menu.menu_agenda, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
