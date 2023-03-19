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


public class login extends AppCompatActivity {
    Button b1;
    EditText contraseña, usuario;
    TextView mensajes;
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
                Object[] miArreglo =buscar(v);
                String usuario2 = (String) miArreglo[0];
                String tipo = (String) miArreglo[1];
                String contraseña2 = (String) miArreglo[2];
                String usuario1=usuario.getText().toString(),contraseña1=contraseña.getText().toString();
                if (usuario1.isEmpty()){

                }else{
                    if (usuario1.equals(usuario2)){
                        if(contraseña1.equals(contraseña2)){
                            mensajes.setText("");
                            Intent i =new Intent(login.this, MainActivity4.class);
                            i.putExtra("tipo", tipo);
                            i.putExtra("usuario", usuario2);
                            startActivity(i);
                        }else{
                            mensajes.setText("contraseña equivocada"+tipo);
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
        String tipo="";
        String contraseña2="";
        if(!usuario2.isEmpty()){
            try {
                Cursor fila=basededatos.rawQuery("SELECT usuario,contrase FROM user WHERE id='"+usuario2+"'",null);
                if(fila.moveToFirst()){
                    tipo=fila.getString(0);
                    contraseña2=fila.getString(1);
                    basededatos.close();
                }else {
                    usuario2="";
                    basededatos.close();
                }
            }catch (SQLException e){
                Toast.makeText(login.this,"error"+e,Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(login.this,"escribe el id a buscar",Toast.LENGTH_SHORT).show();
        }
        Object[] resultado = { usuario2,tipo, contraseña2 };
        return resultado;
    }

}