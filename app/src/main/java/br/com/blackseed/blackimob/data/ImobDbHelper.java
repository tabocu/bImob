package br.com.blackseed.blackimob.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.blackseed.blackimob.Inquilino;
import br.com.blackseed.blackimob.data.ImobContract.*;

public class ImobDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "imob.db";



    public ImobDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_INQUILINO_TABLE = "CREATE TABLE " + InquilinoEntry.TABLE_NAME + " (" +
                InquilinoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InquilinoEntry.COLUMN_NOME + " TEXT NOT NULL, " +
                InquilinoEntry.COLUMN_TIPO_PESSOA + " BOOLEAN, " +
                InquilinoEntry.COLUMN_CADASTRO_PESSOA + " TEXT" + ");";

        final String SQL_CREATE_IMOVEL_TABLE = "CREATE TABLE " + ImovelEntry.TABLE_NAME + " (" +
                ImovelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ImovelEntry.COLUMN_APELIDO + " TEXT NOT NULL, " +
                ImovelEntry.COLUMN_TIPO_IMOVEL + " TEXT" + "); ";

        final String SQL_CREATE_TELEFONE_TABLE = "CREATE TABLE " + TelefoneEntry.TABLE_NAME + " (" +
                TelefoneEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TelefoneEntry.COLUMN_TELEFONE + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + TelefoneEntry.COLUMN_INQUILINO_ID + ") REFERENCES " +
                TelefoneEntry.TABLE_NAME + " (" + InquilinoEntry._ID + ");";

        final String SQL_CREATE_EMAIL_TABLE = "CREATE TABLE " + EmailEntry.TABLE_NAME + " (" +
                EmailEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EmailEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + TelefoneEntry.COLUMN_INQUILINO_ID + ") REFERENCES " +
                TelefoneEntry.TABLE_NAME + " (" + InquilinoEntry._ID + ");";

        db.execSQL(SQL_CREATE_INQUILINO_TABLE);
        db.execSQL(SQL_CREATE_IMOVEL_TABLE);
        db.execSQL(SQL_CREATE_TELEFONE_TABLE);
        db.execSQL(SQL_CREATE_EMAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + InquilinoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ImovelEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TelefoneEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EmailEntry.TABLE_NAME);
        onCreate(db);
    }
}
