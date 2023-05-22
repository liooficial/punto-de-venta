package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class login extends AppCompatActivity {
    Button b1;
    EditText contraseña, usuario;
    TextView mensajes;
    private String tipo;
    private String contraseñat;
    private String usuario2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        b1 = (Button) findViewById(R.id.inicioo);
        contraseña = (EditText) findViewById(R.id.contraseña);
        usuario = (EditText) findViewById(R.id.usuario);
        mensajes = (TextView) findViewById(R.id.mensajes);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarusuario();
            }
        });
    }
    public void validarusuario(){
        String usuario2=usuario.getText().toString();
        if(!usuario2.isEmpty()){
            String url = "https://web6regrfg.000webhostapp.com/base_de_datos_cel/examen_buscar_user.php?id="+usuario2;
            Buscar(url);
        }else{
            Toast.makeText(login.this,"escribe el usuari a ",Toast.LENGTH_SHORT).show();
        }
    }

    public void Buscar(String url) {
        String contraseña1= String.valueOf(contraseña.getText());
        JsonArrayRequest stringaray=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            usuario2 = jsonObject.getString("id");
                            tipo = jsonObject.getString("usuario");
                            contraseñat = jsonObject.getString("contrase");
                                if(contraseña1.equals(contraseñat)){
                                    mensajes.setText("");
                                    Intent inten =new Intent(login.this, MainActivity4.class);
                                    inten.putExtra("tipo", tipo);
                                    inten.putExtra("usuario", usuario2);
                                    startActivity(inten);
                                }else{
                                    mensajes.setText("contraseña equivocada");
                                }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "json"+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "usuario no encontrado en la base de datos"+error, Toast.LENGTH_LONG).show();
                System.out.println(error.getMessage());
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringaray);
    }

}
