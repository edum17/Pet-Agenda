package com.example.german.controlmascotas;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by German on 01/04/2016.
 */
public class ListaMascotas extends Fragment {

    SQLControlador dbconeccion;
    ListView lista;
    ExpandableListView listaFiltro;
    Context context;
    private CharSequence mTitle;
    Spinner spinFiltro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lay_lista_mascotas, container, false);

        context = rootView.getContext();
        mTitle = "Mascotas";
        dbconeccion = new SQLControlador(context);
        dbconeccion.abrirBaseDatos();


        lista = (ListView) rootView.findViewById(R.id.listViewMascotas);
        listarMascotas();
        spinFiltro = (Spinner) rootView.findViewById(R.id.spinnerFiltro);
        filtrar();
        setHasOptionsMenu(true);

        return rootView;
    }

    private void filtrar() {
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(context,R.array.lista_filtro,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFiltro.setAdapter(adapter);
        spinFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) listarMascotas();
                else if (position == 1) listarTipoAnimales();
                else if (position == 2) Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                else if (position == 3) Toast.makeText(context, "3", Toast.LENGTH_SHORT).show();
                else if (position == 4) Toast.makeText(context, "4", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void listarTipoAnimales() {
        //Toast.makeText(getActivity(), "Listo todos los tipos de animales", Toast.LENGTH_SHORT).show();
        final ArrayList<String> tipoM = dbconeccion.listarTiposAnimales();

        //Creacion de sequencia de items
        final CharSequence[] Animals = tipoM.toArray(new String[tipoM.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Animales");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String animalSel = Animals[item].toString();  //Selected item in listview
                listarMascotasPorTipo(animalSel);
            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //Creacion del objeto alert dialog via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Mostramos el dialog
        alertDialogObject.show();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        restoreActionBar();
        inflater.inflate(R.menu.menu_listar_mascotas, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_AyudaListaMascotas) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Ayuda").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
            builder.setMessage("ListaMascota");
            builder.setNeutralButton("Aceptar",null);
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void listarMascotas() {
        final ListViewAdapterMascotas adapter = new ListViewAdapterMascotas(context,dbconeccion.listarMascotas());
        adapter.notifyDataSetChanged();
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = {"Eliminar mascota", "Modificar mascota"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Mascota");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Eliminar mascota")) {
                            String nomC = adapter.getItemName(position);
                            String identficador = Long.toString(adapter.getItemId(position));
                            dbconeccion.eliminarMascotaYCitas(Integer.parseInt(identficador));
                            Toast.makeText(context, "La mascota " + nomC + " ha sido eliminada", Toast.LENGTH_SHORT).show();
                            adapter.updateAdapter(dbconeccion.listarMascotas());
                        }
                        else if (items[which].equals("Modificar mascota")) {
                            Intent consultar_mascota = new Intent(context,ConsultarMascota.class);
                            String identficador = Long.toString(adapter.getItemId(position));
                            consultar_mascota.putExtra("idMascota", identficador);
                            getActivity().startActivity(consultar_mascota);
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private void listarMascotasPorTipo(final String tipo) {
        final ListViewAdapterMascotas adapter = new ListViewAdapterMascotas(context,dbconeccion.listarMascotasPorTipo(tipo));
        adapter.notifyDataSetChanged();
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = {"Eliminar mascota", "Modificar mascota"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Mascota");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Eliminar mascota")) {
                            String nomC = adapter.getItemName(position);
                            String identficador = Long.toString(adapter.getItemId(position));
                            dbconeccion.eliminarMascotaYCitas(Integer.parseInt(identficador));
                            Toast.makeText(context, "La mascota " + nomC + " ha sido eliminada", Toast.LENGTH_SHORT).show();
                            adapter.updateAdapter(dbconeccion.listarMascotasPorTipo(tipo));
                        }
                        else if (items[which].equals("Modificar mascota")) {
                            Intent consultar_mascota = new Intent(context,ConsultarMascota.class);
                            String identficador = Long.toString(adapter.getItemId(position));
                            consultar_mascota.putExtra("idMascota", identficador);
                            getActivity().startActivity(consultar_mascota);
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

}