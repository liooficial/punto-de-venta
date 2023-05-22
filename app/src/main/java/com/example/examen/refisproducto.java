package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class refisproducto extends AppCompatActivity {
Button b1,b2,b3,b4,b5, usuarios, producto, venta,inventario,salir,btn_menu;
DrawerLayout mDrawerLayout;
EditText nombre,forma,volumen,precio,cantidad;
TextView id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refisproducto);
        b1 = (Button) findViewById(R.id.btscaner);
        b2 = (Button) findViewById(R.id.bt1);
        b3 = (Button) findViewById(R.id.bt2);
        b4 = (Button) findViewById(R.id.bt3);
        b5 = (Button) findViewById(R.id.bt4);
        id = (TextView) findViewById(R.id.id2);
        nombre = (EditText) findViewById(R.id.n2);
        forma = (EditText) findViewById(R.id.Forma2);
        volumen = (EditText) findViewById(R.id.v2);
        precio = (EditText) findViewById(R.id.precio_v2);
        cantidad = (EditText) findViewById(R.id.catidadp2);

        usuarios = (Button) findViewById(R.id.usuarios);
        producto = (Button) findViewById(R.id.producto);
        venta = (Button) findViewById(R.id.venta);
        inventario = (Button) findViewById(R.id.inventario);
        salir = (Button) findViewById(R.id.salir);
        btn_menu= (Button) findViewById(R.id.btn_menu);

        mDrawerLayout  = findViewById(R.id.drawer_layout);


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

                Intent intent = new Intent(refisproducto.this, usuariosin.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(refisproducto.this, refisproducto.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(refisproducto.this, com.example.examen.inventario.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(refisproducto.this, MainActivity4.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);

            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(refisproducto.this, login.class);
                startActivity(intent);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    escanner();
                }catch (SQLException e){
                    Toast.makeText(refisproducto.this,"error"+e,Toast.LENGTH_LONG).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar(view);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar(view);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(view);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar(view);
            }
        });
    }
    public void  escanner(){
        IntentIntegrator intregador= new IntentIntegrator( refisproducto.this);
        intregador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intregador.setPrompt("producto");
        intregador.setCameraId(0);
        intregador.setBeepEnabled(true);
        intregador.setBarcodeImageEnabled(true);
        intregador.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult resultado=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(resultado != null){
            if(resultado.getContents()==null){
                Toast.makeText(this,"lesctura canselar ",Toast.LENGTH_LONG).show();
            }
            else{
                id.setText(resultado.getContents().toString());
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
        public void insertar(View view){

            String id1=id.getText().toString();
            String nombre2=nombre.getText().toString();
            String forma2=forma.getText().toString();
            String volumen2=volumen.getText().toString();
            String precio2=precio.getText().toString();
            String cantidad2=cantidad.getText().toString();
            if(id1.isEmpty() && nombre2.isEmpty() && forma2.isEmpty()&& volumen2.isEmpty()&& precio2.isEmpty()&& cantidad2.isEmpty()){
                Toast.makeText(refisproducto.this,"campos vacios",Toast.LENGTH_SHORT).show();
            }else{
                String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_insertar_produc.php";
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id1);
                params.put("nombre", nombre2);
                params.put("forma", forma2);
                params.put("volumen", volumen2);
                params.put("precio", precio2);
                params.put("cantidad", cantidad2);
                    insertar_base(url,params);
                    id.setText("");
                    nombre.setText("");
                    forma.setText("");
                    volumen.setText("");
                    precio.setText("");
                    cantidad.setText("");

            }
        }
        public void buscar(View view ){
            String id2=id.getText().toString();
            if(!id2.isEmpty()){
                String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_buscar_produc.php?id="+id2;
                System.out.println(url);
                Buscar_base(url);
            }else{
                Toast.makeText(refisproducto.this,"escribe el id a buscar",Toast.LENGTH_SHORT).show();
            }
        }
        public void eliminar(View view ){
            sqllite elimina=new sqllite(this,null,null,1);
            SQLiteDatabase basededatos=elimina.getWritableDatabase();
            String id2=id.getText().toString()    ;
            if(!id2.isEmpty()){
                    String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_delete_produc.php";
                    eliminar_base(url, id2);
                    Toast.makeText(refisproducto.this,"se elimino corectamente",Toast.LENGTH_SHORT).show();
                    id.setText("");
                    nombre.setText("");
                    forma.setText("");
                    volumen.setText("");
                    precio.setText("");
                    cantidad.setText("");

            }else{
                Toast.makeText(refisproducto.this,"escribe el id a eliminar",Toast.LENGTH_SHORT).show();
            }
        }
        public void actualizar(View view ){


            String id2= id.getText().toString();
            String nombre2=nombre.getText().toString();
            String forma2=forma.getText().toString();
            String volumen2=volumen.getText().toString();
            String precio2=precio.getText().toString();
            String cantidad2=cantidad.getText().toString();

            if( nombre2.isEmpty() && forma2.isEmpty()&& volumen2.isEmpty()&& precio2.isEmpty()&& cantidad2.isEmpty()){
                Toast.makeText(refisproducto.this,"campos vacios",Toast.LENGTH_SHORT).show();
            }else{

                String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_update_produc.php";
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",id2);
                params.put("nombre", nombre2);
                params.put("forma", forma2);
                params.put("volumen", volumen2);
                params.put("precio", precio2);
                params.put("cantidad", cantidad2);
                modificar_base(url, params);

                    id.setText("");
                    nombre.setText("");
                    forma.setText("");
                    volumen.setText("");
                    precio.setText("");
                    cantidad.setText("");


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
    private void Buscar_base(String s) {
        JsonArrayRequest stringaray=new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        nombre.setText(jsonObject.getString("nombre"));
                        forma.setText(jsonObject.getString("forma"));
                        volumen.setText(jsonObject.getString("volumen"));
                        precio.setText(jsonObject.getString("precio"));
                        cantidad.setText(jsonObject.getString("cantidad"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "json"+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
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