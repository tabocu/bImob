package br.com.blackseed.blackimob.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ImobContract {

    public static final String CONTENT_AUTHORITY = "br.com.blackseed.blackimob";
    public static final Uri BASE_CONTENT_URI = Uri.parse("Content://" + CONTENT_AUTHORITY);

    public static final String PATH_INQUILINO = "inquilino";
    public static final String PATH_IMOVEL = "imovel";
//    public static final String PATH_LOCACAO = "locacao";
    public static final String PATH_TELEFONE = "telefone";
    public static final String PATH_EMAIL = "email";

    public static final class InquilinoEntry implements BaseColumns{

        public static final  Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INQUILINO).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INQUILINO;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INQUILINO;

        //Table name
        public static final String TABLE_NAME = "inquilino";

        //Columns
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_CADASTRO_PESSOA = "cadastro_pessoa";
        public static final String COLUMN_TIPO_PESSOA = "tipo_pessoa";

        public static Uri buildInquilinoUri(long id){
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
        public static final String COLUMN_TIPO_IMOVEL= "tipo_imovel";

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
        public static final String COLUMN_INQUILINO_ID = "inquilino_id";   //Foreign key

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
        public static final String COLUMN_INQUILINO_ID = "inquilino_id";   //Foreign key

        public static Uri buildEmailUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }


}
