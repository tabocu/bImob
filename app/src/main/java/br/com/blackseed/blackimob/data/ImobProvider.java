package br.com.blackseed.blackimob.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class ImobProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ImobDbHelper mOpenHelper;

    static final int PESSOA     = 100;
    static final int IMOVEL     = 200;
    static final int TELEFONE   = 300;
    static final int EMAIL      = 400;


    @Override
    public boolean onCreate() {

        mOpenHelper = new ImobDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case PESSOA: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ImobContract.PessoaEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case IMOVEL: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ImobContract.ImovelEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case TELEFONE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ImobContract.TelefoneEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case EMAIL: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ImobContract.EmailEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri){

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PESSOA:
                return ImobContract.PessoaEntry.CONTENT_TYPE;
            case IMOVEL:
                return ImobContract.ImovelEntry.CONTENT_TYPE;
            case TELEFONE:
                return ImobContract.TelefoneEntry.CONTENT_TYPE;
            case EMAIL:
                return ImobContract.EmailEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case PESSOA: {
                long _id = db.insert(ImobContract.PessoaEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = ImobContract.PessoaEntry.buildPessoaUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case IMOVEL: {
                long _id = db.insert(ImobContract.ImovelEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = ImobContract.ImovelEntry.buildImovelUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TELEFONE: {
                long _id = db.insert(ImobContract.TelefoneEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = ImobContract.TelefoneEntry.buildTelefoneUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case EMAIL: {
                long _id = db.insert(ImobContract.EmailEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = ImobContract.EmailEntry.buildEmailUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)  {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case PESSOA:
                rowsDeleted = db.delete(
                        ImobContract.PessoaEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case IMOVEL:
                rowsDeleted = db.delete(
                        ImobContract.ImovelEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TELEFONE:
                rowsDeleted = db.delete(
                        ImobContract.TelefoneEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EMAIL:
                rowsDeleted = db.delete(
                        ImobContract.EmailEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case PESSOA:
                rowsUpdated = db.update(ImobContract.PessoaEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case IMOVEL:
                rowsUpdated = db.update(ImobContract.ImovelEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TELEFONE:
                rowsUpdated = db.update(ImobContract.TelefoneEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case EMAIL:
                rowsUpdated = db.update(ImobContract.EmailEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }



    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ImobContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, ImobContract.PATH_PESSOA, PESSOA);
        matcher.addURI(authority, ImobContract.PATH_IMOVEL, IMOVEL);
        matcher.addURI(authority, ImobContract.PATH_TELEFONE, TELEFONE);
        matcher.addURI(authority, ImobContract.PATH_EMAIL, EMAIL);

        return matcher;
    }

}
