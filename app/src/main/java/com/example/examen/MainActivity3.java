package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity3 extends AppCompatActivity {
Button b1,b2,b3,b4,b5;
EditText e2,e3,e4,e5;
TextView e1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        b1 = (Button) findViewById(R.id.btscaner);
        b2 = (Button) findViewById(R.id.bt1);
        b3 = (Button) findViewById(R.id.bt2);
        b4 = (Button) findViewById(R.id.bt3);
        b5 = (Button) findViewById(R.id.bt4);
        e1 = (TextView) findViewById(R.id.id2);
        e2 = (EditText) findViewById(R.id.n2);
        e3 = (EditText) findViewById(R.id.d2);
        e4 = (EditText) findViewById(R.id.c2);
        e5 = (EditText) findViewById(R.id.precio_v2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escanner();
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
        IntentIntegrator intregador= new IntentIntegrator( MainActivity3.this);
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
                e1.setText(resultado.getContents());
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
        public void insertar(View view){
            sqllite risto=new sqllite(this,null,null,1);
            SQLiteDatabase basededatos=risto.getWritableDatabase();
            String id=e1.getText().toString();
            String nombre=e2.getText().toString();
            String descripcion=e3.getText().toString();
            String cantidad=e4.getText().toString();
            String precioc=e5.getText().toString();

            if(id.isEmpty() && nombre.isEmpty() && descripcion.isEmpty()&& cantidad.isEmpty()&& precioc.isEmpty()){
                Toast.makeText(MainActivity3.this,"campos vacios",Toast.LENGTH_SHORT).show();
            }else{
                ContentValues r1=new ContentValues();
                r1.put("id",id);
                r1.put("nombre",nombre);
                r1.put("descripcion",descripcion);
                r1.put("cantidad",cantidad);
                r1.put("precio",precioc);
                if(basededatos!=null){
                    try{
                        basededatos.insertOrThrow("produc",null,r1);
                        basededatos.close();
                        Toast.makeText(MainActivity3.this,"regristo guardado",Toast.LENGTH_SHORT).show();
                    }catch (SQLException e){
                        Toast.makeText(MainActivity3.this,"error"+e,Toast.LENGTH_SHORT).show();
                    }
                    e1.setText("");
                    e2.setText("");
                    e3.setText("");
                    e4.setText("");
                    e5.setText("");
                }else{
                    Toast.makeText(MainActivity3.this,"todos lo campos guardados corectamente",Toast.LENGTH_SHORT).show();

                }
            }
        }
        public void buscar(View view ){
            sqllite busca=new sqllite(this,null,null,1);
            SQLiteDatabase basededatos=busca.getWritableDatabase();
            String id=e1.getText().toString();
            if(!id.isEmpty()){
                try {
                    Cursor fila=basededatos.rawQuery("SELECT nombre,descripcion,cantidad,precio FROM produc WHERE id='"+id+"'",null);
                    if(fila.moveToFirst()){
                        Toast.makeText(MainActivity3.this,"registro encontado",Toast.LENGTH_SHORT).show();
                        e2.setText(fila.getString(0));
                        e3.setText(fila.getString(1));
                        e4.setText(fila.getString(2));
                        e5.setText(fila.getString(3));
                        basededatos.close();
                    }else {
                        Toast.makeText(MainActivity3.this,"registro no encontado",Toast.LENGTH_SHORT).show();
                        basededatos.close();

                    }
                }catch (SQLException e){
                    Toast.makeText(MainActivity3.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(MainActivity3.this,"escribe el id a buscar",Toast.LENGTH_SHORT).show();
            }
        }
        public void eliminar(View view ){
            sqllite elimina=new sqllite(this,null,null,1);
            SQLiteDatabase basededatos=elimina.getWritableDatabase();
            String id=e1.getText().toString()    ;
            if(!id.isEmpty()){
                int cantidad=basededatos.delete("produc","id="+id,null);
                basededatos.close();
                if(cantidad>=1){
                    Toast.makeText(MainActivity3.this,"se elimino corectamente",Toast.LENGTH_SHORT).show();
                    e1.setText("");
                    e2.setText("");
                    e3.setText("");
                    e4.setText("");
                    e5.setText("");
                }else{
                    Toast.makeText(MainActivity3.this,"no se elimino",Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(MainActivity3.this,"escribe el id a eliminar",Toast.LENGTH_SHORT).show();
            }
        }
        public void actualizar(View view ){

            sqllite risto=new sqllite(this,null,null,1);
            SQLiteDatabase basededatos=risto.getWritableDatabase();
            String[] parametros= {e1.getText().toString()};
            String nombre=e2.getText().toString();
            String descripcion=e3.getText().toString();
            String cantidad=e4.getText().toString();
            String precioc=e5.getText().toString();

            if(nombre.isEmpty() && descripcion.isEmpty()&& cantidad.isEmpty()&& precioc.isEmpty()){
                Toast.makeText(MainActivity3.this,"campos vacios",Toast.LENGTH_SHORT).show();
            }else{
                ContentValues r1=new ContentValues();
                r1.put("nombre",nombre);
                r1.put("descripcion",descripcion);
                r1.put("cantidad",cantidad);
                r1.put("precio",precioc);

                if(basededatos!=null){
                    try{
                        basededatos.update("produc",r1,"id="+"=?",parametros);
                        basededatos.close();
                        Toast.makeText(MainActivity3.this,"regristo actualizado",Toast.LENGTH_SHORT).show();
                    }catch (SQLException e){
                        Toast.makeText(MainActivity3.this,"error"+e,Toast.LENGTH_SHORT).show();
                    }
                    e1.setText("");
                    e2.setText("");
                    e3.setText("");
                    e4.setText("");
                    e5.setText("");
                }else{
                    Toast.makeText(MainActivity3.this,"todos lo campos guardados corectamente",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }