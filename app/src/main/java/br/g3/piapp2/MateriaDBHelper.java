package br.g3.piapp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MateriaDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "materias.db";
    private static final int DB_VERSION = 1;
    MateriaDBHelper (Context context){
        super (context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HelpDeskContract.createTableMateria());
        db.execSQL(HelpDeskContract.insertMateria());
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL(HelpDeskContract.MateriaContract.DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int a, int b) {
    }
}
