package br.com.blackseed.blackimob;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class AddInquilinoActivity extends AppCompatActivity {

    private AddPessoaFisicaFragment mAddPessoaFisicaFragment = new AddPessoaFisicaFragment();
    private AddPessoaJuridicaFragment mAddPessoaJuridicaFragment = new AddPessoaJuridicaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inquilino);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setPessoaFisica();
        Switch pessoaSwitch = (Switch) findViewById(R.id.pessoaSwitch);
        pessoaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
