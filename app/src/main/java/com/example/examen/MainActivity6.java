package com.example.examen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity6 extends AppCompatActivity {

    Button btscaner,listo,cancelar;

    TextView id,nombre,forma,volumen,precio,cantidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        id = (TextView) findViewById(R.id.id2);
        nombre = (TextView) findViewById(R.id.n2);
        forma = (TextView) findViewById(R.id.Forma2);
        volumen = (TextView) findViewById(R.id.v2);
        precio = (TextView) findViewById(R.id.precio_v2);
        cantidad = (TextView) findViewById(R.id.catidadp2);

        btscaner = (Button) findViewById(R.id.btscaner);
        listo = (Button) findViewById(R.id.listo);
        cancelar = (Button) findViewById(R.id.cancelar);
        btscaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    escanner();
                }catch (SQLException e){
                    Toast.makeText(MainActivity6.this,"error"+e,Toast.LENGTH_LONG).show();
                }
            }
        });
        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity6.this, MainActivity4.class);
                startActivity(intent);
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity6.this, MainActivity4.class);
                startActivity(intent);
            }
        });
    }
    public void  escanner(){
        IntentIntegrator intregador= new IntentIntegrator( MainActivity6.this);
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
                String id2=resultado.getContents().toString();
                String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_buscar_produc.php?id="+id2;
                Buscar_base(url);
                System.out.println(url);
            }
        }else {
            System.out.println("fdsfsdf");
        }
        super.onActivityResult(requestCode, resultCode, data);
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