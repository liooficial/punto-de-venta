package com.example.examen;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import androidx.annotation.Nullable;

        import com.example.examen.entidades.PRODUTOS;

        import java.util.ArrayList;

public class sqllite extends SQLiteOpenHelper{
    //private static final String TABLE_CONTACTOS ="produc" ;

    public sqllite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context,"produc" , factory, version);

    }



    /*public sqllite(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, TABLE_CONTACTOS , factory, version);
    }*/



    @Override
    public void onCreate(SQLiteDatabase basededatos ) {
        basededatos.execSQL("CREATE TABLE produc (id text PRIMARY KEY , nombre text, forma text, volumen text, precio text, cantidad text)");
        basededatos.execSQL("INSERT INTO  produc (id,nombre, forma,volumen,precio, cantidad) VALUES ('5901234123457','Spray nocturno', 'Aerosol','100 Mililitros','779', '2')");
        //basededatos.execSQL("CREATE TABLE venta (id text PRIMARY KEY AUTOINCREMENT,id_p text, nombre text, forma text, volumen text, precio text, cantidad text, vendedor text)");
        basededatos.execSQL("CREATE TABLE user (id text PRIMARY KEY, usuario text, contrase text)");
        basededatos.execSQL("INSERT INTO  user (id,usuario, contrase) VALUES ('lio', 'administrador', '1234')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase basededatos, int i, int i1) {


    }
    public ArrayList<PRODUTOS> mostrarContactos(){
        ArrayList<PRODUTOS> listaContactos = new ArrayList<>();
        PRODUTOS contacto;
        Cursor cursorContactos;
        SQLiteDatabase basededatos = this.getReadableDatabase();
        cursorContactos = basededatos.rawQuery("SELECT * FROM " + "produc" , null);

        if(cursorContactos.moveToFirst()){
            do{
                contacto = new PRODUTOS();
                contacto.setId(cursorContactos.getString(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setForma(cursorContactos.getString(2));
                contacto.setVolumen(cursorContactos.getString(3));
                contacto.setPrecio(cursorContactos.getString(4));
                contacto.setCantidad(cursorContactos.getString(5));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }
        cursorContactos.close();
        return listaContactos;
    }
    /*
    public ArrayList<PRODUTOS> mostrarventas(){
        ArrayList<PRODUTOS> listaContactos = new ArrayList<>();
        PRODUTOS contacto;
        Cursor cursorContactos;
        SQLiteDatabase basededatos = this.getReadableDatabase();
        cursorContactos = basededatos.rawQuery("SELECT * FROM " + "venta" , null);

        if(cursorContactos.moveToFirst()){
            do{
                contacto = new PRODUTOS();
                contacto.setId(cursorContactos.getString(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setForma(cursorContactos.getString(2));
                contacto.setVolumen(cursorContactos.getString(3));
                contacto.setPrecio(cursorContactos.getString(4));
                contacto.setCantidad(cursorContactos.getString(5));
                contacto.setCantidad(cursorContactos.getString(6));
                contacto.setCantidad(cursorContactos.getString(7));

                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }
        cursorContactos.close();
        return listaContactos;
    }*/
    public void  actualizar(String id,int NEWCAN){
        SQLiteDatabase basededatos = this.getReadableDatabase();
        String[] parametros= {id};
        ContentValues r1=new ContentValues();
        String cantidad = String.valueOf(NEWCAN);
        r1.put("cantidad",cantidad);
        basededatos.update("produc",r1,"id="+"=?",parametros);
        basededatos.close();
    }


}
