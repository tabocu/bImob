package br.com.blackseed.blackimob;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class ImoveisAdapter_ extends CursorAdapter {

    public static class ViewHolder{
        public final TextView apelidoView;

        public ViewHolder (View view){
            apelidoView = (TextView) view.findViewById(R.id.tvApelido);
        }
    }


    public ImoveisAdapter_(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        int layoutId = R.layout.imovel_list_item;
        View view = LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.apelidoView.setText(null); //implementar

    }
}
