package br.com.blackseed.blackimob;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import br.com.blackseed.blackimob.data.ImobContract.PessoaEntry;
import br.com.blackseed.blackimob.data.ImobDb;


public class AddInquilinoActivity extends AppCompatActivity {

    long id = -1;
    private ImobDb db;
    private AddPessoaFisicaFragment mAddPessoaFisicaFragment = new AddPessoaFisicaFragment();
    private AddPessoaJuridicaFragment mAddPessoaJuridicaFragment = new AddPessoaJuridicaFragment();

    private Switch mPessoaSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inquilino);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializa o banco de dados
        db = new ImobDb(this);

        // Obtem referencia do seletor de pessoa
        mPessoaSwitch = (Switch) findViewById(R.id.pessoaSwitch);

        // Pega o id da pessoa (no caso de edição)
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Obtem o id da pessoa
            id = bundle.getLong("id");
            // Obtem o objeto pessoa do banco de dados
            Cursor pessoaCursor = db.fetchPessoa(id);

            int columnIsPessoaFisica = pessoaCursor.getColumnIndexOrThrow(PessoaEntry.COLUMN_IS_PESSOA_FISICA);

            if (pessoaCursor.getInt(columnIsPessoaFisica) == 1) { // Se for pessoa fisica
                // Carrega a fragment de pessoa fisica
                mAddPessoaFisicaFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.pessoaFrame, mAddPessoaFisicaFragment)
                        .commit();
                mPessoaSwitch.setChecked(false);
            } else { // Se for pessoa juridica
                // Carrega o fragment de pessoa juridica
                mAddPessoaJuridicaFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.pessoaFrame, mAddPessoaJuridicaFragment)
                        .commit();
                mPessoaSwitch.setChecked(true);
            }
            // Desabilita o switch
            mPessoaSwitch.setEnabled(false);
        } else {
            // Carrega a fragment de pessoa fisica
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pessoaFrame, mAddPessoaFisicaFragment)
                    .commit();
            // Adiciona o listener responsavel por alternar entre os
            // fragmentes de pessoa fisica e juridica
            mPessoaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.pessoaFrame, mAddPessoaJuridicaFragment)
                                .commit();
                    } else {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.pessoaFrame, mAddPessoaFisicaFragment)
                                .commit();
                    }
                }
            });
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_apply) {
            long pessoaId;
            if (mPessoaSwitch.isChecked()) pessoaId = mAddPessoaJuridicaFragment.saveData();
            else pessoaId = mAddPessoaFisicaFragment.saveData();

            if (pessoaId == -1)
                Toast.makeText(this, R.string.pessoa_adicionada, Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, R.string.pessoa_atualizada, Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
