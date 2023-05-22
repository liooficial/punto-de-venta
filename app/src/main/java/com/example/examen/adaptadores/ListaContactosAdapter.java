package com.example.examen.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.examen.R;
import com.example.examen.entidades.PRODUTOS;

import java.util.ArrayList;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder>{
    ArrayList<PRODUTOS> listaContactos;

    public ListaContactosAdapter(ArrayList<PRODUTOS> listaContactos){
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_usuarios, null, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.viewid.setText(listaContactos.get(position).getId());
        holder.viewnombre.setText(listaContactos.get(position).getNombre());
        holder.viewforma.setText(listaContactos.get(position).getForma());
        holder.viewvolumen.setText(listaContactos.get(position).getVolumen());
        holder.viewprecio.setText(listaContactos.get(position).getPrecio());
        holder.viewcantidad.setText(listaContactos.get(position).getCantidad());
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView viewid, viewnombre, viewforma,viewvolumen, viewprecio, viewcantidad;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewid = itemView.findViewById(R.id.id);
            viewnombre = itemView.findViewById(R.id.nombre);
            viewforma = itemView.findViewById(R.id.forma);
            viewvolumen = itemView.findViewById(R.id.volumen);
            viewprecio = itemView.findViewById(R.id.precio);
            viewcantidad = itemView.findViewById(R.id.cantidad);
        }
    }
}
