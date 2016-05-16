package br.com.blackseed.blackimob.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.adapter.EmailsAdapter;
import br.com.blackseed.blackimob.adapter.TelefonesAdapter;
import br.com.blackseed.blackimob.data.ImobContract;
import br.com.blackseed.blackimob.data.ImobDb;
import br.com.blackseed.blackimob.entity.Email;
import br.com.blackseed.blackimob.entity.Pessoa;
import br.com.blackseed.blackimob.entity.Telefone;

public class DetailPessoaFisicaActivity extends AppCompatActivity {

    private ImobDb db;

    private Pessoa.Fisica pessoa;
    private List<Telefone> telefoneList;
    private List<Email> emailList;

    private TextView mCpfTextView;
    private ListView mTelefoneListView;
    private ListView mEmailListView;

    private TelefonesAdapter sTelefonesAdapter;
    private EmailsAdapter sEmailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pessoa_fisica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        loadData(bundle.getLong("id"));

        sTelefonesAdapter = new TelefonesAdapter(this,R.layout.contato_item_list);
        sEmailsAdapter = new EmailsAdapter(this,R.layout.contato_item_list);

        sTelefonesAdapter.addAll(telefoneList);
        sEmailsAdapter.addAll(emailList);

        mTelefoneListView = (ListView) findViewById(R.id.telefoneListView);
        mEmailListView = (ListView) findViewById(R.id.emailListView);

        mTelefoneListView.setAdapter(sTelefonesAdapter);
        mEmailListView.setAdapter(sEmailsAdapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(pessoa.getNome());
    }

    private void loadData(long id) {
        db = new ImobDb(this);
        pessoa = (Pessoa.Fisica) db.readPessoa(id);
        telefoneList = db.readTelefone(ImobContract.TelefoneEntry.COLUMN_PESSOA_ID,pessoa.getId());
        emailList = db.readEmail(ImobContract.EmailEntry.COLUMN_PESSOA_ID,pessoa.getId());

    }
}
