package br.com.blackseed.blackimob.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import br.com.blackseed.blackimob.data.ImobContract.EmailEntry;
import br.com.blackseed.blackimob.data.ImobContract.PessoaEntry;
import br.com.blackseed.blackimob.data.ImobContract.TelefoneEntry;


public class ImobDb {

    private ImobDbHelper dbHelper;

    public ImobDb(Context context) {
        dbHelper = new ImobDbHelper(context);
    }
    
    public Cursor fetchAllPessoa() {
        String ordenacao =
                PessoaEntry.COLUMN_IS_FAVORITO + " DESC, " +
                        PessoaEntry.COLUMN_NOME + " ASC";

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PessoaEntry.TABLE_NAME);

        return qb.query(dbHelper.getReadableDatabase(),
                PessoaEntry.PESSOA_SELECT,
                null, null, null, null, ordenacao);
    }

    public Cursor fetchPessoa(Long id) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PessoaEntry.TABLE_NAME);
        qb.appendWhere(PessoaEntry._ID + " = " + id);

        Cursor cursor = qb.query(dbHelper.getReadableDatabase(),
                PessoaEntry.PESSOA_SELECT,
                null, null, null, null, null);

        if (!cursor.moveToNext()) return null;
        return cursor;
    }

    public Cursor fetchTelefoneOfPessoa(Long id) {
        String[] sqlSelect = {
                TelefoneEntry._ID,
                TelefoneEntry.COLUMN_TELEFONE
        };

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TelefoneEntry.TABLE_NAME);
        qb.appendWhere(TelefoneEntry.COLUMN_PESSOA_ID + " = " + id);

        Cursor cursor = qb.query(dbHelper.getReadableDatabase(),
                sqlSelect,
                null, null, null, null, null);

        return cursor;
    }

    public Cursor fetchEmailOfPessoa(Long id) {
        String[] sqlSelect = {
                EmailEntry._ID,
                EmailEntry.COLUMN_EMAIL
        };

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(EmailEntry.TABLE_NAME);
        qb.appendWhere(EmailEntry.COLUMN_PESSOA_ID + " = " + id);

        Cursor cursor = qb.query(dbHelper.getReadableDatabase(),
                sqlSelect,
                null, null, null, null, null);

        return cursor;
    }

    public int deletePessoa(long id) {
        deleteTelefoneOfPessoa(id);
        deleteEmailOfPessoa(id);
        return dbHelper.getWritableDatabase()
                .delete(PessoaEntry.TABLE_NAME, PessoaEntry._ID + " = " + id, null);
    }

    public void updatePessoa(long id, ContentValues contentValues) {
        String where = PessoaEntry._ID + " = " + id;
        dbHelper.getWritableDatabase().update(PessoaEntry.TABLE_NAME, contentValues, where, null);
    }

    public long createPessoa(ContentValues contentValues) {
        return dbHelper.getWritableDatabase().insert(PessoaEntry.TABLE_NAME, null, contentValues);
    }

    public long createTelefone(ContentValues contentValues) {
        return dbHelper.getWritableDatabase()
                .insert(TelefoneEntry.TABLE_NAME, null, contentValues);
    }

    public long createEmail(ContentValues contentValues) {
        return dbHelper.getWritableDatabase()
                .insert(EmailEntry.TABLE_NAME, null, contentValues);
    }

    public int deleteTelefoneOfPessoa(long pessoa_id) {
        return dbHelper.getWritableDatabase()
                .delete(TelefoneEntry.TABLE_NAME, TelefoneEntry.COLUMN_PESSOA_ID + " = " + pessoa_id, null);
    }

    public int deleteEmailOfPessoa(long pessoa_id) {
        return dbHelper.getWritableDatabase()
                .delete(EmailEntry.TABLE_NAME, EmailEntry.COLUMN_PESSOA_ID + " = " + pessoa_id, null);
    }

    public static List<String> cursorToStringList(Cursor cursor, String column) {
        List<String> stringList = new ArrayList<>();
        int columnIndex = cursor.getColumnIndexOrThrow(column);
        while (cursor.moveToNext())
            stringList.add(cursor.getString(columnIndex));
        return stringList;
    }

}
