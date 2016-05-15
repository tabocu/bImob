package br.com.blackseed.blackimob;

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

import br.com.blackseed.blackimob.data.ImobDb;
import br.com.blackseed.blackimob.entity.Pessoa;

public class AddInquilinoActivity extends AppCompatActivity {

    private AddPessoaFisicaFragment mAddPessoaFisicaFragment = new AddPessoaFisicaFragment();
    private AddPessoaJuridicaFragment mAddPessoaJuridicaFragment = new AddPessoaJuridicaFragment();

    private Switch mPessoaSwitch;

    private ImobDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new ImobDb(this);

        setContentView(R.layout.activity_add_inquilino);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setPessoaFisica();
        mPessoaSwitch = (Switch) findViewById(R.id.pessoaSwitch);
        mPessoaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) setPessoaJuridica();
                else setPessoaFisica();
            }
        });

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

    public void setPessoaFisica() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pessoaFrame, mAddPessoaFisicaFragment)
                .commit();
    }

    public void setPessoaJuridica() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pessoaFrame, mAddPessoaJuridicaFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_apply) {
            onClickAdd();
            Toast.makeText(this, R.string.pessoa_adicionada, Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onClickAdd() {

        if (mPessoaSwitch.isChecked()) {
            Pessoa.Juridica pessoa = new Pessoa.Juridica();
            pessoa.setRazaoSocial(mAddPessoaJuridicaFragment.getRazaoSocial());
            pessoa.setNomeFantasia(mAddPessoaJuridicaFragment.getNomeFantasia());
            pessoa.setCnpj(mAddPessoaJuridicaFragment.getCnpj());
            db.createPessoa(pessoa);
        } else {
            Pessoa.Fisica pessoa = new Pessoa.Fisica();
            pessoa.setNome(mAddPessoaFisicaFragment.getNome());
            pessoa.setCpf(mAddPessoaFisicaFragment.getCpf());
            db.createPessoa(pessoa);
        }

    }
}
