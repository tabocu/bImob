package br.com.blackseed.blackimob;

import android.content.Intent;
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

public class AddInquilinoActivity extends AppCompatActivity {

    private AddPessoaFisicaFragment mAddPessoaFisicaFragment = new AddPessoaFisicaFragment();
    private AddPessoaJuridicaFragment mAddPessoaJuridicaFragment = new AddPessoaJuridicaFragment();

    private Switch mPessoaSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            Toast.makeText(this, R.string.imovel_adicionado, Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onClickAdd() {
        Intent newIntent = getIntent();
        newIntent.putExtra("tag_pessoa_juridica", mPessoaSwitch.isChecked());

        if (mPessoaSwitch.isChecked()) {
            newIntent.putExtra("tag_nome_fantasia", mPessoaSwitch.isChecked());
            newIntent.putExtra("tag_razao_social", mPessoaSwitch.isChecked());
            newIntent.putExtra("tag_cnpj", mPessoaSwitch.isChecked());
            newIntent.putExtra("tag_telefone", mPessoaSwitch.isChecked());
            newIntent.putExtra("tag_email", mPessoaSwitch.isChecked());
        } else {
            newIntent.putExtra("tag_nome", mPessoaSwitch.isChecked());
            newIntent.putExtra("tag_cpf", mPessoaSwitch.isChecked());
            newIntent.putExtra("tag_telefone", mPessoaSwitch.isChecked());
            newIntent.putExtra("tag_email", mPessoaSwitch.isChecked());
        }

        setResult(RESULT_OK, newIntent);
    }
}
