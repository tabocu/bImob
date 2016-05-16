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
import br.com.blackseed.blackimob.entity.Email;
import br.com.blackseed.blackimob.entity.Telefone;

/**
 * Created by tabocu on 11/05/16.
 */
public class EmailsAdapter extends ArrayAdapter<Email> {

    public EmailsAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Email email = getItem(position);

        LayoutInflater layoutInflater;
        layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(R.layout.contato_item_list, null);

        if(position == 0) {
            ImageView imageView = (ImageView) view.findViewById(R.id.contatoImageView);
            imageView.setImageResource(R.drawable.ic_email_gray_24dp);
        }

        TextView textView = (TextView) view.findViewById(R.id.contatoTextView);
        textView.setText(email.getEndereco());

        return view;
    }
}
