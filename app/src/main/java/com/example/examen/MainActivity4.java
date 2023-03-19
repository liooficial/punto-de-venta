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

import com.example.examen.adaptadores.CustomAdapter;
import com.example.examen.entidades.PRODUTOS;
import com.example.examen.entidades.Item;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
    Button  usuarios, producto, venta,inventario,salir,btn_menu,agregar,cancelar,compar;
    DrawerLayout mDrawerLayout;
    RecyclerView listaproductos;
    TextView total,canbio;
    EditText IMPORTE;
    CustomAdapter adapter = new CustomAdapter();
    String [] datos = {};
    //String usuariov;
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

        listaproductos=(RecyclerView)findViewById(R.id.listaproductos);
        listaproductos.setAdapter(adapter);
        listaproductos.setLayoutManager(new LinearLayoutManager(this));



        Intent intent = getIntent();
        String tipo = intent.getStringExtra("tipo");
        String usuario = intent.getStringExtra("usuario");
        //usuariov=intent.getStringExtra("usuario");

        if(tipo.equals("vendedor")){
            usuarios.setEnabled(false);
            producto.setEnabled(false);
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
                    adapter.setPurchaseCompleted(false);
                    adapter.clearItems();
                    total.setText(0);
                    IMPORTE.setText(0);
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
                sqllite busca=new sqllite(this,null,null,1);
                SQLiteDatabase basededatos=busca.getWritableDatabase();
                String id2=resultado.getContents().toString();
                try {
                    Cursor fila=basededatos.rawQuery("SELECT * FROM produc WHERE id='"+id2+"'",null);
                    if(fila.moveToFirst()){
                        int numeroComoEntero = Integer.parseInt(fila.getString(5));
                        numeroComoEntero=numeroComoEntero-1;
                        if(numeroComoEntero<0){
                            Toast.makeText(MainActivity4.this,"Ya no hay producto",Toast.LENGTH_LONG).show();
                        }else{
                            busca.actualizar(id2,numeroComoEntero);
                            //basededatos.execSQL("INSERT INTO venta (id_p, nombre, forma, volumen, precio, cantidad, vendedor) VALUES (?, ?, ?, ?, ?, ?, ?)", new Object[] { fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getString(5), usuariov });
                            adapter.addItem(new Item(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getString(5)));
                            double precioTotal = adapter.getPrecioTotal();
                            total.setText(String.valueOf(precioTotal));
                            basededatos.close();
                        }
                    }else {
                        Toast.makeText(MainActivity4.this,"registro no encontado",Toast.LENGTH_SHORT).show();
                        basededatos.close();
                    }
                }catch (SQLException e){
                    Toast.makeText(MainActivity4.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}


