package br.com.blackseed.blackimob;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.blackseed.blackimob.components.AdressEditView;
import br.com.blackseed.blackimob.components.MultiEditView;
import br.com.blackseed.blackimob.data.ImobContract;
import br.com.blackseed.blackimob.data.ImobDb;
import br.com.blackseed.blackimob.entity.Email;
import br.com.blackseed.blackimob.entity.Pessoa;
import br.com.blackseed.blackimob.entity.Telefone;
import br.com.blackseed.blackimob.utils.MaskTextWatcher;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPessoaJuridicaFragment extends Fragment {

    private ImobDb db;

    private long id = -1;
    private EditText mNomeFantasiaEditText;
    private EditText mRazaoSocialEditText;
    private EditText mCnpjEditText;
    private MultiEditView mTelefoneMultiEditView;
    private MultiEditView mEmailMultiEditView;
    private AdressEditView mEnderecoEditView;


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
        mNomeFantasiaEditText = (EditText) rootView.findViewById(R.id.nomeFantasiaEditText);
        mRazaoSocialEditText = (EditText) rootView.findViewById(R.id.razaoSocialEditText);
        mCnpjEditText = (EditText) rootView.findViewById(R.id.cnpjEditText);
        mTelefoneMultiEditView = (MultiEditView) rootView.findViewById(R.id.telefoneMultiEditView);
        mEmailMultiEditView = (MultiEditView) rootView.findViewById(R.id.emailMultiEditView);
        mEnderecoEditView = (AdressEditView) rootView.findViewById(R.id.enderecoEditView);

        // Configura o campo de Cnpj com mascara e tipo de entrada
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

        // Pega o id da pessoa (no caso de edição)
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Obtem o id da pessoa
            id = bundle.getLong("id");
            // Obtem o objeto pessoa do banco de dados
            Pessoa.Juridica pessoa = (Pessoa.Juridica) db.readPessoa(id);
            // Preenche o campo de nome fantasia
            mNomeFantasiaEditText.setText(pessoa.getNome());
            // Preenche o campo de razao social
            mRazaoSocialEditText.setText(pessoa.getRazaoSocial());
            // Preenche o campo de cnpj
            mCnpjEditText.setText(pessoa.getCnpj());
            // Obtem os telefones da pessoa no banco de dados
            List<Telefone> telefoneList = db.readTelefone(
                    ImobContract.TelefoneEntry.COLUMN_PESSOA_ID, pessoa.getId());
            // Extrai uma lista de telefones em String
            List<String> telefoneStringList = new ArrayList<>();
            for (Telefone telefone : telefoneList)
                telefoneStringList.add(telefone.getNumero());
            // Preenche o campo de telefone
            mTelefoneMultiEditView.setTextList(telefoneStringList);
            // Obtem os emails da pessoa no banco de dados
            List<Email> emailList = db.readEmail(
                    ImobContract.EmailEntry.COLUMN_PESSOA_ID, pessoa.getId());
            // Extrai uma lista de telefones em String
            List<String> emailStringList = new ArrayList<>();
            for (Email email : emailList)
                emailStringList.add(email.getEndereco());
            // Preenche o campo de telefone
            mEmailMultiEditView.setTextList(emailStringList);
        } else {
            id = -1;
        }

        return rootView;
    }


    public long saveData() {

        Pessoa.Juridica pessoa = new Pessoa.Juridica();

        // Preenche o objeto pessoa
        pessoa.setId(id);
        pessoa.setNome(mNomeFantasiaEditText.getText().toString());
        pessoa.setRazaoSocial(mRazaoSocialEditText.getText().toString());
        pessoa.setCnpj(mCnpjEditText.getText().toString().replaceAll("\\D", ""));

        List<Telefone> telefoneList = new ArrayList<>();
        List<String> telefoneTextList = mTelefoneMultiEditView.getTextList();
        for (String telefoneString : telefoneTextList) {
            Telefone telefone = new Telefone();
            telefone.setNumero(telefoneString);
            telefoneList.add(telefone);
        }

        List<Email> emailList = new ArrayList<>();
        List<String> emailTextList = mEmailMultiEditView.getTextList();
        for (String emailString : emailTextList) {
            Email email = new Email();
            email.setEndereco(emailString);
            emailList.add(email);
        }

        // Cria se não existir
        if (id == -1) {
            db.createPessoa(pessoa);
        }
        // Atualiza se existir
        else {
            db.updatePessoa(pessoa);
            db.deleteTelefone(ImobContract.TelefoneEntry.COLUMN_PESSOA_ID, pessoa.getId());
            db.deleteEmail(ImobContract.EmailEntry.COLUMN_PESSOA_ID, pessoa.getId());
        }

        db.createTelefone(ImobContract.TelefoneEntry.COLUMN_PESSOA_ID, pessoa.getId(), telefoneList);
        db.createEmail(ImobContract.EmailEntry.COLUMN_PESSOA_ID, pessoa.getId(), emailList);

        return id;
    }
}
