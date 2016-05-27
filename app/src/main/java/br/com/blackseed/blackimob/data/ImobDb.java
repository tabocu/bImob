package br.com.blackseed.blackimob.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.blackseed.blackimob.MainActivity;
import br.com.blackseed.blackimob.data.ImobContract.EmailEntry;
import br.com.blackseed.blackimob.data.ImobContract.PessoaEntry;
import br.com.blackseed.blackimob.data.ImobContract.ImovelEntry;
import br.com.blackseed.blackimob.data.ImobContract.TelefoneEntry;
import br.com.blackseed.blackimob.data.ImobContract.EnderecoEntry;
import br.com.blackseed.blackimob.detail.DetailPessoaActivity;


public class ImobDb {

    private ImobDbHelper dbHelper;
    private Context context;

    public ImobDb(Context context) {
        dbHelper = new ImobDbHelper(context);
        this.context = context;
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

    public Cursor fetchEndereco(Long enderecoId) {
      String[] sqlSelect = {
              EnderecoEntry._ID,
              EnderecoEntry.COLUMN_PLACE_ID,
              EnderecoEntry.COLUMN_LOCAL,
              EnderecoEntry.COLUMN_COMPLEMENTO,
              EnderecoEntry.COLUMN_LATITUDE,
              EnderecoEntry.COLUMN_LONGITUDE
      };

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(EnderecoEntry.TABLE_NAME);
        qb.appendWhere(EnderecoEntry._ID + " = " + enderecoId);

        Cursor cursor = qb.query(dbHelper.getReadableDatabase(),
                sqlSelect,
                null, null, null, null, null);

        if (!cursor.moveToNext()) return null;
        return cursor;
    };

//    public Cursor fetchEnderecoOfPessoa(Long pessoaId) {
//
//        String[] sqlPessoaSelect = {PessoaEntry.COLUMN_ENDERECO_ID};
//
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//        qb.setTables(PessoaEntry.TABLE_NAME);
//        qb.appendWhere(PessoaEntry._ID + " = " + pessoaId);
//
//        Cursor cursor = qb.query(dbHelper.getReadableDatabase(),
//                sqlPessoaSelect,
//                null, null, null, null, null);
//
//        return fetchEndereco(cursor.getLong(0));
//    };

//    public Cursor fetchEnderecoOfImovel(Long imovelId) {
//
//        String[] sqlPessoaSelect = {PessoaEntry.COLUMN_ENDERECO_ID};
//
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//        qb.setTables(ImovelEntry.TABLE_NAME);
//        qb.appendWhere(ImovelEntry._ID + " = " + imovelId);
//
//        Cursor cursor = qb.query(dbHelper.getReadableDatabase(),
//                sqlPessoaSelect,
//                null, null, null, null, null);
//
//        return fetchEndereco(cursor.getLong(0));
//    };

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

    public long createEndereco(ContentValues contentValues) {
        return dbHelper.getWritableDatabase()
                .insert(EnderecoEntry.TABLE_NAME, null, contentValues);
    }

    public int deleteTelefoneOfPessoa(long pessoa_id) {
        return dbHelper.getWritableDatabase()
                .delete(TelefoneEntry.TABLE_NAME, TelefoneEntry.COLUMN_PESSOA_ID + " = " + pessoa_id, null);
    }

    public int deleteEmailOfPessoa(long pessoa_id) {
        return dbHelper.getWritableDatabase()
                .delete(EmailEntry.TABLE_NAME, EmailEntry.COLUMN_PESSOA_ID + " = " + pessoa_id, null);
    }

    public int deleteEndereco(long endereco_id) {
        return dbHelper.getWritableDatabase()
                .delete(EnderecoEntry.TABLE_NAME, EnderecoEntry._ID + " = " + endereco_id, null);
    }

//    public int deleteEnderecoOfImovel(long imovel_id) {
//
//        Cursor cursor = fetchEnderecoOfImovel(imovel_id);
//        return dbHelper.getWritableDatabase()
//                .delete(EnderecoEntry.TABLE_NAME, EnderecoEntry._ID + " = "
//                        + cursor.getLong(cursor.getColumnIndex(EnderecoEntry._ID)), null);
//    }

    public static List<String> cursorToStringList(Cursor cursor, String column) {
        List<String> stringList = new ArrayList<>();
        int columnIndex = cursor.getColumnIndexOrThrow(column);
        while (cursor.moveToNext())
            stringList.add(cursor.getString(columnIndex));
        return stringList;
    }

}
