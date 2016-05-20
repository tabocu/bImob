package br.com.blackseed.blackimob.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import br.com.blackseed.blackimob.data.ImobContract.EmailEntry;
import br.com.blackseed.blackimob.data.ImobContract.EnderecoEntry;
import br.com.blackseed.blackimob.data.ImobContract.ImovelEntry;
import br.com.blackseed.blackimob.data.ImobContract.PessoaEntry;
import br.com.blackseed.blackimob.data.ImobContract.TelefoneEntry;
import br.com.blackseed.blackimob.entity.Email;
import br.com.blackseed.blackimob.entity.Endereco;
import br.com.blackseed.blackimob.entity.Imovel;
import br.com.blackseed.blackimob.entity.Pessoa;
import br.com.blackseed.blackimob.entity.Telefone;


public class ImobDb {

    private ImobDbHelper dbHelper;

    public ImobDb(Context context) {
        dbHelper = new ImobDbHelper(context);
    }

    // Novo metodo de trabalhar o banco
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

        if (!cursor.moveToNext()) return null;
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

        if (!cursor.moveToNext()) return null;
        return cursor;
    }

    public int deletePessoa(long id) {
        deleteTelefone(TelefoneEntry.COLUMN_PESSOA_ID, id);
        deleteEmail(EmailEntry.COLUMN_PESSOA_ID, id);
        return dbHelper.getWritableDatabase()
                .delete(PessoaEntry.TABLE_NAME, PessoaEntry._ID + " = " + id, null);
    }

    public void updatePessoa(long id, ContentValues contentValues) {
        String where = PessoaEntry._ID + " = " + id;
        dbHelper.getWritableDatabase().update(PessoaEntry.TABLE_NAME, contentValues, where, null);
    }

    public static List<String> cursorToStringList(Cursor cursor, String column) {
        List<String> stringList = new ArrayList<>();
        int columnIndex = cursor.getColumnIndexOrThrow(column);
        for (cursor.moveToFirst(); cursor.isAfterLast(); cursor.moveToNext())
            stringList.add(cursor.getString(columnIndex));
        return stringList;
    }








    private static ContentValues getContentValues(Pessoa pessoa) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(PessoaEntry.COLUMN_NOME, pessoa.getNome());

        if (pessoa.getEndereco() != null)
            contentValues.put(PessoaEntry.COLUMN_ENDERECO_ID, pessoa.getEndereco().getId());

        if (pessoa.isPessoaFisica()) {
            Pessoa.Fisica pessoaFisica = (Pessoa.Fisica) pessoa;
            contentValues.put(PessoaEntry.COLUMN_CPF, pessoaFisica.getCpf());
            contentValues.put(PessoaEntry.COLUMN_IS_FAVORITO, pessoaFisica.isFavorito());
            contentValues.put(PessoaEntry.COLUMN_IS_PESSOA_FISICA, true);
        } else {
            Pessoa.Juridica pessoaJuridica = (Pessoa.Juridica) pessoa;
            contentValues.put(PessoaEntry.COLUMN_RAZAO_SOCIAL, pessoaJuridica.getRazaoSocial());
            contentValues.put(PessoaEntry.COLUMN_CNPJ, pessoaJuridica.getCnpj());
            contentValues.put(PessoaEntry.COLUMN_IS_FAVORITO, pessoaJuridica.isFavorito());
            contentValues.put(PessoaEntry.COLUMN_IS_PESSOA_FISICA, false);
        }

        return contentValues;
    }

    private static ContentValues getContentValues(Imovel imovel) {
        ContentValues contentValues = new ContentValues();

        if (imovel.getEndereco() != null)
            contentValues.put(PessoaEntry.COLUMN_ENDERECO_ID, imovel.getEndereco().getId());
        contentValues.put(ImovelEntry.COLUMN_APELIDO, imovel.getApelido());
        contentValues.put(ImovelEntry.COLUMN_TIPO_IMOVEL, imovel.getTipo());
        contentValues.put(ImovelEntry.COLUMN_IS_FAVORITO, imovel.isFavorito());

        return contentValues;
    }


    //---------------------------------------------------------------------------------------------
    //CRUD IMOVEL

    private static ContentValues getContentValues(Telefone telefone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TelefoneEntry.COLUMN_TELEFONE, telefone.getNumero());

        return contentValues;
    }

    private static ContentValues getContentValues(Email email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EmailEntry.COLUMN_EMAIL, email.getEndereco());

        return contentValues;
    }

    private static ContentValues getContentValues(Endereco endereco) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EnderecoEntry.COLUMN_PLACE_ID, endereco.getPlaceId());
        contentValues.put(ImobContract.EnderecoEntry.COLUMN_LOCAL, endereco.getLocal());
        contentValues.put(ImobContract.EnderecoEntry.COLUMN_COMPLEMENTO, endereco.getComplemento());
        contentValues.put(ImobContract.EnderecoEntry.COLUMN_LATITUDE, endereco.getLatitude());
        contentValues.put(ImobContract.EnderecoEntry.COLUMN_LONGITUDE, endereco.getLongitude());

        return contentValues;
    }



    public Pessoa createPessoa(Pessoa pessoa) {

        if (pessoa.getEndereco() != null)
            createEndereco(pessoa.getEndereco());

        ContentValues contentValues = getContentValues(pessoa);
        long _id = dbHelper.getWritableDatabase().insert(PessoaEntry.TABLE_NAME, null, contentValues);

        pessoa.setId(_id);
        return pessoa;
    }








    //---------------------------------------------------------------------------------------------
    //CRUD IMOVEL

    public Pessoa readPessoa(long id) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PessoaEntry.TABLE_NAME);
        qb.appendWhere(PessoaEntry._ID + " = " + id);
        Cursor pessoaCursor = qb.query(dbHelper.getReadableDatabase(),
                PessoaEntry.PESSOA_SELECT,
                null, null, null, null, null);

        Pessoa pessoa;

        if (!pessoaCursor.moveToNext()) return null;
        if (pessoaCursor.getInt(pessoaCursor.getColumnIndex(PessoaEntry.COLUMN_IS_PESSOA_FISICA)) == 1) {
            pessoa = new Pessoa.Fisica();
            ((Pessoa.Fisica) pessoa).setCpf(
                    pessoaCursor.getString(
                            pessoaCursor.getColumnIndex(
                                    PessoaEntry.COLUMN_CPF)));

        } else {
            pessoa = new Pessoa.Juridica();
            ((Pessoa.Juridica) pessoa).setRazaoSocial(
                    pessoaCursor.getString(
                            pessoaCursor.getColumnIndex(
                                    PessoaEntry.COLUMN_RAZAO_SOCIAL)));
            ((Pessoa.Juridica) pessoa).setCnpj(
                    pessoaCursor.getString(
                            pessoaCursor.getColumnIndex(
                                    PessoaEntry.COLUMN_CNPJ)));
        }

        //pessoa.setEndereco(readEndereco(
        //      pessoaCursor.getLong(pessoaCursor.getColumnIndex(PessoaEntry.COLUMN_ENDERECO_ID))));
        pessoa.setNome(pessoaCursor.getString(pessoaCursor.getColumnIndex(PessoaEntry.COLUMN_NOME)));
        pessoa.setFavorito(pessoaCursor.getInt(pessoaCursor.getColumnIndex(PessoaEntry.COLUMN_IS_FAVORITO)) == 1);
        pessoa.setId(pessoaCursor.getLong(pessoaCursor.getColumnIndex(PessoaEntry._ID)));

        return pessoa;
    }

    public List<Pessoa> readAllPessoa() {

        String ordenacao = PessoaEntry.COLUMN_IS_FAVORITO + " DESC, " + PessoaEntry.COLUMN_NOME + " ASC";

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PessoaEntry.TABLE_NAME);

        List<Pessoa> pessoaList = new ArrayList<>();
        Cursor pessoaCursor = qb.query(dbHelper.getReadableDatabase(),
                PessoaEntry.PESSOA_SELECT,
                null, null, null, null, ordenacao);

        while (pessoaCursor.moveToNext()) {
            Pessoa pessoa;
            if (pessoaCursor.getInt(pessoaCursor.getColumnIndex(PessoaEntry.COLUMN_IS_PESSOA_FISICA)) == 1) {
                pessoa = new Pessoa.Fisica();
                ((Pessoa.Fisica) pessoa).setCpf(
                        pessoaCursor.getString(
                                pessoaCursor.getColumnIndex(
                                        PessoaEntry.COLUMN_CPF)));

            } else {
                pessoa = new Pessoa.Juridica();

                ((Pessoa.Juridica) pessoa).setRazaoSocial(
                        pessoaCursor.getString(
                                pessoaCursor.getColumnIndex(
                                        PessoaEntry.COLUMN_RAZAO_SOCIAL)));
                ((Pessoa.Juridica) pessoa).setCnpj(
                        pessoaCursor.getString(
                                pessoaCursor.getColumnIndex(
                                        PessoaEntry.COLUMN_CNPJ)));
            }


            pessoa.setEndereco(readEndereco(
                    pessoaCursor.getLong(pessoaCursor.getColumnIndex(PessoaEntry.COLUMN_ENDERECO_ID))));
            pessoa.setNome(pessoaCursor.getString(pessoaCursor.getColumnIndex(PessoaEntry.COLUMN_NOME)));
            pessoa.setFavorito(pessoaCursor.getInt(pessoaCursor.getColumnIndex(PessoaEntry.COLUMN_IS_FAVORITO)) == 1);
            pessoa.setId(pessoaCursor.getLong(pessoaCursor.getColumnIndex(PessoaEntry._ID)));
            pessoaList.add(pessoa);
        }
        return pessoaList;
    }

    public void updatePessoa(Pessoa pessoa) {
        ContentValues contentValues = getContentValues(pessoa);
        String where = PessoaEntry._ID + " = " + pessoa.getId();
        dbHelper.getWritableDatabase().update(PessoaEntry.TABLE_NAME, contentValues, where, null);
    }

    public int deletePessoa(Pessoa pessoa) {
        deleteTelefone(TelefoneEntry.COLUMN_PESSOA_ID, pessoa.getId());
        deleteEmail(EmailEntry.COLUMN_PESSOA_ID, pessoa.getId());
        deleteEndereco(pessoa.getEndereco());
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







    //---------------------------------------------------------------------------------------------
    //CRUD TELEFONE

    public Imovel createImovel(Imovel imovel) {

        if (imovel.getEndereco() != null)
            createEndereco(imovel.getEndereco());
        ContentValues contentValues = getContentValues(imovel);

        long _id = dbHelper.getWritableDatabase().insert(ImovelEntry.TABLE_NAME, null, contentValues);

        imovel.setId(_id);
        return imovel;
    }

    public List<Imovel> readAllImovel() {

        String ordenacao = ImovelEntry.COLUMN_IS_FAVORITO + " DESC, " + ImovelEntry.COLUMN_APELIDO + " ASC";

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(ImovelEntry.TABLE_NAME);

        List<Imovel> imovelList = new ArrayList<>();
        Cursor imovelCursor = qb.query(dbHelper.getReadableDatabase(),
                ImovelEntry.IMOVEL_SELECT,
                null, null, null, null, ordenacao);

        while (imovelCursor.moveToNext()) {
            Imovel imovel = new Imovel();
            imovel.setApelido(
                    imovelCursor.getString(
                            imovelCursor.getColumnIndex(
                                    ImovelEntry.COLUMN_APELIDO)));

            imovel.setTipo(
                    imovelCursor.getString(
                            imovelCursor.getColumnIndex(
                                    ImovelEntry.COLUMN_TIPO_IMOVEL)));

            imovel.setFavorito(
                    imovelCursor.getInt(
                            imovelCursor.getColumnIndex(
                                    ImovelEntry.COLUMN_IS_FAVORITO)) == 1);

            imovel.setEndereco(readEndereco(
                    imovelCursor.getLong(imovelCursor.getColumnIndex(ImovelEntry.COLUMN_ENDERECO_ID))));
            imovel.setId(imovelCursor.getLong(imovelCursor.getColumnIndex(ImovelEntry._ID)));
            imovelList.add(imovel);
        }
        return imovelList;
    }

    public boolean updateImovel(Imovel imovel) {
        return false;
    }

    public int deleteImovel(Imovel imovel) {
        deleteEndereco(imovel.getEndereco());
        return dbHelper.getWritableDatabase()
                .delete(ImovelEntry.TABLE_NAME, ImovelEntry._ID + " = " + imovel.getId(), null);
    }

    public int deleteImovel(List<Imovel> imoveis) {
        int result = 0;
        for (Imovel imovel : imoveis) {
            result += deleteImovel(imovel);
        }
        return result;
    }

    public void createTelefone(String column, Long foreignKey, List<Telefone> telefoneList) {

        for (Telefone telefone : telefoneList) {
            createTelefone(column, foreignKey, telefone);
        }
    }








    //---------------------------------------------------------------------------------------------
    //CRUD EMAIL

    public Telefone createTelefone(String column, Long foreignKey, Telefone telefone) {
        ContentValues contentValues = getContentValues(telefone);
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









    //---------------------------------------------------------------------------------------------
    //CRUD ENDEREÇO

    public void createEmail(String column, Long foreignKey, List<Email> emailList) {

        for (Email email : emailList) {
            createEmail(column, foreignKey, email);
        }
    }

    public Email createEmail(String foreignColumn, Long foreignKey, Email email) {
        ContentValues contentValues = getContentValues(email);
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
        qb.setTables(EmailEntry.TABLE_NAME);
        qb.appendWhere(foreignColumn + " = " + foreignKey);
        Cursor emailCursor = qb.query(dbHelper.getReadableDatabase(),
                sqlSelect,
                null, null, null, null, null);

        int _id = emailCursor.getColumnIndex(EmailEntry._ID);
        int endereco = emailCursor.getColumnIndex(EmailEntry.COLUMN_EMAIL);

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









    //---------------------------------------------------------------------------------------------
    //CONTENT VALUES

    public int deleteEmail(Email email) {
        return dbHelper.getWritableDatabase()
                .delete(EmailEntry.TABLE_NAME, EmailEntry._ID + " = " + email.getId(), null);
    }

    public int deleteEmail(String foreignColumn, long foreignKey) {
        return dbHelper.getWritableDatabase()
                .delete(EmailEntry.TABLE_NAME, foreignColumn + " = " + foreignKey, null);
    }

    public Endereco createEndereco(Endereco endereco) {

        ContentValues contentValues = getContentValues(endereco);
        long _id = dbHelper.getWritableDatabase().insert(EnderecoEntry.TABLE_NAME, null, contentValues);

        endereco.setId(_id);
        return endereco;
    }

    public Endereco readEndereco(long id) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(EnderecoEntry.TABLE_NAME);
        qb.appendWhere(EnderecoEntry._ID + " = " + id);
        Cursor enderecoCursor = qb.query(dbHelper.getReadableDatabase(),
                EnderecoEntry.ENDERECO_SELECT,
                null, null, null, null, null);

        Endereco endereco = new Endereco();

        endereco.setPlace_id(enderecoCursor.getString(enderecoCursor.getColumnIndex(EnderecoEntry.COLUMN_PLACE_ID)));
        endereco.setLocal(enderecoCursor.getString(enderecoCursor.getColumnIndex(EnderecoEntry.COLUMN_LOCAL)));
        endereco.setComplemento(enderecoCursor.getString(enderecoCursor.getColumnIndex(EnderecoEntry.COLUMN_COMPLEMENTO)));
        endereco.setLatitude(enderecoCursor.getDouble(enderecoCursor.getColumnIndex(EnderecoEntry.COLUMN_LATITUDE)));
        endereco.setLongitude(enderecoCursor.getDouble(enderecoCursor.getColumnIndex(EnderecoEntry.COLUMN_LONGITUDE)));
        endereco.setId(enderecoCursor.getLong(enderecoCursor.getColumnIndex(EnderecoEntry._ID)));

        return endereco;
    }

    public int deleteEndereco(Endereco endereco) {
        return dbHelper.getWritableDatabase()
                .delete(EnderecoEntry.TABLE_NAME, EnderecoEntry._ID + " = " + endereco.getId(), null);
    }


}
