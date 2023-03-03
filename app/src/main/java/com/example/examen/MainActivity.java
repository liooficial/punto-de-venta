package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.zxing.integration.android.IntentIntegrator;

public class MainActivity extends AppCompatActivity {
    Button b1;
    EditText contraseña, usuario;
    TextView mensajes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.inicioo);
        contraseña = (EditText) findViewById(R.id.contraseña);
        usuario = (EditText) findViewById(R.id.usuario);
        mensajes = (TextView) findViewById(R.id.mensajes);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object[] miArreglo =buscar(v);
                String usuario2 = (String) miArreglo[0];
                String contraseña2 = (String) miArreglo[1];
                String usuario1=usuario.getText().toString(),contraseña1=contraseña.getText().toString();
                if (usuario1.isEmpty()){

                }else{
                if (usuario1.equals(usuario2)){
                    if(contraseña1.equals(contraseña2)){
                        Intent i =new Intent(MainActivity.this,MainActivity2.class);
                        startActivity(i);
                    }else{
                        mensajes.setText("contraseña equivocada"+contraseña2);
                    }
                }else{
                    mensajes.setText("usuario no existe");
                }
                }
            }
        });
    }
    public Object[]  buscar(View view){
        sqllite busca=new sqllite(this,null,null,1);
        SQLiteDatabase basededatos=busca.getWritableDatabase();
        String usuario2=usuario.getText().toString();
        String contraseña2="";
        if(!usuario2.isEmpty()){
            try {
                Cursor fila=basededatos.rawQuery("SELECT contrase FROM user WHERE id='"+usuario2+"'",null);
                if(fila.moveToFirst()){
                    contraseña2=fila.getString(0);
                    basededatos.close();
                }else {
                    usuario2="";
                    basededatos.close();
                }
            }catch (SQLException e){
                Toast.makeText(MainActivity.this,"error"+e,Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this,"escribe el id a buscar",Toast.LENGTH_SHORT).show();
        }
        Object[] resultado = { usuario2, contraseña2 };
        return resultado;
    }

}