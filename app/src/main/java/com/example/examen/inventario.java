package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.examen.adaptadores.ListaContactosAdapter;
import com.example.examen.entidades.PRODUTOS;

public class inventario extends AppCompatActivity {
    ArrayList<PRODUTOS> listaArrayContactos;
    RecyclerView LISTA;
    Button  usuarios, producto, venta,inventario,salir,btn_menu;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventario);

        LISTA=(RecyclerView)findViewById(R.id.listausuariosl);
        LISTA.setLayoutManager(new LinearLayoutManager(this));
        sqllite dbContactos = new sqllite(this,null,null,1);
        listaArrayContactos = new ArrayList<>();
        ListaContactosAdapter adapter = new ListaContactosAdapter(dbContactos.mostrarContactos());
        LISTA.setAdapter(adapter);

        usuarios = (Button) findViewById(R.id.usuarios);
        producto = (Button) findViewById(R.id.producto);
        venta = (Button) findViewById(R.id.venta);
        inventario = (Button) findViewById(R.id.inventario);
        salir = (Button) findViewById(R.id.salir);
        btn_menu= (Button) findViewById(R.id.btn_menu);
        mDrawerLayout  = findViewById(R.id.mDrawerLayout);

        Intent intent = getIntent();
        String tipo = intent.getStringExtra("tipo");
        String usuario = intent.getStringExtra("usuario");

        if(tipo.equals("vendedor")){
            usuarios.setEnabled(false);
            producto.setEnabled(false);
        }

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(com.example.examen.inventario.this, usuariosin.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(com.example.examen.inventario.this, refisproducto.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(com.example.examen.inventario.this, com.example.examen.inventario.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.examen.inventario.this, MainActivity4.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);

            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(com.example.examen.inventario.this, login.class);
                startActivity(intent);
            }
        });
    }
}