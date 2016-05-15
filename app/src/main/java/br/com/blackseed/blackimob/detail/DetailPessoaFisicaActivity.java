package br.com.blackseed.blackimob.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.data.ImobDb;

public class DetailPessoaFisicaActivity extends AppCompatActivity {

    private ImobDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pessoa_fisica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
