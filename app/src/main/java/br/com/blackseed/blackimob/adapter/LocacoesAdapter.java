package br.com.blackseed.blackimob.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import br.com.blackseed.blackimob.entity.Locacao;

/**
 * Created by tabocu on 11/05/16.
 */
public class LocacoesAdapter extends ArrayAdapter<Locacao> {


    public LocacoesAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;

    }
}
