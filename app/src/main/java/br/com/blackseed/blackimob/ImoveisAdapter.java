package br.com.blackseed.blackimob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ImoveisAdapter extends ArrayAdapter<Imovel> {
    public ImoveisAdapter(Context context, ArrayList<Imovel> imoveis) {
        super(context, 0, imoveis);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Imovel imovel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.imovel_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvApelido);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvCEP);
        // Populate the data into the template view using the data object
        tvName.setText(imovel.apelido);
        tvHome.setText(imovel.cep);
        // Return the completed view to render on screen
        return convertView;
    }
}