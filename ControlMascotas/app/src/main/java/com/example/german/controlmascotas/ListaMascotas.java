package com.example.german.controlmascotas;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.Intent;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by German on 01/04/2016.
 */
public class ListaMascotas extends Fragment {

    private SQLControlador dbconeccion;
    private ListView lista;
    private Context context;
    private Button iBTodas, iBTipos, iBTratEsp, iBSinTrast, iBVacu;
    private CharSequence mTitle;

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
        iBTodas = (Button) rootView.findViewById(R.id.imageButTodas);
        iBTodas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarMascotas();
            }
        });
        iBTipos = (Button) rootView.findViewById(R.id.imageButTipos);
        iBTipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarTipoAnimales();
            }
        });
        iBTratEsp = (Button) rootView.findViewById(R.id.imageButTratEsp);
        iBTratEsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarTratamientoAnimales();
            }
        });
        iBSinTrast = (Button) rootView.findViewById(R.id.imageButSinTratEsp);
        iBSinTrast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarMascotasSinTratamiento();
            }
        });
        iBVacu = (Button) rootView.findViewById(R.id.imageButVacu);
        iBVacu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarMascotasPorIntervalo();
            }
        });
        setHasOptionsMenu(true);

        return rootView;
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

    public void listarTratamientoAnimales() {
        //Toast.makeText(getActivity(), "Listo todos los tipos de animales", Toast.LENGTH_SHORT).show();
        final ArrayList<String> medM = dbconeccion.listarTratamientoAnimales();

        //Creacion de sequencia de items
        final CharSequence[] Tratamiento = medM.toArray(new String[medM.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Tratemiento");
        dialogBuilder.setItems(Tratamiento, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String medMSel = Tratamiento[item].toString();  //Selected item in listview
                listarMascotasPorTratamientoSel(medMSel);
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
            builder.setMessage("Si se desea eliminar o modificar (consultar) una cita, tan sólo se tiene que seleccionar y elegir una de las dos opciones.\n" +
                    "Si se quiere filtrar el listado de las mascotas, se puede hacer por tipo, tratamiento especial, sin tratamiento y por vacunación.");
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Eliminar cita");
                            builder.setMessage("¿Desea eliminar ésta cita?");
                            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String nomC = adapter.getItemName(position);
                                    String identficador = Long.toString(adapter.getItemId(position));
                                    dbconeccion.eliminarMascotaYCitas(Integer.parseInt(identficador));
                                    Toast.makeText(context, "La mascota " + nomC + " ha sido eliminada", Toast.LENGTH_SHORT).show();
                                    adapter.updateAdapter(dbconeccion.listarMascotas());
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
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
                            ArrayList<Mascota> actualizada = dbconeccion.listarMascotasPorTipo(tipo);
                            if (actualizada.size() > 0) adapter.updateAdapter(actualizada);
                            else {
                                listarMascotas();
                            }
                        } else if (items[which].equals("Modificar mascota")) {
                            Intent consultar_mascota = new Intent(context, ConsultarMascota.class);
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

    private void listarMascotasPorTratamientoSel(final String tratamiento) {
        final ListViewAdapterMascotas adapter = new ListViewAdapterMascotas(context,dbconeccion.listarMascotasPorTratamiento(tratamiento));
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
                            ArrayList<Mascota> actualizada = dbconeccion.listarMascotasPorTratamiento(tratamiento);
                            if (actualizada.size() > 0) adapter.updateAdapter(actualizada);
                            else {
                                listarMascotas();
                            }
                        } else if (items[which].equals("Modificar mascota")) {
                            Intent consultar_mascota = new Intent(context, ConsultarMascota.class);
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

    private void listarMascotasSinTratamiento() {
        final ListViewAdapterMascotas adapter = new ListViewAdapterMascotas(context,dbconeccion.listarMascotasSinTratamiento());
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
                            ArrayList<Mascota> actualizada = dbconeccion.listarMascotasSinTratamiento();
                            if (actualizada.size() > 0) adapter.updateAdapter(actualizada);
                            else {
                                listarMascotas();
                            }
                        } else if (items[which].equals("Modificar mascota")) {
                            Intent consultar_mascota = new Intent(context, ConsultarMascota.class);
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

    private void listarMascotasPorIntervalo() {

        Calendar mcurrentDate=Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth=mcurrentDate.get(Calendar.MONTH)+1;
        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        final Dialog d = new Dialog(context);
        d.setTitle("Intervalo de fechas");
        d.setContentView(R.layout.dialog_lista_intervalo);

        Button b1 = (Button) d.findViewById(R.id.butFiltrar);
        Button b2 = (Button) d.findViewById(R.id.butCancelar);


        //Fecha de inicio del intervalo
        final NumberPicker np1dia = (NumberPicker) d.findViewById(R.id.numberP1Dia);
        np1dia.setMaxValue(31);
        np1dia.setValue(mDay);
        np1dia.setMinValue(1);
        np1dia.setWrapSelectorWheel(false);

        final NumberPicker np1mes = (NumberPicker) d.findViewById(R.id.numberP1Mes);
        np1mes.setMaxValue(12);
        np1mes.setValue(mMonth);
        np1mes.setMinValue(1);
        np1mes.setWrapSelectorWheel(false);

        final NumberPicker np1ano = (NumberPicker) d.findViewById(R.id.numberP1Ano);
        np1ano.setMaxValue(2050);
        np1ano.setValue(mYear);
        np1ano.setMinValue(2015);
        np1ano.setWrapSelectorWheel(false);


        //Fecha de finalizacion del intervalo
        final NumberPicker np2dia = (NumberPicker) d.findViewById(R.id.numberP2Dia);
        np2dia.setMaxValue(31);
        np2dia.setValue(mDay);
        np2dia.setMinValue(1);
        np2dia.setWrapSelectorWheel(false);

        final NumberPicker np2mes = (NumberPicker) d.findViewById(R.id.numberP2Mes);
        np2mes.setMaxValue(12);
        np2mes.setValue(mMonth);
        np2mes.setMinValue(1);
        np2mes.setWrapSelectorWheel(false);

        final NumberPicker np2ano = (NumberPicker) d.findViewById(R.id.numberP2Ano);
        np2ano.setMaxValue(2050);
        np2ano.setValue(mYear);
        np2ano.setMinValue(2015);
        np2ano.setWrapSelectorWheel(false);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String fechaIni = String.valueOf(np1ano.getValue()) + "-";
                if (np1mes.getValue() < 10) fechaIni += "0" + String.valueOf(np1mes.getValue()) + "-";
                else fechaIni += String.valueOf(np1mes.getValue()) + "-";
                if (np1dia.getValue() < 10) fechaIni += "0" + String.valueOf(np1dia.getValue());
                else fechaIni += String.valueOf(np1dia.getValue());

                String fechaFin = String.valueOf(np2ano.getValue()) + "-";
                if (np2mes.getValue() < 10) fechaFin += "0" + String.valueOf(np2mes.getValue()) + "-";
                else fechaFin += String.valueOf(np2mes.getValue()) + "-";
                if (np2dia.getValue() < 10) fechaFin += "0" + String.valueOf(np2dia.getValue());
                else fechaFin += String.valueOf(np2dia.getValue());

                final String finalFechaIni = fechaIni;
                final String finalFechaFin = fechaFin;
                listarMascotasEnlasFechas(finalFechaIni, finalFechaFin);
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();
    }

    private void listarMascotasEnlasFechas(final String fechaIni, final String fechaFin) {
        ArrayList<Mascota> Mascotas = dbconeccion.listarMascotasEnElIntervalo(fechaIni,fechaFin);
        final ListViewAdapterMascotas adapter = new ListViewAdapterMascotas(context, Mascotas);
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
                            ArrayList<Mascota> actualizada = dbconeccion.listarMascotasEnElIntervalo(fechaIni,fechaFin);
                            if (actualizada.size() > 0) adapter.updateAdapter(actualizada);
                            else {
                                listarMascotas();
                            }
                        } else if (items[which].equals("Modificar mascota")) {
                            Intent consultar_mascota = new Intent(context, ConsultarMascota.class);
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