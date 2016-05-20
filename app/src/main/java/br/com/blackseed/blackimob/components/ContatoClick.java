package br.com.blackseed.blackimob.components;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

public class ContatoClick implements AdapterView.OnClickListener {

    public static final int DIAL = 0;
    public static final int SMS = 1;
    public static final int EMAIL = 2;

    private String param;
    private int type;

    public ContatoClick(String param, int type) {
        this.param = param;
        this.type = type;
    }

    @Override
    public void onClick(View v) {
        switch (type) {
            case DIAL:
                dialIntent(v.getContext());
                break;
            case SMS:
                smsIntent(v.getContext());
                break;
            case EMAIL:
                emailIntent(v.getContext());
                break;
        }
    }

    private void dialIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + param));
        context.startActivity(intent);
    }

    private void smsIntent(Context context) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + param));
        sendIntent.putExtra("sms_body", "");
        context.startActivity(sendIntent);
    }

    private void emailIntent(Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + param));
        context.startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
    }
}
