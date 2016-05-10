package br.com.blackseed.blackimob;


import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;


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


    public ImoveisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImoveisFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_imoveis, container, false);

        // Construct the data source

        // Create the adapter to convert the array to views
        ImoveisAdapter adapter = new ImoveisAdapter(getActivity(), Dados.imoveis);
        // Attach the adapter to a ListView
//        ListView listView = (ListView) rootView.findViewById(R.id.lvItens);
//        listView.setAdapter(adapter);
        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        Imovel testImovel1 = new Imovel("NOME","123456789");
        Imovel testImovel2 = new Imovel("NOME","123456789");
        Imovel testImovel3 = new Imovel("NOME","123456789");
        Imovel testImovel4 = new Imovel("NOME","123456789");
        Imovel testImovel5 = new Imovel("NOME","123456789");
        Imovel testImovel6 = new Imovel("NOME","123456789");
        Imovel testImovel7 = new Imovel("NOME","123456789");
        Imovel testImovel8 = new Imovel("NOME","123456789");
        Imovel testImovel9 = new Imovel("NOME","123456789");
        Imovel testImovel10 = new Imovel("NOME","123456789");

        adapter.add(testImovel1);
        adapter.add(testImovel2);
        adapter.add(testImovel3);
        adapter.add(testImovel4);
        adapter.add(testImovel5);
        adapter.add(testImovel6);
        adapter.add(testImovel7);
        adapter.add(testImovel8);
        adapter.add(testImovel9);
        adapter.add(testImovel10);

        return rootView;
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
