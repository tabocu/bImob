package br.com.blackseed.blackimob;


import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.blackseed.blackimob.adapter.ImoveisAdapter;
import br.com.blackseed.blackimob.data.ImobDb;
import br.com.blackseed.blackimob.entity.Imovel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImoveisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImoveisFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImobDb db;
    private ImoveisAdapter adapter;
    private List<Imovel> imovelList;



    public ImoveisFragment() {
        // Required empty public constructor
    }

    public static ImoveisFragment newInstance(String param1, String param2) {
        ImoveisFragment fragment = new ImoveisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = new ImobDb(getContext());
        adapter = new ImoveisAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_imoveis, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        imovelList = db.readAllImovel();
        adapter.addAll(imovelList);

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Imovel imovel = (Imovel) parent.getItemAtPosition(position);
                itemMenu(imovel);
                return true;
            }
        });
        return rootView;
    }

    private void itemMenu(final Imovel imovel) {
        final CharSequence[] options = {"Koalar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Koala")) {
                    db.deleteImovel(imovel);
                    adapter.clear();
                    adapter.addAll(db.readAllImovel());
                }
            }
        });
        builder.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
