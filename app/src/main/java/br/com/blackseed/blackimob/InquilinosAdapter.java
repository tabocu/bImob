package br.com.blackseed.blackimob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InquilinosAdapter extends ArrayAdapter<Inquilino> {
    public InquilinosAdapter(Context context, ArrayList<Inquilino> inquilinos) {
        super(context, 0, inquilinos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Inquilino inquilino = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inquilino_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvNome);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvCPF);
        // Populate the data into the template view using the data object
        tvName.setText(inquilino.nome);
        tvHome.setText(inquilino.cpf);
        // Return the completed view to render on screen
        return convertView;
    }
}