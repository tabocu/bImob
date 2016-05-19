package br.com.blackseed.blackimob;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.blackseed.blackimob.cursoradapter.PessoaAdapter;
import br.com.blackseed.blackimob.data.ImobContract;
import br.com.blackseed.blackimob.data.ImobContract.PessoaEntry;
import br.com.blackseed.blackimob.data.ImobDb;
import br.com.blackseed.blackimob.detail.DetailPessoaFisicaActivity;
import br.com.blackseed.blackimob.detail.DetailPessoaJuridicaActivity;

public class InquilinosFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ImobDb db;
    private PessoaAdapter adapter;
    private ListView mPessoaListView;

    public InquilinosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new ImobDb(getContext());
        adapter = new PessoaAdapter(getContext(), db.fetchAllPessoa());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_inquilinos, container, false);

        mPessoaListView = (ListView) rootView.findViewById(R.id.itensListView);
        mPessoaListView.setOnItemClickListener(this);
        mPessoaListView.setAdapter(adapter);

        return rootView;
    }

    public void updateList() {
        adapter.changeCursor(db.fetchAllPessoa());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);

        int columnIsPessoaFisica = cursor.getColumnIndexOrThrow(PessoaEntry.COLUMN_IS_PESSOA_FISICA);
        int columnId = cursor.getColumnIndexOrThrow(ImobContract.PessoaEntry._ID);

        Bundle bundle = new Bundle();
        bundle.putLong("id", cursor.getLong(columnId));

        Intent intent;
        if (cursor.getInt(columnIsPessoaFisica) == 1)
            intent = new Intent(getContext(), DetailPessoaFisicaActivity.class);
        else
            intent = new Intent(getContext(), DetailPessoaJuridicaActivity.class);

        intent.putExtras(bundle);
        startActivityForResult(intent, 0, null);
    }
}
