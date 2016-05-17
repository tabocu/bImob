package br.com.blackseed.blackimob.detail;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.blackseed.blackimob.R;
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
    private LinearLayout mTelefoneLayout;
    private LinearLayout mEmailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pessoa_fisica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        loadData(bundle.getLong("id"));

        if (telefoneList.isEmpty() && emailList.isEmpty()) {
            findViewById(R.id.contatoCard).setVisibility(View.GONE);
        } else {
            if (!telefoneList.isEmpty()) {
                mTelefoneLayout = (LinearLayout) findViewById(R.id.telefoneLayout);
                getTelefoneView();
            } else {
                findViewById(R.id.separador).setVisibility(View.GONE);
            }
            mEmailLayout = (LinearLayout) findViewById(R.id.emailLayout);
            getEmailView();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(pessoa.getNome());
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

        } else if (id == R.id.action_edit) {

        } else if (id == R.id.action_favorite) {
            if (item.isChecked()) {
                item.setIcon(R.drawable.ic_favorite_24dp);
                item.setChecked(false);
            } else {
                item.setIcon(R.drawable.ic_favorite_not_24dp);
                item.setChecked(true);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadData(long id) {
        db = new ImobDb(this);
        pessoa = (Pessoa.Fisica) db.readPessoa(id);
        telefoneList = db.readTelefone(ImobContract.TelefoneEntry.COLUMN_PESSOA_ID,pessoa.getId());
        emailList = db.readEmail(ImobContract.EmailEntry.COLUMN_PESSOA_ID,pessoa.getId());
    }


    public void getTelefoneView() {

        for (int i = 0; i < telefoneList.size(); i++) {
            final Telefone telefone = telefoneList.get(i);

            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(this);
            View view = layoutInflater.inflate(R.layout.contato_item_list, null);

            if (i == 0) {
                ImageView imageView = (ImageView) view.findViewById(R.id.contatoImageView);
                imageView.setImageResource(R.drawable.ic_call_gray_24dp);
            }

            TextView textView = (TextView) view.findViewById(R.id.contatoTextView);
            textView.setText(telefone.getNumero());

            ImageButton imageButton = (ImageButton) view.findViewById(R.id.contatoImageBtn);
            imageButton.setImageResource(R.drawable.ic_sms_chat_black_24dp);

            // Botão de mensagem clicado
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("sms:" + telefone.getNumero()));
                    sendIntent.putExtra("sms_body", "");
                    startActivity(sendIntent);
                }
            });

            // Telefone clicado
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = ((TextView) v.findViewById(R.id.contatoTextView)).getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + telefone.getNumero()));
                    startActivity(intent);
                }
            });

            // Clicar e segurar
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemMenu(telefone.getNumero());
                    return true;
                }
            });

            mTelefoneLayout.addView(view);
        }
    }


    public void getEmailView() {
        for (int i = 0; i < emailList.size(); i++) {
            final Email email = emailList.get(i);

            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(this);
            View view = layoutInflater.inflate(R.layout.email_item_list, null);

            if (i == 0) {
                ImageView imageView = (ImageView) view.findViewById(R.id.contatoImageView);
                imageView.setImageResource(R.drawable.ic_email_gray_24dp);
            }

            TextView textView = (TextView) view.findViewById(R.id.contatoTextView);
            textView.setText(email.getEndereco());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:" + email.getEndereco()));
                    startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
                }
            });

            // Clicar e segurar
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemMenu(email.getEndereco());
                    return true;
                }
            });

            mEmailLayout.addView(view);
        }
    }

    private void itemMenu(final String content) {
        final CharSequence[] options = {"Copiar para área de transferência"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(content);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Imob", content);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(DetailPessoaFisicaActivity.this, "Texto copiado", Toast.LENGTH_SHORT);
                }
            }
        });
        builder.show();
    }
}
