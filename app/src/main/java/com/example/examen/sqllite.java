package com.example.examen;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import androidx.annotation.Nullable;

public class sqllite extends SQLiteOpenHelper{
    public sqllite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "produc", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase basededatos ) {
        basededatos.execSQL("CREATE TABLE produc (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, cantidad TEXT, precio TEXT)");
        basededatos.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, contrase TEXT)");
        basededatos.execSQL("INSERT INTO  user (id, contrase) VALUES ('lio', '1234')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase basededatos, int i, int i1) {

    }
}
