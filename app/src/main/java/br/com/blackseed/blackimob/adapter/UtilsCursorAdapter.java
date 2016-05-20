package br.com.blackseed.blackimob.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.components.CopyLongClick;
import br.com.blackseed.blackimob.data.ImobContract;

public class UtilsCursorAdapter {

    private UtilsCursorAdapter() {
    }

    public static View getSeparadorView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.separador_item_list, null);
    }

    public static class Nome {
        private Nome() {
        }

        public static View getViewFromCursor(Context context, Cursor cursor) {
            View view = LayoutInflater.from(context).inflate(R.layout.contato_item_list, null);

            int column = cursor.getColumnIndexOrThrow(ImobContract.PessoaEntry.COLUMN_NOME);

            ImageView contatoImageView = (ImageView) view.findViewById(R.id.contatoImageView);
            TextView contatoTextView = (TextView) view.findViewById(R.id.contatoTextView);
            ImageButton contatoImageBtn = (ImageButton) view.findViewById(R.id.contatoImageBtn);

            contatoImageView.setImageResource(R.drawable.ic_person_gray_24dp);
            contatoTextView.setText(cursor.getString(column));
            contatoImageBtn.setVisibility(View.GONE);

            view.setOnLongClickListener(new CopyLongClick(context, cursor.getString(column)));

            return view;
        }
    }

    public static class RazaoSocial {
        private RazaoSocial() {
        }

        public static View getViewFromCursor(Context context, Cursor cursor) {
            View view = LayoutInflater.from(context).inflate(R.layout.contato_item_list, null);

            int column = cursor.getColumnIndexOrThrow(ImobContract.PessoaEntry.COLUMN_RAZAO_SOCIAL);

            ImageView contatoImageView = (ImageView) view.findViewById(R.id.contatoImageView);
            TextView contatoTextView = (TextView) view.findViewById(R.id.contatoTextView);
            ImageButton contatoImageBtn = (ImageButton) view.findViewById(R.id.contatoImageBtn);

            contatoImageView.setImageResource(R.drawable.ic_business_man);
            contatoTextView.setText(cursor.getString(column));
            contatoImageBtn.setVisibility(View.GONE);

            view.setOnLongClickListener(new CopyLongClick(context, cursor.getString(column)));

            return view;
        }
    }

    public static class Cpf {
        private Cpf() {
        }

        public static View getViewFromCursor(Context context, Cursor cursor) {
            View view = LayoutInflater.from(context).inflate(R.layout.contato_item_list, null);

            int column = cursor.getColumnIndexOrThrow(ImobContract.PessoaEntry.COLUMN_CPF);

            ImageView contatoImageView = (ImageView) view.findViewById(R.id.contatoImageView);
            TextView contatoTextView = (TextView) view.findViewById(R.id.contatoTextView);
            ImageButton contatoImageBtn = (ImageButton) view.findViewById(R.id.contatoImageBtn);

            contatoImageView.setImageResource(R.drawable.ic_cpf_gray_24dp);
            contatoTextView.setText(cursor.getString(column));
            contatoImageBtn.setVisibility(View.GONE);

            view.setOnLongClickListener(new CopyLongClick(context, cursor.getString(column)));

            return view;
        }
    }

    public static class Cnpj {
        private Cnpj() {
        }

        public static View getViewFromCursor(Context context, Cursor cursor) {
            View view = LayoutInflater.from(context).inflate(R.layout.contato_item_list, null);

            int column = cursor.getColumnIndexOrThrow(ImobContract.PessoaEntry.COLUMN_CNPJ);

            ImageView contatoImageView = (ImageView) view.findViewById(R.id.contatoImageView);
            TextView contatoTextView = (TextView) view.findViewById(R.id.contatoTextView);
            ImageButton contatoImageBtn = (ImageButton) view.findViewById(R.id.contatoImageBtn);

            contatoImageView.setImageResource(R.drawable.ic_razao_social_black_24dp);
            contatoTextView.setText(cursor.getString(column));
            contatoImageBtn.setVisibility(View.GONE);

            view.setOnLongClickListener(new CopyLongClick(context, cursor.getString(column)));

            return view;
        }
    }
}
