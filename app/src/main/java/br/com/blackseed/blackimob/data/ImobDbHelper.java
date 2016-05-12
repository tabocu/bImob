package br.com.blackseed.blackimob.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.blackseed.blackimob.data.ImobContract.EmailEntry;
import br.com.blackseed.blackimob.data.ImobContract.ImovelEntry;
import br.com.blackseed.blackimob.data.ImobContract.PessoaEntry;
import br.com.blackseed.blackimob.data.ImobContract.TelefoneEntry;

public class ImobDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "imob.db";
    private static final int DATABASE_VERSION = 2;

    public ImobDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_PESSOA_TABLE = "CREATE TABLE " + PessoaEntry.TABLE_NAME + " (" +
                PessoaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PessoaEntry.COLUMN_NOME             + " VARCHAR(80), " +
                PessoaEntry.COLUMN_NOME_FANTASIA    + " VARCHAR(80), " +
                PessoaEntry.COLUMN_RAZAO_SOCIAL     + " VARCHAR(80), " +
                PessoaEntry.COLUMN_CPF + " VARCHAR(11), " +
                PessoaEntry.COLUMN_CNPJ + " VARCHAR(14), " +
                PessoaEntry.COLUMN_IS_PESSOA_FISICA + " BOOLEAN " + ");";

        final String SQL_CREATE_TELEFONE_TABLE = "CREATE TABLE " + TelefoneEntry.TABLE_NAME + " (" +
                TelefoneEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TelefoneEntry.COLUMN_TELEFONE + " TEXT NOT NULL, " +
                TelefoneEntry.COLUMN_PESSOA_ID + " INTEGER NOT NULL, " +

                " FOREIGN KEY (" + TelefoneEntry.COLUMN_PESSOA_ID + ") REFERENCES " +
                TelefoneEntry.TABLE_NAME + " (" + PessoaEntry._ID + "));";

        final String SQL_CREATE_EMAIL_TABLE = "CREATE TABLE " + EmailEntry.TABLE_NAME + " (" +
                EmailEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EmailEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                EmailEntry.COLUMN_PESSOA_ID + " INTEGER NOT NULL, " +

                " FOREIGN KEY (" + TelefoneEntry.COLUMN_PESSOA_ID + ") REFERENCES " +
                TelefoneEntry.TABLE_NAME + " (" + PessoaEntry._ID + ")" +
                ");";

        final String SQL_CREATE_IMOVEL_TABLE = "CREATE TABLE " + ImovelEntry.TABLE_NAME + " (" +
                ImovelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ImovelEntry.COLUMN_APELIDO + " TEXT NOT NULL, " +
                ImovelEntry.COLUMN_TIPO_IMOVEL + " TEXT" + "); ";

        db.execSQL(SQL_CREATE_PESSOA_TABLE);
        db.execSQL(SQL_CREATE_IMOVEL_TABLE);
        db.execSQL(SQL_CREATE_TELEFONE_TABLE);
        db.execSQL(SQL_CREATE_EMAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PessoaEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ImovelEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TelefoneEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EmailEntry.TABLE_NAME);
        onCreate(db);
    }
}
