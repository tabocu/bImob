package br.com.blackseed.blackimob.detail;

import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.adapter.EmailAdapter;
import br.com.blackseed.blackimob.adapter.TelefoneAdapter;
import br.com.blackseed.blackimob.adapter.UtilsCursorAdapter;
import br.com.blackseed.blackimob.data.ImobContract.PessoaEntry;

public class DetailPessoaFisicaActivity extends DetailPessoaActivity implements OnMapReadyCallback {

    private LinearLayout mDadosLinearLayout;
    private LinearLayout mContatosLinearLayout;
    private LinearLayout mLocalLinearLayout;
    private LinearLayout mMapaLinearLayout;
    private boolean favorito;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pessoa_fisica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDadosLinearLayout = (LinearLayout) findViewById(R.id.dadosLinearLayout);
        mContatosLinearLayout = (LinearLayout) findViewById(R.id.contatosLinearLayout);
        mLocalLinearLayout = (LinearLayout) findViewById(R.id.localLinearLayout);

        Cursor cursorPessoa = getDB().fetchPessoa(getId());

        //Preenche estrela de favorito
        favorito = cursorPessoa.getInt(
                cursorPessoa.getColumnIndexOrThrow(
                        PessoaEntry.COLUMN_IS_FAVORITO)) == 1;

        //Preenche dados principais
        mDadosLinearLayout.addView(UtilsCursorAdapter.Nome.getViewFromCursor(this, cursorPessoa));
        mDadosLinearLayout.addView(UtilsCursorAdapter.getSeparadorView(this));
        mDadosLinearLayout.addView(UtilsCursorAdapter.Cpf.getViewFromCursor(this, cursorPessoa));
        mDadosLinearLayout.addView(UtilsCursorAdapter.getSeparadorView(this));

        //Preenche Contatos
        //Telefone
        Cursor cursorTelefone = getDB().fetchTelefoneOfPessoa(getId());
        TelefoneAdapter telefoneAdapter = new TelefoneAdapter(this, cursorTelefone);
        for (int i = 0; i < telefoneAdapter.getCount(); i++)
            mContatosLinearLayout.addView(telefoneAdapter.getView(i, null, null));
        mContatosLinearLayout.addView(UtilsCursorAdapter.getSeparadorView(this));
        //E-mail
        Cursor cursorEmail = getDB().fetchEmailOfPessoa(getId());
        EmailAdapter emailAdapter = new EmailAdapter(this, cursorEmail);
        for (int i = 0; i < emailAdapter.getCount(); i++)
            mContatosLinearLayout.addView(emailAdapter.getView(i, null, null));

        //Preenche endereco
        Cursor cursorEndereco = getDB().fetchEndereco(cursorPessoa.getLong(cursorPessoa.getColumnIndexOrThrow(PessoaEntry.COLUMN_ENDERECO_ID)));
        if(cursorEndereco != null) {
            mLocalLinearLayout.addView(UtilsCursorAdapter.Local.getViewFromCursor(this, cursorEndereco));
            mLocalLinearLayout.addView(UtilsCursorAdapter.Complemento.getViewFromCursor(this, cursorEndereco));
        } else
            mLocalLinearLayout.addView(UtilsCursorAdapter.noInfo.getViewFromCursor(this));

        MapFragment mMapFragment = MapFragment.newInstance();
        mMapFragment.getMapAsync(this);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapaLinearLayout, mMapFragment);
        fragmentTransaction.commit();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(cursorPessoa.getString(cursorPessoa.getColumnIndexOrThrow(PessoaEntry.COLUMN_NOME)));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.action_favorite).setChecked(favorito);

        if (favorito)
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_24dp);
        else
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_not_24dp);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}
