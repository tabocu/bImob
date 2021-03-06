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

import java.util.List;

import br.com.blackseed.blackimob.components.AdressEditView;
import br.com.blackseed.blackimob.components.MultiEditView;
import br.com.blackseed.blackimob.data.ImobContract;
import br.com.blackseed.blackimob.data.ImobDb;
import br.com.blackseed.blackimob.utils.MaskTextWatcher;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPessoaJuridicaFragment extends Fragment {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    private ImobDb db;

    private long id = -1;
    private EditText mNomeEditText;
    private EditText mRazaoSocialEditText;
    private EditText mCnpjEditText;
    private MultiEditView mTelefoneMultiEditView;
    private MultiEditView mEmailMultiEditView;
    private EditText mEnderecoEditText;
    private EditText mComplementoEditText;

    private boolean mBlockAutoComplete = false;


    public AddPessoaJuridicaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout do fragment
        View rootView = inflater.inflate(R.layout.fragment_add_pessoa_juridica, container, false);

        // Inicializa o banco de dados
        db = new ImobDb(getContext());

        // Obtem referencias dos componentes do layout
        mNomeEditText = (EditText) rootView.findViewById(R.id.nomeEditText);
        mRazaoSocialEditText = (EditText) rootView.findViewById(R.id.razaoSocialEditText);
        mCnpjEditText = (EditText) rootView.findViewById(R.id.cnpjEditText);
        mTelefoneMultiEditView = (MultiEditView) rootView.findViewById(R.id.telefoneMultiEditView);
        mEmailMultiEditView = (MultiEditView) rootView.findViewById(R.id.emailMultiEditView);
        mEnderecoEditText = (EditText) rootView.findViewById(R.id.enderecoEditText);
        mComplementoEditText = (EditText) rootView.findViewById(R.id.complementoEditText);

        // Configura o campo de cnpj com mascara e tipo de entrada
        mCnpjEditText.addTextChangedListener(new MaskTextWatcher(MaskTextWatcher.Mask.CNPJ));
        mCnpjEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if (hasFocus && editText.getText().toString().isEmpty()) {
                    editText.setText("0");
                } else if (!hasFocus && editText.getText().toString().equals("00.000.000/0000-00")) {
                    editText.setText("");
                }
            }
        });
        mCnpjEditText.setRawInputType(InputType.TYPE_CLASS_NUMBER);


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
            int columnNome = pessoaCursor.getColumnIndexOrThrow(ImobContract.PessoaEntry.COLUMN_NOME);
            int columnRazaoSocial = pessoaCursor.getColumnIndexOrThrow(ImobContract.PessoaEntry.COLUMN_RAZAO_SOCIAL);
            int columnCnpj = pessoaCursor.getColumnIndexOrThrow(ImobContract.PessoaEntry.COLUMN_CNPJ);
            // Preenche os campos de dados
            mNomeEditText.setText(pessoaCursor.getString(columnNome));
            mRazaoSocialEditText.setText(pessoaCursor.getString(columnRazaoSocial));
            mCnpjEditText.setText(pessoaCursor.getString(columnCnpj));
            // Preenche os campos de contatos
            mTelefoneMultiEditView.setTextList(ImobDb.cursorToStringList(telefoneCursor, ImobContract.TelefoneEntry.COLUMN_TELEFONE));
            mEmailMultiEditView.setTextList(ImobDb.cursorToStringList(emailCursor, ImobContract.EmailEntry.COLUMN_EMAIL));
        }

        return rootView;
    }

    public long saveData() {

        ContentValues pessoaContentValues = new ContentValues();
        pessoaContentValues.put(ImobContract.PessoaEntry.COLUMN_IS_PESSOA_FISICA, false);
        pessoaContentValues.put(ImobContract.PessoaEntry.COLUMN_NOME, mNomeEditText.getText().toString());
        pessoaContentValues.put(ImobContract.PessoaEntry.COLUMN_RAZAO_SOCIAL, mRazaoSocialEditText.getText().toString());
        pessoaContentValues.put(ImobContract.PessoaEntry.COLUMN_CNPJ, mCnpjEditText.getText().toString().replaceAll("\\D", ""));

        if (id == -1) id = db.createPessoa(pessoaContentValues);
        else {
            db.updatePessoa(id, pessoaContentValues);
            db.deleteTelefoneOfPessoa(id);
            db.deleteEmailOfPessoa(id);
        }

        List<String> telefoneTextList = mTelefoneMultiEditView.getTextList();
        for (String telefoneString : telefoneTextList) {
            ContentValues telefoneContentValues = new ContentValues();
            telefoneContentValues.put(ImobContract.TelefoneEntry.COLUMN_PESSOA_ID, id);
            telefoneContentValues.put(ImobContract.TelefoneEntry.COLUMN_TELEFONE, telefoneString);
            db.createTelefone(telefoneContentValues);
        }

        List<String> emailTextList = mEmailMultiEditView.getTextList();
        for (String emailString : emailTextList) {
            ContentValues emailContentValues = new ContentValues();
            emailContentValues.put(ImobContract.EmailEntry.COLUMN_PESSOA_ID, id);
            emailContentValues.put(ImobContract.EmailEntry.COLUMN_EMAIL, emailString);
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
