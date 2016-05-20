package br.com.blackseed.blackimob.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.components.ContatoClick;
import br.com.blackseed.blackimob.components.CopyLongClick;
import br.com.blackseed.blackimob.data.ImobContract.TelefoneEntry;

public class TelefoneAdapter extends CursorAdapter {

    private final int columnTelefone;

    public TelefoneAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        columnTelefone = cursor.getColumnIndexOrThrow(TelefoneEntry.COLUMN_TELEFONE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.contato_item_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView contatoImageView = (ImageView) view.findViewById(R.id.contatoImageView);
        TextView contatoTextView = (TextView) view.findViewById(R.id.contatoTextView);
        ImageButton contatoImageBtn = (ImageButton) view.findViewById(R.id.contatoImageBtn);

        if (cursor.isFirst())
            contatoImageView.setImageResource(R.drawable.ic_call_gray_24dp);
        contatoTextView.setText(cursor.getString(columnTelefone));
        contatoImageBtn.setImageResource(R.drawable.ic_sms_chat_black_24dp);
        contatoImageBtn.setOnClickListener(new ContatoClick(cursor.getString(columnTelefone), ContatoClick.SMS));

        view.setOnLongClickListener(new CopyLongClick(context, cursor.getString(columnTelefone)));
        view.setOnClickListener(new ContatoClick(cursor.getString(columnTelefone), ContatoClick.DIAL));
    }


}
