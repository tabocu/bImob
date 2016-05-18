package br.com.blackseed.blackimob.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ImobContract {

    public static final String CONTENT_AUTHORITY = "br.com.blackseed.blackimob";
    public static final Uri BASE_CONTENT_URI = Uri.parse("Content://" + CONTENT_AUTHORITY);

    public static final String PATH_PESSOA = "pessoa";
    public static final String PATH_IMOVEL = "imovel";
//    public static final String PATH_LOCACAO = "locacao";
    public static final String PATH_TELEFONE = "telefone";
    public static final String PATH_EMAIL = "email";

    public static final class PessoaEntry implements BaseColumns{

        public static final  Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PESSOA).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PESSOA;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PESSOA;

        //Table name
        public static final String TABLE_NAME = "pessoa";

        //Columns
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_NOME_FANTASIA = "nome_fantasia";
        public static final String COLUMN_RAZAO_SOCIAL = "razao_social";
        public static final String COLUMN_CPF = "cpf";
        public static final String COLUMN_CNPJ = "cnpj";
        public static final String COLUMN_IS_PESSOA_FISICA = "tipo_pessoa";
        public static final String COLUMN_IS_FAVORITO = "favorito";

        public static final String[] PESSOA_SELECT = {
                PessoaEntry._ID,
                PessoaEntry.COLUMN_IS_PESSOA_FISICA,
                PessoaEntry.COLUMN_NOME,
                PessoaEntry.COLUMN_NOME_FANTASIA,
                PessoaEntry.COLUMN_RAZAO_SOCIAL,
                PessoaEntry.COLUMN_CPF,
                PessoaEntry.COLUMN_CNPJ,
                PessoaEntry.COLUMN_IS_FAVORITO
        };

        public static Uri buildPessoaUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }

    public static final class ImovelEntry implements BaseColumns{

        public static final  Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_IMOVEL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IMOVEL;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IMOVEL;

        //Table name
        public static final String TABLE_NAME = "imovel";
        //Columns
        public static final String COLUMN_APELIDO = "apelido";
        public static final String COLUMN_CEP = "cep";
        public static final String COLUMN_TIPO_IMOVEL = "tipo_imovel";
        public static final String COLUMN_IS_FAVORITO = "favorito";


        public static final String[] IMOVEL_SELECT = {
                ImovelEntry._ID,
                ImovelEntry.COLUMN_APELIDO,
                ImovelEntry.COLUMN_CEP,
                ImovelEntry.COLUMN_TIPO_IMOVEL,
                ImovelEntry.COLUMN_IS_FAVORITO
        };

        public static Uri buildImovelUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

    }

//   public static final class LocacaoEntry implements BaseColumns{
//
//       public static final  Uri CONTENT_URI =
//               BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCACAO).build();
//
//       public static final String CONTENT_TYPE =
//               ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCACAO;
//
//       public static final String CONTENT_ITEM_TYPE =
//               ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCACAO;
//
//       //Table name
//       public static final String TABLE_NAME = "locacao";
//
//       public static Uri buildLocacaoUri(long id){
//           return ContentUris.withAppendedId(CONTENT_URI,id);
//       }
//
//    }

    public static final class TelefoneEntry implements BaseColumns{

        public static final  Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TELEFONE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TELEFONE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TELEFONE;

        //Table name
        public static final String TABLE_NAME = "telefone";
        //Columns
        public static final String COLUMN_TELEFONE = "telefone";
        public static final String COLUMN_PESSOA_ID = "pessoa_id";   //Foreign key

        public static Uri buildTelefoneUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }

    public static final class EmailEntry implements BaseColumns{

        public static final  Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EMAIL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EMAIL;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EMAIL;

        //Table name
        public static final String TABLE_NAME = "email";
        //Columns
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PESSOA_ID = "pessoa_id";   //Foreign key

        public static Uri buildEmailUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }


}
