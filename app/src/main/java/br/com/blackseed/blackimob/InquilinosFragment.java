package br.com.blackseed.blackimob;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.blackseed.blackimob.adapter.PessoasAdapter;
import br.com.blackseed.blackimob.data.ImobDb;
import br.com.blackseed.blackimob.entity.Pessoa;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InquilinosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InquilinosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImobDb db;
    private PessoasAdapter adapter;
    private List<Pessoa> pessoaList;


    public InquilinosFragment() {

    }

    public static InquilinosFragment newInstance(String param1, String param2) {
        InquilinosFragment fragment = new InquilinosFragment();
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
        adapter = new PessoasAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_inquilinos, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.itensListView);
        listView.setAdapter(adapter);
        pessoaList = db.readAllPessoa();
        adapter.addAll(pessoaList);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Pessoa pessoa = (Pessoa) parent.getItemAtPosition(position);
                itemMenu(pessoa);
                return true;
            }
        });

        return rootView;
    }

    private void itemMenu(final Pessoa pessoa) {
        final CharSequence[] options = {"Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Delete")) {
                    db.deletePessoa(pessoa);
                    updateList();
                }
            }
        });
        builder.show();
    }

    public void updateList() {
        adapter.clear();
        adapter.addAll(db.readAllPessoa());
    }


}
