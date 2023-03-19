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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
                sqllite busca=new sqllite(this,null,null,1);
                SQLiteDatabase basededatos=busca.getWritableDatabase();
                String id2=resultado.getContents().toString();
                try {
                    Cursor fila=basededatos.rawQuery("SELECT nombre,forma,volumen,precio,cantidad FROM produc WHERE id='"+id2+"'",null);
                    if(fila.moveToFirst()){
                        Toast.makeText(MainActivity6.this,"registro encontado",Toast.LENGTH_SHORT).show();
                        id.setText(id2);
                        nombre.setText(fila.getString(0));
                        forma.setText(fila.getString(1));
                        volumen.setText(fila.getString(2));
                        precio.setText(fila.getString(3));
                        cantidad.setText(fila.getString(4));
                        basededatos.close();
                    }else {
                        Toast.makeText(MainActivity6.this,"registro no encontado",Toast.LENGTH_SHORT).show();
                        basededatos.close();

                    }
                }catch (SQLException e){
                    Toast.makeText(MainActivity6.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}