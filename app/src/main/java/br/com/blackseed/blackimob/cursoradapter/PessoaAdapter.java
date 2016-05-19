package br.com.blackseed.blackimob.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.data.ImobContract.PessoaEntry;

public class PessoaAdapter extends CursorAdapter {

    private final int columnId;
    private final int columnNome;
    private final int columnRazaoSocial;
    private final int columnCpf;
    private final int columnCnpj;
    private final int columnIsPessoaFisica;

    public PessoaAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        columnId = cursor.getColumnIndexOrThrow(PessoaEntry._ID);
        columnNome = cursor.getColumnIndexOrThrow(PessoaEntry.COLUMN_NOME);
        columnRazaoSocial = cursor.getColumnIndexOrThrow(PessoaEntry.COLUMN_RAZAO_SOCIAL);
        columnCpf = cursor.getColumnIndexOrThrow(PessoaEntry.COLUMN_CPF);
        columnCnpj = cursor.getColumnIndexOrThrow(PessoaEntry.COLUMN_CNPJ);
        columnIsPessoaFisica = cursor.getColumnIndexOrThrow(PessoaEntry.COLUMN_IS_PESSOA_FISICA);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.pessoa_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView iconImageView = (ImageView) view.findViewById(R.id.iconImageView);
        TextView idTextView = (TextView) view.findViewById(R.id.idTextView);
        TextView nomeTextView = (TextView) view.findViewById(R.id.nomeTextView);
        TextView razaoSocialTextView = (TextView) view.findViewById(R.id.razaoSocialTextView);
        TextView cpfCnpjTextView = (TextView) view.findViewById(R.id.cpfCnpjTextView);

        idTextView.setText(String.valueOf(cursor.getLong(columnId)));
        nomeTextView.setText(cursor.getString(columnNome));

        if (cursor.getInt(columnIsPessoaFisica) == 1) {
            razaoSocialTextView.setVisibility(View.GONE);
            iconImageView.setImageResource(R.drawable.ic_person_gray_24dp);
            cpfCnpjTextView.setText(cursor.getString(columnCpf));
        } else {
            razaoSocialTextView.setVisibility(View.VISIBLE);
            iconImageView.setImageResource(R.drawable.ic_domain_black_24dp);
            razaoSocialTextView.setText(cursor.getString(columnRazaoSocial));
            cpfCnpjTextView.setText(cursor.getString(columnCnpj));
        }
    }
}
