package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText usuario,contraseña;
    Button modificar,agregar,eliminar,b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        usuario = (EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById(R.id.contraseña);
        modificar = (Button) findViewById(R.id.bt1);
        agregar = (Button) findViewById(R.id.bt2);
        eliminar = (Button) findViewById(R.id.bt3);
        b1 = (Button) findViewById(R.id.bt10);
        b2 = (Button) findViewById(R.id.bt20);
        b3 = (Button) findViewById(R.id.bt30);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                startActivity(intent);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar(view);
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar(view);
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(view);
            }
        });
    }
    public void insertar(View view){
        sqllite risto=new sqllite(this,null,null,1);
        SQLiteDatabase basededatos=risto.getWritableDatabase();
        String id=usuario.getText().toString();
        String nombre=contraseña.getText().toString();
        if(id.isEmpty() && nombre.isEmpty()){
            Toast.makeText(MainActivity2.this,"campos vacios",Toast.LENGTH_SHORT).show();
        }else{
            ContentValues r1=new ContentValues();
            r1.put("id",id);
            r1.put("nombre",nombre);
            if(basededatos!=null){
                try{
                    basededatos.insertOrThrow("produc",null,r1);
                    basededatos.close();
                    Toast.makeText(MainActivity2.this,"regristo guardado",Toast.LENGTH_SHORT).show();
                }catch (SQLException e){
                    Toast.makeText(MainActivity2.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
                usuario.setText("");
                contraseña.setText("");
            }else{
                Toast.makeText(MainActivity2.this,"todos lo campos guardados corectamente",Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void eliminar(View view ){
        sqllite elimina=new sqllite(this,null,null,1);
        SQLiteDatabase basededatos=elimina.getWritableDatabase();
        String id=usuario.getText().toString()    ;
        if(!id.isEmpty()){
            int cantidad=basededatos.delete("produc","id="+id,null);
            basededatos.close();
            if(cantidad>=1){
                Toast.makeText(MainActivity2.this,"se elimino corectamente",Toast.LENGTH_SHORT).show();
                usuario.setText("");
                contraseña.setText("");
            }else{
                Toast.makeText(MainActivity2.this,"no se elimino",Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(MainActivity2.this,"escribe el id a eliminar",Toast.LENGTH_SHORT).show();
        }
    }
    public void actualizar(View view ){

        sqllite risto=new sqllite(this,null,null,1);
        SQLiteDatabase basededatos=risto.getWritableDatabase();
        String[] parametros= {usuario.getText().toString()};
        String nombre=contraseña.getText().toString();

        if(nombre.isEmpty() ){
            Toast.makeText(MainActivity2.this,"campos vacios",Toast.LENGTH_SHORT).show();
        }else{
            ContentValues r1=new ContentValues();
            r1.put("nombre",nombre);

            if(basededatos!=null){
                try{
                    basededatos.update("produc",r1,"id="+"=?",parametros);
                    basededatos.close();
                    Toast.makeText(MainActivity2.this,"regristo actualizado",Toast.LENGTH_SHORT).show();
                }catch (SQLException e){
                    Toast.makeText(MainActivity2.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
                usuario.setText("");
                contraseña.setText("");
            }else{
                Toast.makeText(MainActivity2.this,"todos lo campos guardados corectamente",Toast.LENGTH_SHORT).show();
            }

        }
    }

}