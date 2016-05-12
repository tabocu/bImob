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
import br.com.blackseed.blackimob.entity.Email;
import br.com.blackseed.blackimob.entity.Imovel;
import br.com.blackseed.blackimob.entity.Pessoa;
import br.com.blackseed.blackimob.entity.Telefone;

public class ImobDb {

    private ImobDbHelper dbHelper;

    public ImobDb(Context context) {
        dbHelper = new ImobDbHelper(context);
    }

    //CRUD - Pessoa

    public Pessoa createPessoa(Pessoa pessoa) {

        ContentValues contentValues = new ContentValues();

        if (pessoa.isPessoaFisica()) {
            Pessoa.Fisica pessoaFisica = (Pessoa.Fisica) pessoa;
            contentValues.put(PessoaEntry.COLUMN_NOME, pessoaFisica.getNome());
            contentValues.put(PessoaEntry.COLUMN_CPF, pessoaFisica.getCpf());
            contentValues.put(PessoaEntry.COLUMN_IS_PESSOA_FISICA, true);
        } else {
            Pessoa.Juridica pessoaJuridica = (Pessoa.Juridica) pessoa;
            contentValues.put(PessoaEntry.COLUMN_NOME_FANTASIA, pessoaJuridica.getNomeFantasia());
            contentValues.put(PessoaEntry.COLUMN_RAZAO_SOCIAL, pessoaJuridica.getRazaoSocial());
            contentValues.put(PessoaEntry.COLUMN_CNPJ, pessoaJuridica.getCnpj());
            contentValues.put(PessoaEntry.COLUMN_IS_PESSOA_FISICA, false);
        }

        long _id = dbHelper.getWritableDatabase().insert(PessoaEntry.TABLE_NAME, null, contentValues);

        pessoa.setId(_id);
        return pessoa;
    }

    public List<Pessoa> readAllPessoa() {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(ImobContract.PessoaEntry.TABLE_NAME);

        List<Pessoa> pessoaList = new ArrayList<>();
        Cursor pessoaCursor = qb.query(dbHelper.getReadableDatabase(),
                PessoaEntry.PESSOA_SELECT,
                null, null, null, null, null);

        while (pessoaCursor.moveToNext()) {
            Pessoa pessoa;
            if (pessoaCursor.getInt(pessoaCursor.getColumnIndex(PessoaEntry.COLUMN_IS_PESSOA_FISICA)) == 1) {
                pessoa = new Pessoa.Fisica();
                ((Pessoa.Fisica) pessoa).setNome(
                        pessoaCursor.getString(
                                pessoaCursor.getColumnIndex(
                                        PessoaEntry.COLUMN_NOME)));
                ((Pessoa.Fisica) pessoa).setCpf(
                        pessoaCursor.getString(
                                pessoaCursor.getColumnIndex(
                                        PessoaEntry.COLUMN_CPF)));

            } else {
                pessoa = new Pessoa.Juridica();
                ((Pessoa.Juridica) pessoa).setNomeFantasia(
                        pessoaCursor.getString(
                                pessoaCursor.getColumnIndex(
                                        PessoaEntry.COLUMN_NOME_FANTASIA)));
                ((Pessoa.Juridica) pessoa).setRazaoSocial(
                        pessoaCursor.getString(
                                pessoaCursor.getColumnIndex(
                                        PessoaEntry.COLUMN_RAZAO_SOCIAL)));
                ((Pessoa.Juridica) pessoa).setCnpj(
                        pessoaCursor.getString(
                                pessoaCursor.getColumnIndex(
                                        PessoaEntry.COLUMN_CNPJ)));
            }
            pessoa.setId(pessoaCursor.getLong(pessoaCursor.getColumnIndex(PessoaEntry._ID)));
            pessoaList.add(pessoa);
        }
        return pessoaList;
    }

    public boolean updatePessoa(Pessoa pessoa) {
        return false;
    }

    public int deletePessoa(Pessoa pessoa) {
        deleteTelefone(TelefoneEntry.COLUMN_PESSOA_ID, pessoa.getId());
        deleteEmail(EmailEntry.COLUMN_PESSOA_ID, pessoa.getId());
        return dbHelper.getWritableDatabase()
                .delete(PessoaEntry.TABLE_NAME, PessoaEntry._ID + " = " + pessoa.getId(), null);
    }

