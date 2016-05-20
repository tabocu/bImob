package br.com.blackseed.blackimob.cursoradapter;

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
import br.com.blackseed.blackimob.data.ImobContract.EmailEntry;

public class EmailAdapter extends CursorAdapter {

    private final int columnEmail;

    public EmailAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        columnEmail = cursor.getColumnIndexOrThrow(EmailEntry.COLUMN_EMAIL);
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
            contatoImageView.setImageResource(R.drawable.ic_email_gray_24dp);
        contatoTextView.setText(cursor.getString(columnEmail));
        contatoImageBtn.setVisibility(View.GONE);

        view.setOnLongClickListener(new CopyLongClick(context, cursor.getString(columnEmail)));
        view.setOnClickListener(new ContatoClick(cursor.getString(columnEmail), ContatoClick.EMAIL));
    }


}
