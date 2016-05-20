package br.com.blackseed.blackimob.detail;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.LinearLayout;

import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.adapter.EmailAdapter;
import br.com.blackseed.blackimob.adapter.TelefoneAdapter;
import br.com.blackseed.blackimob.adapter.UtilsCursorAdapter;
import br.com.blackseed.blackimob.data.ImobContract.PessoaEntry;

public class DetailPessoaJuridicaActivity extends DetailPessoaActivity {

    private LinearLayout mDadosLinearLayout;
    private LinearLayout mContatosLinearLayout;
    private boolean favorito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pessoa_fisica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDadosLinearLayout = (LinearLayout) findViewById(R.id.dadosLinearLayout);
        mContatosLinearLayout = (LinearLayout) findViewById(R.id.contatosLinearLayout);

        Cursor cursorPessoa = getDB().fetchPessoa(getId());
        favorito = cursorPessoa.getInt(
                cursorPessoa.getColumnIndexOrThrow(
                        PessoaEntry.COLUMN_IS_FAVORITO)) == 1;
        mDadosLinearLayout.addView(UtilsCursorAdapter.Nome.getViewFromCursor(this, cursorPessoa));
        mDadosLinearLayout.addView(UtilsCursorAdapter.getSeparadorView(this));
        mDadosLinearLayout.addView(UtilsCursorAdapter.RazaoSocial.getViewFromCursor(this, cursorPessoa));
        mDadosLinearLayout.addView(UtilsCursorAdapter.getSeparadorView(this));
        mDadosLinearLayout.addView(UtilsCursorAdapter.Cnpj.getViewFromCursor(this, cursorPessoa));

        Cursor cursorTelefone = getDB().fetchTelefoneOfPessoa(getId());
        TelefoneAdapter telefoneAdapter = new TelefoneAdapter(this, cursorTelefone);
        for (int i = 0; i < telefoneAdapter.getCount(); i++)
            mContatosLinearLayout.addView(telefoneAdapter.getView(i, null, null));

        mContatosLinearLayout.addView(UtilsCursorAdapter.getSeparadorView(this));

        Cursor cursorEmail = getDB().fetchEmailOfPessoa(getId());
        EmailAdapter emailAdapter = new EmailAdapter(this, cursorEmail);
        for (int i = 0; i < emailAdapter.getCount(); i++)
            mContatosLinearLayout.addView(emailAdapter.getView(i, null, null));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(cursorPessoa.getString(cursorPessoa.getColumnIndexOrThrow(PessoaEntry.COLUMN_NOME)));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.action_favorite).setChecked(favorito);

        if (favorito)
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_24dp);
        else
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_not_24dp);
        return true;
    }
}
