package br.com.blackseed.blackimob.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import br.com.blackseed.blackimob.entity.Telefone;

/**
 * Created by tabocu on 11/05/16.
 */
public class TelefonesAdapter extends ArrayAdapter<Telefone> {

    public TelefonesAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
