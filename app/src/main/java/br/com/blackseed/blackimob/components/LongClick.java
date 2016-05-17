package br.com.blackseed.blackimob.components;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

/**
 * Created by nilol_000 on 17/05/2016.
 */
public class LongClick implements View.OnLongClickListener {

    private String text;
    private Context context;

    public LongClick(Context context, String text) {
        this.context = context;
        this.text = text;
    }

    @Override
    public boolean onLongClick(View v) {
        itemMenu(text);
        return true;
    }

    private void itemMenu(final String content) {
        final CharSequence[] options = {"Copiar para área de transferência"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(content);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Imob", content);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "Texto copiado", Toast.LENGTH_SHORT);
                }
            }
        });
        builder.show();
    }
}
