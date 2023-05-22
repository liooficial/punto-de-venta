package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.examen.adaptadores.ListaContactosAdapter;
import com.example.examen.entidades.PRODUTOS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        mostrar2();
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
    public void mostrar2(){
        String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_todos_produc.php";
        ver_base(url);

    }
    private  void ver_base(String url) {
        ArrayList<PRODUTOS> listaContactos=new ArrayList<>();
    JsonArrayRequest stringaray=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            JSONObject jsonObject = null;
            PRODUTOS contacto;
            for (int i = 0; i < response.length(); i++) {
                try {
                    jsonObject = response.getJSONObject(i);
                    contacto = new PRODUTOS();
                    contacto.setId(jsonObject.getString("id"));
                    contacto.setNombre(jsonObject.getString("nombre"));
                    contacto.setForma(jsonObject.getString("forma"));
                    contacto.setVolumen(jsonObject.getString("volumen"));
                    contacto.setPrecio(jsonObject.getString("precio"));
                    contacto.setCantidad(jsonObject.getString("cantidad"));
                    System.out.println(contacto);
                    listaContactos.add(contacto);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "json"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            listaArrayContactos = new ArrayList<>();
            ListaContactosAdapter adapter = new ListaContactosAdapter(listaContactos);
            LISTA.setAdapter(adapter);

        }

    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "void" +error.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println(error.getMessage());
        }
    });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringaray);
    }

}