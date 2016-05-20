package br.com.blackseed.blackimob.detail;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.blackseed.blackimob.AddInquilinoActivity;
import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.data.ImobContract;
import br.com.blackseed.blackimob.data.ImobDb;

public abstract class DetailPessoaActivity extends AppCompatActivity {

    private long id;
    private ImobDb db;

    private static final ContentValues FAVORITE_TRUE;
    private static final ContentValues FAVORITE_FALSE;

    static {
        FAVORITE_TRUE = new ContentValues();
        FAVORITE_TRUE.put(ImobContract.PessoaEntry.COLUMN_IS_FAVORITO, true);
        FAVORITE_FALSE = new ContentValues();
        FAVORITE_FALSE.put(ImobContract.PessoaEntry.COLUMN_IS_FAVORITO, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new ImobDb(this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getLong("id");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            deletePessoa();
        } else if (id == R.id.action_edit) {
            editPessoa();
        } else if (id == R.id.action_favorite) {
            favoritePessoa(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        recreate();
    }

    public long getId() {
        return id;
    }

    public ImobDb getDB() {
        return db;
    }

    private void deletePessoa() {
        // Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Define os parâmetros
        builder.setTitle(R.string.deletePessoaAlertTitle)
                .setMessage(R.string.deletePessoaAlertText)
                .setPositiveButton(R.string.deletePessoaAlertPositive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(DetailPessoaActivity.this, R.string.deletePessoaAlertToast, Toast.LENGTH_SHORT).show();
                        db.deletePessoa(id);
                        finish();
                    }
                }).setNegativeButton(R.string.deletePessoaAlertNegative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        }).create().show(); // Exibe
    }

    private void editPessoa() {
        Intent intent = new Intent(this, AddInquilinoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1, null);
    }

    private void favoritePessoa(MenuItem item) {
        if (item.isChecked()) {
            item.setIcon(R.drawable.ic_favorite_not_24dp);
            item.setChecked(false);
            db.updatePessoa(id, FAVORITE_FALSE);
        } else {
            item.setIcon(R.drawable.ic_favorite_24dp);
            item.setChecked(true);
            db.updatePessoa(id, FAVORITE_TRUE);
        }
    }

}
