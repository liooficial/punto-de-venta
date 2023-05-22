package com.example.examen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
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
import com.example.examen.adaptadores.CustomAdapter;
import com.example.examen.entidades.PRODUTOS;
import com.example.examen.entidades.Item;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class MainActivity4 extends AppCompatActivity {
    Button  usuarios, producto, venta,inventario,salir,btn_menu,agregar,cancelar,compar;
    DrawerLayout mDrawerLayout;
    RecyclerView listaproductos;
    TextView total,canbio;
    EditText IMPORTE;
    CustomAdapter adapter = new CustomAdapter();
    public List<String> miLista = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        usuarios = (Button) findViewById(R.id.usuarios);
        producto = (Button) findViewById(R.id.producto);
        venta = (Button) findViewById(R.id.venta);
        inventario = (Button) findViewById(R.id.inventario);
        salir = (Button) findViewById(R.id.salir);
        btn_menu = (Button) findViewById(R.id.btn_menu);
        mDrawerLayout  = findViewById(R.id.drawer_layout);
        cancelar=(Button) findViewById(R.id.cancelar);
        agregar=(Button) findViewById(R.id.agregar);
        compar=(Button) findViewById(R.id.compar);
        total=(TextView) findViewById(R.id.total);
        canbio=(TextView) findViewById(R.id.canbio);
        IMPORTE=(EditText) findViewById(R.id.IMPORTE);

        listaproductos=(RecyclerView)findViewById(R.id.listaproductos);
        listaproductos.setAdapter(adapter);
        listaproductos.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String tipo = intent.getStringExtra("tipo");
        String usuario = intent.getStringExtra("usuario");

        if(tipo.equals("vendedor")){
            usuarios.setEnabled(false);
        }
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity4.this,"error"+e,Toast.LENGTH_LONG).show();

                }

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cancelar();
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity4.this,"error"+e,Toast.LENGTH_LONG).show();
                }
            }
        });
        compar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(IMPORTE.getText().length() == 0){
                        Toast.makeText(MainActivity4.this,"Pago vacio",Toast.LENGTH_LONG).show();
                    }else{
                        Float a=Float.valueOf((String) total.getText());
                        Float b=Float.valueOf(String.valueOf(IMPORTE.getText()));
                        Float r;
                        if (a<=b){
                            adapter.setPurchaseCompleted(true);
                            adapter.clearItems();
                            total.setText(""+0);
                            IMPORTE.setText(""+0);
                            r=b-a;
                            canbio.setText(r.toString());
                        }else{
                            Toast.makeText(MainActivity4.this,"No te alcanza",Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity4.this,"error"+e,Toast.LENGTH_LONG).show();

                }
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    escanner();
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity4.this,"error"+e,Toast.LENGTH_LONG).show();

                }
            }
        });

        usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity4.this, usuariosin.class);
                    intent.putExtra("tipo", tipo);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity4.this,"error"+e,Toast.LENGTH_LONG).show();

                }
            }
        });
        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity4.this, refisproducto.class);
                    intent.putExtra("tipo", tipo);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity4.this,"error"+e,Toast.LENGTH_LONG).show();

                }
            }
        });
        inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity4.this, com.example.examen.inventario.class);
                    intent.putExtra("tipo", tipo);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity4.this,"error"+e,Toast.LENGTH_LONG).show();

                }
            }
        });
        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity4.this, MainActivity4.class);
                    intent.putExtra("tipo", tipo);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity4.this,"error"+e,Toast.LENGTH_LONG).show();

                }
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity4.this, login.class);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity4.this,"error"+e,Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    public void  escanner(){
        IntentIntegrator intregador= new IntentIntegrator( MainActivity4.this);
        intregador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intregador.setPrompt("producto");
        intregador.setCameraId(0);
        intregador.setBeepEnabled(true);
        intregador.setBarcodeImageEnabled(true);
        intregador.initiateScan();
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult resultado=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(resultado != null){
            if(resultado.getContents()==null){
                Toast.makeText(this,"lesctura canselar ",Toast.LENGTH_LONG).show();
            }
            else{
                String id2=resultado.getContents().toString();
                String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_buscar_produc.php?id="+id2;
                Buscar_base(url,id2);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void Buscar_base(String s,String id) {
        JsonArrayRequest stringaray=new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        int numeroComoEntero = Integer.parseInt(jsonObject.getString("cantidad"));
                        numeroComoEntero=numeroComoEntero-1;
                        if(numeroComoEntero<0){
                            Toast.makeText(MainActivity4.this,"Ya no hay producto",Toast.LENGTH_LONG).show();
                        }else{
                            String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_update_produccantidad.php";
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id",id);
                            miLista.add(id);
                            params.put("cantidad", String.valueOf(numeroComoEntero));
                            modificar_base(url, params);
                            adapter.addItem(new Item(jsonObject.getString("id"), jsonObject.getString("nombre"), jsonObject.getString("forma"),jsonObject.getString("volumen"), jsonObject.getString("precio"), jsonObject.getString("cantidad")));
                            double precioTotal = adapter.getPrecioTotal();
                            total.setText(String.valueOf(precioTotal));
                        }

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
    private void modificar_base(String url, final Map<String, String> params) {
        StringRequest stringrequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "voidm" +error.getMessage(), Toast.LENGTH_LONG).show();
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
    private void cancelar(){

        System.out.println("fgdgyt  "+miLista.size());
        for (int i = 0; i < miLista.size(); i++) {
            String id = miLista.get(i);
            String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_buscar_produc.php?id="+id;
            System.out.println(id);
            Buscar_base2(url,id);
            System.out.println(url);
        }
        miLista.clear();
        adapter.setPurchaseCompleted(true);
        adapter.clearItems();
        total.setText(""+0);
        IMPORTE.setText(""+0);
    }
    private void Buscar_base2(String s,String id) {
        final CountDownLatch latch = new CountDownLatch(1);
        JsonArrayRequest stringaray=new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        int numeroComoEntero = Integer.parseInt(jsonObject.getString("cantidad"));
                        numeroComoEntero=numeroComoEntero+1;
                        System.out.println(numeroComoEntero);
                        String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_update_produccantidad.php";
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id",id);
                        params.put("cantidad", String.valueOf(numeroComoEntero));
                        modificar_base(url, params);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "json"+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                latch.countDown();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                latch.countDown();
                Toast.makeText(getApplicationContext(), "void" +error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(error.getMessage());
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringaray);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


