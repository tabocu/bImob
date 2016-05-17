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
import br.com.blackseed.blackimob.entity.Email;
import br.com.blackseed.blackimob.entity.Telefone;
import br.com.blackseed.blackimob.utils.MaskTextWatcher;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPessoaJuridicaFragment extends Fragment {

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
        View rootView = inflater.inflate(R.layout.fragment_add_pessoa_juridica, container, false);

        mNomeFantasiaEditText = (EditText) rootView.findViewById(R.id.nomeFantasiaEditText);
        mRazaoSocialEditText = (EditText) rootView.findViewById(R.id.razaoSocialEditText);
        mCnpjEditText = (EditText) rootView.findViewById(R.id.cnpjEditText);
        mTelefoneMultiEditView = (MultiEditView) rootView.findViewById(R.id.telefoneMultiEditView);
        mEmailMultiEditView = (MultiEditView) rootView.findViewById(R.id.emailMultiEditView);
        mEnderecoEditView = (AdressEditView) rootView.findViewById(R.id.enderecoEditView);

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

        return rootView;
    }

    public String getNomeFantasia() {return mNomeFantasiaEditText.getText().toString();}

    public String getRazaoSocial() {return mRazaoSocialEditText.getText().toString();}

    public String getCnpj() {
        return mCnpjEditText.getText().toString().replaceAll("\\D", "");
    }

    public List<Telefone> getTelefones() {
        List<EditText> editText = mTelefoneMultiEditView.getEditTextList();

        List<Telefone> telefones = new ArrayList<>();

        for (int i = 0; i < editText.size(); i++) {
            if (!editText.get(i).getText().toString().isEmpty()) {
                Telefone telefone = new Telefone();
                telefone.setNumero(editText.get(i).getText().toString());
                telefones.add(telefone);
            }
        }
        return telefones;
    }

    public List<Email> getEmails() {
        List<EditText> editText = mEmailMultiEditView.getEditTextList();

        List<Email> emails = new ArrayList<>();

        for (int i = 0; i < editText.size(); i++) {
            if (!editText.get(i).getText().toString().isEmpty()) {
                Email email = new Email();
                email.setEndereco(editText.get(i).getText().toString());
                emails.add(email);
            }
        }
        return emails;
    }
}