    public int deletePessoa(List<Pessoa> pessoas) {
        int result = 0;
        for (Pessoa pessoa : pessoas) {
            result += deletePessoa(pessoa);
        }
        return result;
    }

    //CRUD - Imovel

    public Imovel createImovel(Imovel imovel) {
        return null;
    }

    public List<Pessoa> readAllImovel() {
        return null;
    }

    public boolean updateImovel(Imovel imovel) {
        return false;
    }

    public boolean deleteImovel(Imovel imovel) {
        return false;
    }

    //CRUD - Telefone

    public Telefone createTelefone(String column, Long foreignKey, Telefone telefone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TelefoneEntry.COLUMN_TELEFONE, telefone.getNumero());
        contentValues.put(column, foreignKey);

        long _id = dbHelper.getWritableDatabase()
                .insert(TelefoneEntry.TABLE_NAME, null, contentValues);

        telefone.setId(_id);

        return telefone;
    }

    public List<Telefone> readTelefone(String foreignColumn, long foreignKey) {
        String[] sqlSelect = {
                TelefoneEntry._ID,
                TelefoneEntry.COLUMN_TELEFONE
        };

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TelefoneEntry.TABLE_NAME);
        qb.appendWhere(foreignColumn + " = " + foreignKey);
        Cursor telefoneCursor = qb.query(dbHelper.getReadableDatabase(),
                sqlSelect,
                null, null, null, null, null);

        int _id = telefoneCursor.getColumnIndex(TelefoneEntry._ID);
        int number = telefoneCursor.getColumnIndex(TelefoneEntry.COLUMN_TELEFONE);

        List<Telefone> telefones = new ArrayList<>();
        Telefone telefone;
        while (telefoneCursor.moveToNext()) {
            telefone = new Telefone();
            telefone.setId(telefoneCursor.getLong(_id));
            telefone.setNumero(telefoneCursor.getString(number));
            telefones.add(telefone);
        }
        return telefones;
    }

    public int deleteTelefone(Telefone telefone) {
        return dbHelper.getWritableDatabase()
                .delete(TelefoneEntry.TABLE_NAME, TelefoneEntry._ID + " = " + telefone.getId(), null);
    }

    public int deleteTelefone(String foreignColumn, long foreignKey) {
        return dbHelper.getWritableDatabase()
                .delete(TelefoneEntry.TABLE_NAME, foreignColumn + " = " + foreignKey, null);
    }

    //CRUD - Email

    public Email createEmail(String foreignColumn, Long foreignKey, Email email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EmailEntry.COLUMN_EMAIL, email.getEndereco());
        contentValues.put(foreignColumn, foreignKey);

        long _id = dbHelper.getWritableDatabase()
                .insert(EmailEntry.TABLE_NAME, null, contentValues);

        email.setId(_id);

        return email;
    }

    public List<Email> readEmail(String foreignColumn, long foreignKey) {
        String[] sqlSelect = {
                EmailEntry._ID,
                EmailEntry.COLUMN_EMAIL
        };

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(ImobContract.EmailEntry.TABLE_NAME);
        qb.appendWhere(foreignColumn + " = " + foreignKey);
        Cursor emailCursor = qb.query(dbHelper.getReadableDatabase(),
                sqlSelect,
                null, null, null, null, null);

        int _id = emailCursor.getColumnIndex(ImobContract.EmailEntry._ID);
        int endereco = emailCursor.getColumnIndex(ImobContract.EmailEntry.COLUMN_EMAIL);

        List<Email> emails = new ArrayList<>();
        Email email;
        while (emailCursor.moveToNext()) {
            email = new Email();
            email.setId(emailCursor.getLong(_id));
            email.setEndereco(emailCursor.getString(endereco));
            emails.add(email);
        }
        return emails;
    }

    public int deleteEmail(Email email) {
        return dbHelper.getWritableDatabase()
                .delete(EmailEntry.TABLE_NAME, EmailEntry._ID + " = " + email.getId(), null);
    }

    public int deleteEmail(String foreignColumn, long foreignKey) {
        return dbHelper.getWritableDatabase()
                .delete(EmailEntry.TABLE_NAME, foreignColumn + " = " + foreignKey, null);
    }
}
