package com.example.german.controlmascotas;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by German on 01/04/2016.
 */
public class Agenda extends Fragment {

    Context context;
    SQLControlador dbconeccion;
    ListView listaDia;
    View rootView;
    private CharSequence mTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        dbconeccion = new SQLControlador(context);
        dbconeccion.abrirBaseDatos();
        rootView = inflater.inflate(R.layout.lay_agenda, container, false);
        mTitle = "Agenda";

        listaDia = (ListView) rootView.findViewById(R.id.listViewAgenda);

        listarCitas();

        setHasOptionsMenu(true);

        return rootView;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        //Eliminamos el titulo de la app en todos los fragments
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setIcon(R.drawable.icono_agenda);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        restoreActionBar();
        inflater.inflate(R.menu.menu_agenda,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_AyudaAgenda) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Ayuda").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
            builder.setMessage("Si se desea eliminar o modificar (consultar) una cita, tan sólo se tiene que seleccionar y elegir una de las dos opciones.");
            builder.setNeutralButton("Aceptar",null);
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void listarCitas() {
        ArrayList<Cita> citas = dbconeccion.listarDiasAgenda();
        final ListViewAdapterDiasAgenda adapterDiaFecha = new ListViewAdapterDiasAgenda(context, citas);
        adapterDiaFecha.notifyDataSetChanged();
        listaDia.setAdapter(adapterDiaFecha);
    }

    private void crearCita() {
        //Singleton.getInstance().setContext(context);
        //Singleton.getInstance().setContainerId(((ViewGroup)getView().getParent()).getId());
        //System.out.println("*************************** ((ViewGroup)getView().getParent()).getId(): " + ((ViewGroup)getView().getParent()).getId());
        Intent creaCita = new Intent(context,NuevaCita.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(creaCita);
    }
}
