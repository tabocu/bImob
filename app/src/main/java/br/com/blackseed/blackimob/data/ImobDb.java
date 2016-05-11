package br.com.blackseed.blackimob.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.blackseed.blackimob.entity.Email;
import br.com.blackseed.blackimob.entity.Imovel;
import br.com.blackseed.blackimob.entity.Pessoa;
import br.com.blackseed.blackimob.entity.Telefone;

public class ImobDb {

    private ImobDbHelper dbHelper;

    public ImobDb(Context context) {
        dbHelper = new ImobDbHelper(context);
    }

    public Pessoa createPessoa(Pessoa pessoa) {

        ContentValues contentValues = new ContentValues();

        if (pessoa.isPessoaFisica()) {
            Pessoa.Fisica pessoaFisica = (Pessoa.Fisica) pessoa;
            contentValues.put(ImobContract.PessoaEntry.COLUMN_NOME, pessoaFisica.getNome());
            contentValues.put(ImobContract.PessoaEntry.COLUMN_CPF, pessoaFisica.getCpf());
            contentValues.put(ImobContract.PessoaEntry.COLUMN_TIPO_PESSOA, true);
        } else {
            Pessoa.Juridica pessoaJuridica = (Pessoa.Juridica) pessoa;
            contentValues.put(ImobContract.PessoaEntry.COLUMN_NOME_FANTASIA, pessoaJuridica.getNomeFantasia());
            contentValues.put(ImobContract.PessoaEntry.COLUMN_RAZAO_SOCIAL, pessoaJuridica.getRazaoSocial());
            contentValues.put(ImobContract.PessoaEntry.COLUMN_CNPJ, pessoaJuridica.getCnpj());
            contentValues.put(ImobContract.PessoaEntry.COLUMN_TIPO_PESSOA, false);
        }

        long _id = dbHelper.getWritableDatabase()
                .insert(ImobContract.PessoaEntry.TABLE_NAME, null, contentValues);

        pessoa.setId(_id);

        for (Telefone telefone : pessoa.telefones())
            createTelefoneOfPessoa(pessoa, telefone);

        for (Email email : pessoa.emails())
            createEmailOfPessoa(pessoa, email);

        return pessoa;
    }

    public List<Pessoa> readAllPessoa() {
        List<Pessoa> pessoas = new ArrayList<>();
        String buildSQL = "SELECT * FROM " + ImobContract.PessoaEntry.TABLE_NAME;
        Cursor c = dbHelper.getReadableDatabase().rawQuery(buildSQL, null);
        while (c.moveToNext()) {
            Pessoa pessoa;
            if (c.getInt(c.getColumnIndex(ImobContract.PessoaEntry.COLUMN_TIPO_PESSOA)) == 1) {
                pessoa = new Pessoa.Fisica();
                ((Pessoa.Fisica) pessoa).setNome(c.getString(c.getColumnIndex(ImobContract.PessoaEntry.COLUMN_NOME)));
                ((Pessoa.Fisica) pessoa).setCpf(c.getString(c.getColumnIndex(ImobContract.PessoaEntry.COLUMN_CPF)));

            } else {
                pessoa = new Pessoa.Juridica();
                ((Pessoa.Juridica) pessoa).setNomeFantasia(c.getString(c.getColumnIndex(ImobContract.PessoaEntry.COLUMN_NOME_FANTASIA)));
                ((Pessoa.Juridica) pessoa).setRazaoSocial(c.getString(c.getColumnIndex(ImobContract.PessoaEntry.COLUMN_RAZAO_SOCIAL)));
                ((Pessoa.Juridica) pessoa).setCnpj(c.getString(c.getColumnIndex(ImobContract.PessoaEntry.COLUMN_CNPJ)));
            }
            pessoa.setId(c.getLong(c.getColumnIndex(ImobContract.PessoaEntry._ID)));
            pessoas.add(pessoa);
        }

        return pessoas;
    }

    public boolean updatePessoa(Pessoa pessoa) {
        return false;
    }

    public boolean deletePessoa(Pessoa pessoa) {
        return false;
    }

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

    private Telefone createTelefoneOfPessoa(Pessoa pessoa, Telefone telefone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImobContract.TelefoneEntry.COLUMN_TELEFONE, telefone.getNumero());
        contentValues.put(ImobContract.TelefoneEntry.COLUMN_PESSOA_ID, pessoa.getId());

        long _id = dbHelper.getWritableDatabase()
                .insert(ImobContract.TelefoneEntry.TABLE_NAME, null, contentValues);

        telefone.setId(_id);

        return telefone;
    }

    private Email createEmailOfPessoa(Pessoa pessoa, Email email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImobContract.EmailEntry.COLUMN_EMAIL, email.getEndereco());
        contentValues.put(ImobContract.EmailEntry.COLUMN_PESSOA_ID, pessoa.getId());

        long _id = dbHelper.getWritableDatabase()
                .insert(ImobContract.EmailEntry.TABLE_NAME, null, contentValues);

        email.setId(_id);

        return email;
    }
}
