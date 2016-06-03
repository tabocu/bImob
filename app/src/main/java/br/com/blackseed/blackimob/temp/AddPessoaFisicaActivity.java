package br.com.blackseed.blackimob.temp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import br.com.blackseed.blackimob.PlaceActivity;
import br.com.blackseed.blackimob.R;
import br.com.blackseed.blackimob.components.MultiEditView;
import br.com.blackseed.blackimob.data.ImobContract;
import br.com.blackseed.blackimob.data.ImobDb;
import br.com.blackseed.blackimob.utils.MaskTextWatcher;
import br.com.blackseed.blackimob.utils.Utils;

public class AddPessoaFisicaActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final int REQUEST_CODE_CAMERA_PHOTO = 2;
    private static final int REQUEST_CODE_GALLERY_PHOTO = 3;

    long id = -1;
    private ImobDb db;
    private EditText mNomeEditText;
    private EditText mCpfEditText;
    private MultiEditView mTelefoneMultiEditView;
    private MultiEditView mEmailMultiEditView;
    private EditText mEnderecoEditText;
    private EditText mComplementoEditText;
    private ImageView mPhotoImageView;

    private String mPlaceId;
    private Double mLatitude;
    private Double mLongitude;

    private Bitmap mBitmap;

    private boolean mBlockAutoComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pessoa_fisica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializa o banco de dados
        db = new ImobDb(this);

        // Obtem referencias dos componentes do layout
        mNomeEditText = (EditText) findViewById(R.id.nomeEditText);
        mCpfEditText = (EditText) findViewById(R.id.cpfEditText);
        mTelefoneMultiEditView = (MultiEditView) findViewById(R.id.telefoneMultiEditView);
        mEmailMultiEditView = (MultiEditView) findViewById(R.id.emailMultiEditView);
        mEnderecoEditText = (EditText) findViewById(R.id.enderecoEditText);
        mComplementoEditText = (EditText) findViewById(R.id.complementoEditText);
        mPhotoImageView = (ImageView) findViewById(R.id.photoImageView);




        // Configura o campo de Cpf com mascara e tipo de entrada
        mCpfEditText.addTextChangedListener(new MaskTextWatcher(MaskTextWatcher.Mask.CPF));
        mCpfEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if (hasFocus && editText.getText().toString().isEmpty()) {
                    editText.setText("0");
                } else if (!hasFocus && editText.getText().toString().equals("000.000.000-00")) {
                    editText.setText("");
                }
            }
        });
        mCpfEditText.setRawInputType(InputType.TYPE_CLASS_NUMBER);

        mEnderecoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mBlockAutoComplete) return;
                if (s.length() > 2) {
                    mBlockAutoComplete = true;
                    Intent intent = new Intent(AddPessoaFisicaActivity.this, PlaceActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("text", s.toString());
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
                }
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Obtem o id da pessoa
            id = bundle.getLong("id");
            // Obtem os cursores
            Cursor pessoaCursor = db.fetchPessoa(id);
            Cursor telefoneCursor = db.fetchTelefoneOfPessoa(id);
            Cursor emailCursor = db.fetchEmailOfPessoa(id);

            // Obtem colunas do cursor
            int columnNome = pessoaCursor.getColumnIndexOrThrow(ImobContract.PessoaEntry.COLUMN_NOME);
            int columnCpf = pessoaCursor.getColumnIndexOrThrow(ImobContract.PessoaEntry.COLUMN_CPF);
            // Preenche os campos de dados
            mNomeEditText.setText(pessoaCursor.getString(columnNome));
            mCpfEditText.setText(pessoaCursor.getString(columnCpf));
            // Preenche os campos de contatos
            mTelefoneMultiEditView.setTextList(ImobDb.cursorToStringList(telefoneCursor, ImobContract.TelefoneEntry.COLUMN_TELEFONE));
            mEmailMultiEditView.setTextList(ImobDb.cursorToStringList(emailCursor, ImobContract.EmailEntry.COLUMN_EMAIL));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_apply) {
            saveData();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == this.RESULT_OK) {
                mEnderecoEditText.setText(data.getStringExtra("description"));
                mEnderecoEditText.setSelection(mEnderecoEditText.getText().length());
                mLatitude = data.getDoubleExtra("latitude", 0);
                mLongitude = data.getDoubleExtra("longitude", 0);
                mPlaceId = data.getStringExtra("id");

                Toast.makeText(this, mEnderecoEditText.getText(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, mLatitude.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, mLongitude.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, mPlaceId, Toast.LENGTH_SHORT).show();

                mBlockAutoComplete = false;

            } else if (resultCode == this.RESULT_CANCELED) {
                mEnderecoEditText.setText(data.getStringExtra("text"));
                mEnderecoEditText.setSelection(mEnderecoEditText.getText().length());
                mBlockAutoComplete = false;
            }
        } else if (requestCode == REQUEST_CODE_CAMERA_PHOTO) {
            File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
            try {
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                mBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                if (mBitmap != null) {
                    mPhotoImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    mPhotoImageView.setImageBitmap(Utils.getResizedBitmap(mBitmap, 500));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_CODE_GALLERY_PHOTO) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mBitmap != null) {
            mPhotoImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mPhotoImageView.setImageBitmap(Utils.getResizedBitmap(mBitmap, 500));
        }
    }

    public long saveData() {

        ContentValues enderecoContenValues = new ContentValues();

        enderecoContenValues.put(ImobContract.EnderecoEntry.COLUMN_PLACE_ID, mPlaceId);
        enderecoContenValues.put(ImobContract.EnderecoEntry.COLUMN_LATITUDE, mLatitude);
        enderecoContenValues.put(ImobContract.EnderecoEntry.COLUMN_LONGITUDE, mLongitude);
        enderecoContenValues.put(ImobContract.EnderecoEntry.COLUMN_LOCAL, mEnderecoEditText.getText().toString());
        enderecoContenValues.put(ImobContract.EnderecoEntry.COLUMN_COMPLEMENTO, mComplementoEditText.getText().toString());
        long endereco_id = db.createEndereco(enderecoContenValues);

        ContentValues pessoaContentValues = new ContentValues();
        pessoaContentValues.put(ImobContract.PessoaEntry.COLUMN_IS_PESSOA_FISICA, true);
        pessoaContentValues.put(ImobContract.PessoaEntry.COLUMN_NOME, mNomeEditText.getText().toString());
        pessoaContentValues.put(ImobContract.PessoaEntry.COLUMN_CPF, mCpfEditText.getText().toString().replaceAll("\\D", ""));
        pessoaContentValues.put(ImobContract.PessoaEntry.COLUMN_ENDERECO_ID, endereco_id);

        if (id == -1) id = db.createPessoa(pessoaContentValues);
        else {
            db.updatePessoa(id, pessoaContentValues);
            db.deleteTelefoneOfPessoa(id);
            db.deleteEmailOfPessoa(id);
        }

        List<String> telefoneTextList = mTelefoneMultiEditView.getTextList();
        for (String telefoneString : telefoneTextList) {
            ContentValues telefoneContentValues = new ContentValues();
            telefoneContentValues.put(ImobContract.TelefoneEntry.COLUMN_PESSOA_ID, id);
            telefoneContentValues.put(ImobContract.TelefoneEntry.COLUMN_TELEFONE, telefoneString);
            db.createTelefone(telefoneContentValues);
        }

        List<String> emailTextList = mEmailMultiEditView.getTextList();
        for (String emailString : emailTextList) {
            ContentValues emailContentValues = new ContentValues();
            emailContentValues.put(ImobContract.EmailEntry.COLUMN_PESSOA_ID, id);
            emailContentValues.put(ImobContract.EmailEntry.COLUMN_EMAIL, emailString);
            db.createEmail(emailContentValues);
        }
        return id;
    }

    private void selectImage() {
        final CharSequence[] options = {"Camera", "Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolher foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CODE_CAMERA_PHOTO);
                } else if (item == 1) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE_GALLERY_PHOTO);
                }
            }
        });
        builder.show();
    }
}
