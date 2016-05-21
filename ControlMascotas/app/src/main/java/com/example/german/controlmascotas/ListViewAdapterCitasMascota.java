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

    Context context;
    ArrayList<Cita> citas;
    LayoutInflater inflater;

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

    public void updateAdapter(ArrayList<Cita> arrylst) {
        citas = arrylst;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtFechaNac, txtHoraIni, txtSepadorCM,txtHoraFin, txtTipoCitaCM;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_fila_lista_mascotas,parent,false);

        txtFechaNac = (TextView) itemView.findViewById(R.id.fila_cita_fechaCM);
        txtHoraIni = (TextView) itemView.findViewById(R.id.fila_cita_horaIniCM);
        txtSepadorCM = (TextView) itemView.findViewById(R.id.fila_cita_separadorCM);
        txtHoraFin = (TextView) itemView.findViewById(R.id.fila_cita_horaFinCM);
        txtTipoCitaCM = (TextView) itemView.findViewById(R.id.fila_cita_tipoCitaCM);

        txtFechaNac.setText(citas.get(position).getFecha());
        txtHoraIni.setText(citas.get(position).getHoraIni());
        txtSepadorCM.setText("-");
        txtHoraFin.setText(citas.get(position).getHoraFin());
        txtTipoCitaCM.setText(citas.get(position).getTipo());


        return itemView;
    }
}
