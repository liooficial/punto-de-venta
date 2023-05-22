package com.example.examen.adaptadores;
import android.database.sqlite.SQLiteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen.R;
import com.example.examen.entidades.Item;

import java.util.ArrayList;
import java.util.List;
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Item> itemList;
    private double precioTotal = 0;
    private boolean purchaseCompleted = false;
    public CustomAdapter() {
        this.itemList = new ArrayList<>();
        this.purchaseCompleted = purchaseCompleted;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ventas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.idT.setText(item.getId());
        holder.nombreT.setText(item.getNombre());
        holder.formaT.setText(item.getForma());
        holder.volumenT.setText(item.getVolumen());
        holder.precioT.setText(item.getPrecio());
        holder.cantidadT.setText(item.getCantidad());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(Item item) {
        itemList.add(item);
        precioTotal += Double.parseDouble(item.getPrecio()) * Integer.parseInt(item.getCantidad());
        notifyItemInserted(itemList.size() - 1);
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void clearItems() {
        if (purchaseCompleted) {

        }
        itemList.clear();
        precioTotal = 0;
        notifyDataSetChanged();
    }

    public void setPurchaseCompleted(boolean purchaseCompleted) {
        this.purchaseCompleted = purchaseCompleted;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idT,nombreT,formaT,volumenT,precioT,cantidadT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idT = itemView.findViewById(R.id.id);
            nombreT = itemView.findViewById(R.id.nombre);
            formaT = itemView.findViewById(R.id.forma);
            volumenT = itemView.findViewById(R.id.volumen);
            precioT = itemView.findViewById(R.id.precio);
            cantidadT = itemView.findViewById(R.id.cantidad);
        }
    }
}