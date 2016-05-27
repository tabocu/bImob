package br.com.blackseed.blackimob;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.blackseed.blackimob.components.MultiEditView;
import br.com.blackseed.blackimob.data.ImobContract;
import br.com.blackseed.blackimob.data.ImobContract.EmailEntry;
import br.com.blackseed.blackimob.data.ImobContract.PessoaEntry;
import br.com.blackseed.blackimob.data.ImobContract.TelefoneEntry;
import br.com.blackseed.blackimob.data.ImobDb;
import br.com.blackseed.blackimob.utils.MaskTextWatcher;

public class AddPessoaFisicaFragment extends Fragment {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    private ImobDb db;

    private long id = -1;
    private EditText mNomeEditText;
    private EditText mCpfEditText;
    private MultiEditView mTelefoneMultiEditView;
    private MultiEditView mEmailMultiEditView;
    private EditText mEnderecoEditText;
    private EditText mComplementoEditText;

    private boolean mBlockAutoComplete = false;

    public AddPessoaFisicaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout do fragment
        View rootView = inflater.inflate(R.layout.fragment_add_pessoa_fisica, container, false);

        // Inicializa o banco de dados
        db = new ImobDb(getContext());

        // Obtem referencias dos componentes do layout
        mNomeEditText = (EditText) rootView.findViewById(R.id.nomeEditText);
        mCpfEditText = (EditText) rootView.findViewById(R.id.cpfEditText);
        mTelefoneMultiEditView = (MultiEditView) rootView.findViewById(R.id.telefoneMultiEditView);
        mEmailMultiEditView = (MultiEditView) rootView.findViewById(R.id.emailMultiEditView);
        mEnderecoEditText = (EditText) rootView.findViewById(R.id.enderecoEditText);
        mComplementoEditText = (EditText) rootView.findViewById(R.id.complementoEditText);

        // Configura o campo de Cpf com mascara e tipo de entrada
        mCpfEditText.addTextChangedListener(new MaskTextWatcher(MaskTextWatcher.Mask.CPF));
        mCpfEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if (hasFocus && editText.getText().toString().isEmpty()) {
                    editText.setText("0");
                } else if (!hasFocus && editText.getText().toString().equals("000.000.000-00")) {
                    editText.setText("");
                }
            }
        });
        mCpfEditText.setRawInputType(InputType.TYPE_CLASS_NUMBER);

        mEnderecoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mBlockAutoComplete) return;
                if (s.length() > 2) {
                    mBlockAutoComplete = true;
                    Intent intent = new Intent(getContext(), PlaceActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("text", s.toString());
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
                }
            }
        });

        // Pega o id da pessoa (no caso de edição)
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Obtem o id da pessoa
            id = bundle.getLong("id");
            // Obtem os cursores
            Cursor pessoaCursor = db.fetchPessoa(id);
            Cursor telefoneCursor = db.fetchTelefoneOfPessoa(id);
            Cursor emailCursor = db.fetchEmailOfPessoa(id);

            // Obtem colunas do cursor
            int columnNome = pessoaCursor.getColumnIndexOrThrow(PessoaEntry.COLUMN_NOME);
            int columnCpf = pessoaCursor.getColumnIndexOrThrow(PessoaEntry.COLUMN_CPF);
            // Preenche os campos de dados
            mNomeEditText.setText(pessoaCursor.getString(columnNome));
            mCpfEditText.setText(pessoaCursor.getString(columnCpf));
            // Preenche os campos de contatos
            mTelefoneMultiEditView.setTextList(ImobDb.cursorToStringList(telefoneCursor, TelefoneEntry.COLUMN_TELEFONE));
            mEmailMultiEditView.setTextList(ImobDb.cursorToStringList(emailCursor, EmailEntry.COLUMN_EMAIL));
        }

        return rootView;
    }

    public long saveData() {

        ContentValues enderecoContenValues = new ContentValues();

        enderecoContenValues.put(ImobContract.EnderecoEntry.COLUMN_LOCAL, mEnderecoEditText.getText().toString());
        enderecoContenValues.put(ImobContract.EnderecoEntry.COLUMN_COMPLEMENTO, mComplementoEditText.getText().toString());
        long endereco_id = db.createEndereco(enderecoContenValues);

        ContentValues pessoaContentValues = new ContentValues();
        pessoaContentValues.put(PessoaEntry.COLUMN_IS_PESSOA_FISICA, true);
        pessoaContentValues.put(PessoaEntry.COLUMN_NOME, mNomeEditText.getText().toString());
        pessoaContentValues.put(PessoaEntry.COLUMN_CPF, mCpfEditText.getText().toString().replaceAll("\\D", ""));
        pessoaContentValues.put(PessoaEntry.COLUMN_ENDERECO_ID, endereco_id);

        if (id == -1) id = db.createPessoa(pessoaContentValues);
        else {
            db.updatePessoa(id, pessoaContentValues);
            db.deleteTelefoneOfPessoa(id);
            db.deleteEmailOfPessoa(id);
        }

        List<String> telefoneTextList = mTelefoneMultiEditView.getTextList();
        for (String telefoneString : telefoneTextList) {
            ContentValues telefoneContentValues = new ContentValues();
            telefoneContentValues.put(TelefoneEntry.COLUMN_PESSOA_ID, id);
            telefoneContentValues.put(TelefoneEntry.COLUMN_TELEFONE, telefoneString);
            db.createTelefone(telefoneContentValues);
        }

        List<String> emailTextList = mEmailMultiEditView.getTextList();
        for (String emailString : emailTextList) {
            ContentValues emailContentValues = new ContentValues();
            emailContentValues.put(EmailEntry.COLUMN_PESSOA_ID, id);
            emailContentValues.put(EmailEntry.COLUMN_EMAIL, emailString);
            db.createEmail(emailContentValues);
        }
        return id;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_AUTOCOMPLETE){
            if(resultCode == getActivity().RESULT_OK){
                mEnderecoEditText.setText(data.getStringExtra("description"));
                mEnderecoEditText.setSelection(mEnderecoEditText.getText().length());
                mBlockAutoComplete = false;

            } else if(resultCode == getActivity().RESULT_CANCELED){
                mEnderecoEditText.setText(data.getStringExtra("text"));
                mEnderecoEditText.setSelection(mEnderecoEditText.getText().length());
                mBlockAutoComplete = false;
            }

        }
    }
}
