package com.example.german.controlmascotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by German on 21/05/2016.
 */
public class ListViewAdapterCitasMascota extends BaseAdapter {

    private Context context;
    private ArrayList<Cita> citas;
    private LayoutInflater inflater;

    public ListViewAdapterCitasMascota(Context context, ArrayList<Cita> citas) {
        this.context = context;
        this.citas = citas;
    }

    @Override
    public int getCount() {
        return citas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public String getItemFechaC(int position) {
        return citas.get(position).getFecha();
    }

    public String getItemHoraIni(int position) {
        return citas.get(position).getHoraIni();
    }

    public void updateAdapter(ArrayList<Cita> arrylst) {
        citas = arrylst;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView fechaNac, txtHoraIni, txtTipoCitaCM;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_fila_citas_mascota, parent, false);

        fechaNac = (TextView) itemView.findViewById(R.id.fila_cita_fechaCM);
        txtHoraIni = (TextView) itemView.findViewById(R.id.fila_cita_horaIniCM);
        txtTipoCitaCM = (TextView) itemView.findViewById(R.id.fila_cita_tipoCitaCM);

        fechaNac.setText(citas.get(position).getFecha());
        txtHoraIni.setText(citas.get(position).getHoraIni());
        txtTipoCitaCM.setText(citas.get(position).getTipo());

        return itemView;
    }
}
