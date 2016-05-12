package br.com.blackseed.blackimob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.entity.Pessoa;

public class PessoasAdapter extends ArrayAdapter<Pessoa> {

    public PessoasAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Pessoa pessoa = getItem(position);

        if (pessoa.isPessoaFisica())
            view = getPessoaFisicaView((Pessoa.Fisica) pessoa, view, parent);
        else
            view = getPessoaJuridicaView((Pessoa.Juridica) pessoa, view, parent);

        return view;
    }

    private View getPessoaFisicaView(Pessoa.Fisica pessoa, View view, ViewGroup parent) {


        LayoutInflater layoutInflater;
        layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(R.layout.pessoa_fisica_list_item, null);


        TextView idTextView = (TextView) view.findViewById(R.id.idTextView);
        idTextView.setText(String.valueOf(pessoa.getId()));
        TextView nomeTextView = (TextView) view.findViewById(R.id.nomeTextView);
        nomeTextView.setText(pessoa.getNome());
        TextView cpfTextView = (TextView) view.findViewById(R.id.cpfTextView);
        cpfTextView.setText(pessoa.getCpf()
                .replaceAll("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1.$2.$3-$4"));

        return view;
    }

    private View getPessoaJuridicaView(Pessoa.Juridica pessoa, View view, ViewGroup parent) {


        LayoutInflater layoutInflater;
        layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(R.layout.pessoa_juridica_list_item, null);


        TextView idTextView = (TextView) view.findViewById(R.id.idTextView);
        idTextView.setText(String.valueOf(pessoa.getId()));
        TextView nomeFantasiaTextView = (TextView) view.findViewById(R.id.nomeFantasiaTextView);
        nomeFantasiaTextView.setText(pessoa.getNomeFantasia());
        TextView razaoSocialTextView = (TextView) view.findViewById(R.id.razaoSocialTextView);
        razaoSocialTextView.setText(pessoa.getRazaoSocial());
        TextView cnpjTextView = (TextView) view.findViewById(R.id.cnpjTextView);
        cnpjTextView.setText(pessoa.getCnpj()
                .replaceAll("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})", "$1.$2.$3/$4-$5"));

        return view;
    }
}
