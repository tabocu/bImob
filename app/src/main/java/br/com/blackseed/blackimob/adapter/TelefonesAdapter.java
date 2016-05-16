package br.com.blackseed.blackimob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.entity.Telefone;

/**
 * Created by tabocu on 11/05/16.
 */
public class TelefonesAdapter extends ArrayAdapter<Telefone> {

    public TelefonesAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Telefone telefone = getItem(position);

        LayoutInflater layoutInflater;
        layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(R.layout.contato_item_list, null);

        if(position == 0) {
            ImageView imageView = (ImageView) view.findViewById(R.id.contatoImageView);
            imageView.setImageResource(R.drawable.ic_call_gray_24dp);
        }

        TextView textView = (TextView) view.findViewById(R.id.contatoTextView);
        textView.setText(telefone.getNumero());

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.contatoImageBtn);
        imageButton.setImageResource(R.drawable.ic_sms_chat_black_24dp);


        return view;
    }
}
