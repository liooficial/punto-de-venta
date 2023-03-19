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

                try{
                    eliminar(view);
                }catch (Exception e){
                    Toast.makeText(usuariosin.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    buscar(view);
                }catch (Exception e){
                    Toast.makeText(usuariosin.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void insertar(View view){
        sqllite risto=new sqllite(this,null,null,1);
        SQLiteDatabase basededatos=risto.getWritableDatabase();
        String id=ID.getText().toString();
        String usu=spinner.getSelectedItem().toString();
        String contrase=contraseña.getText().toString();
        if(id.isEmpty() /*&& usu.isEmpty() */&& contrase.isEmpty()){
            Toast.makeText(usuariosin.this,"campos vacios",Toast.LENGTH_LONG).show();
        }else{
            ContentValues r1=new ContentValues();
            r1.put("id",id);
            r1.put("usuario",usu);
            r1.put("contrase",contrase);
            if(basededatos!=null){
                try{
                    basededatos.insertOrThrow("user",null,r1);
                    basededatos.close();
                    Toast.makeText(usuariosin.this,"regristo guardado",Toast.LENGTH_LONG).show();
                }catch (SQLException e){
                    Toast.makeText(usuariosin.this,"error"+e,Toast.LENGTH_LONG).show();
                }
                ID.setText("");
                contraseña.setText("");
            }else{
                Toast.makeText(usuariosin.this,"todos lo campos guardados corectamente",Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void eliminar(View view ){
        sqllite elimina=new sqllite(this,null,null,1);
        SQLiteDatabase basededatos=elimina.getWritableDatabase();
        String id=ID.getText().toString();
        String selection = "Id=?";
        String[] selectionArgs = {id};
        if(!id.isEmpty()){
            int cantidad = basededatos.delete("user", selection, selectionArgs);
            basededatos.close();
            if(cantidad>=1){
                Toast.makeText(usuariosin.this,"se elimino corectamente",Toast.LENGTH_SHORT).show();
                contraseña.setText("");
            }else{
                Toast.makeText(usuariosin.this,"no se elimino ya que no existe",Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(usuariosin.this,"escribe el id a eliminar",Toast.LENGTH_SHORT).show();
        }
    }
    public void buscar(View view ){
        sqllite busca=new sqllite(this,null,null,1);
        SQLiteDatabase basededatos=busca.getWritableDatabase();
        String id=ID.getText().toString();
        if(!id.isEmpty()){
            try {
                Cursor fila=basededatos.rawQuery("SELECT usuario, contrase FROM user WHERE id='"+id+"'",null);
                if(fila.moveToFirst()){
                    Toast.makeText(usuariosin.this,"registro encontado",Toast.LENGTH_SHORT).show();
                    String su=fila.getString(0);
                    int n=0;
                    if(su.equals("vendedor")){
                        n=0;
                    }
                    if(su.equals("administrador")){
                        n=1;
                    }
                    spinner.setSelection(n);
                    contraseña.setText(fila.getString(1));
                    basededatos.close();
                }else {
                    Toast.makeText(usuariosin.this,"registro no encontado",Toast.LENGTH_SHORT).show();
                    basededatos.close();

                }
            }catch (SQLException e){
                Toast.makeText(usuariosin.this,"error"+e,Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(usuariosin.this,"escribe el id a buscar",Toast.LENGTH_SHORT).show();
        }
    }
    public void actualizar(View view ){

        sqllite risto=new sqllite(this,null,null,1);
        SQLiteDatabase basededatos=risto.getWritableDatabase();
        String[] parametros= {ID.getText().toString()};
        String usu=spinner.getSelectedItem().toString();
        String nombre=contraseña.getText().toString();

        if(nombre.isEmpty()/* && usu.isEmpty()*/ ){
            Toast.makeText(usuariosin.this,"campos vacios",Toast.LENGTH_SHORT).show();
        }else{
            ContentValues r1=new ContentValues();
            r1.put("usuario",usu);
            r1.put("nombre",nombre);

            if(basededatos!=null){
                try{
                    basededatos.update("produc",r1,"id="+"=?",parametros);
                    basededatos.close();
                    Toast.makeText(usuariosin.this,"regristo actualizado",Toast.LENGTH_SHORT).show();
                }catch (SQLException e){
                    Toast.makeText(usuariosin.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
                contraseña.setText("");
            }else{
                Toast.makeText(usuariosin.this,"todos lo campos guardados corectamente",Toast.LENGTH_SHORT).show();
            }

        }
    }


}