package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class usuariosin extends AppCompatActivity {
    EditText ID,contraseña;
    Button modificar,agregar,eliminar,usuarios, producto, venta,inventario,salir,buscar,btn_menu;
    DrawerLayout mDrawerLayout;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuariosin);
        ID = (EditText) findViewById(R.id.IDu);
        contraseña = (EditText) findViewById(R.id.contraseña);
        modificar = (Button) findViewById(R.id.bt1);
        agregar = (Button) findViewById(R.id.bt2);
        eliminar = (Button) findViewById(R.id.bt3);
        usuarios = (Button) findViewById(R.id.usuarios);
        producto = (Button) findViewById(R.id.producto);
        venta = (Button) findViewById(R.id.venta);
        inventario = (Button) findViewById(R.id.inventario);
        salir = (Button) findViewById(R.id.salir);
        buscar= (Button) findViewById(R.id.buscar);
        btn_menu= (Button) findViewById(R.id.btn_menu);
        mDrawerLayout  = findViewById(R.id.drawer_layout);
        spinner = (Spinner) findViewById(R.id.usuario);

        Intent intent = getIntent();
        String tipo = intent.getStringExtra("tipo");
        String usuario = intent.getStringExtra("usuario");

        if(tipo.equals("vendedor")){
            usuarios.setEnabled(false);
            producto.setEnabled(false);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"vendedor", "administrador"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(usuariosin.this, usuariosin.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(usuariosin.this, refisproducto.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(usuariosin.this, com.example.examen.inventario.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(usuariosin.this, MainActivity4.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);

            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(usuariosin.this, login.class);
                startActivity(intent);
            }
        });
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    actualizar(view);
                }catch (Exception e){
                    Toast.makeText(usuariosin.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    insertar(view);
                }catch (Exception e){
                    Toast.makeText(usuariosin.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(view);
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar(view);
            }
        });
    }
    public void insertar(View view){

        String id=ID.getText().toString();
        String usu=spinner.getSelectedItem().toString();
        String contrase=contraseña.getText().toString();
        if(id.isEmpty() && contrase.isEmpty()){
            Toast.makeText(usuariosin.this,"campos vacios",Toast.LENGTH_LONG).show();
        }else{
            String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_insertar_user.php";
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", id);
            params.put("tipo", usu);
            params.put("contrase", contrase);
            insertar_base(url, params);
            ID.setText("");
            contraseña.setText("");
        }
    }
    public void eliminar(View view ){
        String id=ID.getText().toString();

        if(!id.isEmpty()){
            String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_delete_user.php";
            eliminar_base(url, id);
            contraseña.setText("");
            ID.setText("");

        }else{
            Toast.makeText(usuariosin.this,"escribe el id a eliminar",Toast.LENGTH_SHORT).show();
        }
    }
    public void buscar(View view ){
        String id=ID.getText().toString();
        if(!id.isEmpty()){
            try {
                String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_buscar_user.php?id="+id;
                System.out.println(url);
                JsonArrayRequest stringaray=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                String usuario2 = jsonObject.getString("id");
                                String tipo = jsonObject.getString("usuario");
                                String contraseñat = jsonObject.getString("contrase");
                                Toast.makeText(usuariosin.this,"registro encontado",Toast.LENGTH_SHORT).show();
                                int n=0;
                                if(tipo.equals("vendedor")){
                                    n=0;
                                }
                                if(tipo.equals("administrador")){
                                    n=1;
                                }
                                spinner.setSelection(n);
                                contraseña.setText(contraseñat);

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "json"+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "no existe el registro", Toast.LENGTH_LONG).show();
                        System.out.println(error.getMessage());
                    }
                });
                RequestQueue requestQueue= Volley.newRequestQueue(this);
                requestQueue.add(stringaray);

            }catch (SQLException e){
                Toast.makeText(usuariosin.this,"error"+e,Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(usuariosin.this,"escribe el id a buscar",Toast.LENGTH_SHORT).show();
        }
    }
    public void actualizar(View view ){


        String id= ID.getText().toString();
        String usu=spinner.getSelectedItem().toString();
        String contrase=contraseña.getText().toString();

        if(contrase.isEmpty( )){
            Toast.makeText(usuariosin.this,"campos vacios",Toast.LENGTH_SHORT).show();
        }else{
                try{
                    String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_update_user.php";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", id);
                    params.put("tipo", usu);
                    params.put("contrase", contrase);
                    modificar_base(url, params);
                    Toast.makeText(usuariosin.this,"regristo actualizado",Toast.LENGTH_SHORT).show();
                }catch (SQLException e){
                    Toast.makeText(usuariosin.this,"error al actualizar",Toast.LENGTH_SHORT).show();
                }
                contraseña.setText("");
                ID.setText("");
        }
    }
    private void eliminar_base(String url, String id) {
        StringRequest stringrequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "OPERACION REALIZADA ELIMINADO", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "no existe en la base", Toast.LENGTH_LONG).show();
                        System.out.println(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringrequest);
    }
    private void insertar_base(String url, final Map<String, String> params) {
        StringRequest stringrequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "OPERACION REALIZADA INSERTADA", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "void" +error.getMessage(), Toast.LENGTH_LONG).show();
                        System.out.println(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringrequest);
    }
    private void modificar_base(String url, final Map<String, String> params) {
        StringRequest stringrequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "OPERACION REALIZADA Modificado", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "void" +error.getMessage(), Toast.LENGTH_LONG).show();
                        System.out.println(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringrequest);
    }

}