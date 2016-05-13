package br.com.blackseed.blackimob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.entity.Imovel;


public class ImoveisAdapter extends ArrayAdapter<Imovel> {

    public ImoveisAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Imovel imovel = getItem(position);

        LayoutInflater layoutInflater;
        layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(R.layout.imovel_list_item,null);

        TextView idTextView = (TextView) view.findViewById(R.id.idTextView);
        idTextView.setText(String.valueOf(imovel.getId()));

        TextView apelidoTextView = (TextView) view.findViewById(R.id.apelidoTextView);
        apelidoTextView.setText(imovel.getApelido());

        TextView cepTextView = (TextView) view.findViewById(R.id.cepTextView);
        cepTextView.setText(imovel.getCep()
                .replaceAll("([0-9]{2})([0-9]{3})([0-9]{3})", "$1.$2-$3"));

        return view;
    }
}
