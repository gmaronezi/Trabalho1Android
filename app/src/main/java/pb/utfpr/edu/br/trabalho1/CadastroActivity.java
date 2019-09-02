package pb.utfpr.edu.br.trabalho1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import pb.utfpr.edu.br.trabalho1.dao.DatabaseHandler;
import pb.utfpr.edu.br.trabalho1.entidade.PontosTuristicos;

public class CadastroActivity extends AppCompatActivity {

    private EditText etTitulo;
    private EditText etDescricao;
    private EditText etEndereco;
    private EditText etLatitude;
    private EditText etLongitude;
    private EditText etCidade;
    private Spinner spEstado;

    private DatabaseHandler dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_activity);

        etTitulo = findViewById(R.id.etTitulo);
        etDescricao = findViewById(R.id.etDescricao);
        etEndereco = findViewById(R.id.etEndereco);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        etCidade = findViewById(R.id.etCidade);
        spEstado = findViewById(R.id.spEstado);

        dao = new DatabaseHandler( this );

        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CadastroActivity.this, "Item " + position + " Selecionado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void btIncluirOnClick(View view) {
        PontosTuristicos pt = new PontosTuristicos();
        pt.setTitulo( etTitulo.getText().toString() );
        pt.setDescricao( etDescricao.getText().toString() );
        pt.setLatitude(Double.parseDouble(etLatitude.getText().toString()));
        pt.setLongitude(Double.parseDouble(etLongitude.getText().toString()));
        pt.setCidade(etCidade.getText().toString());
        pt.setEstado(spEstado.getSelectedItem().toString());
        dao.incluir( pt );

        Toast.makeText( this, "Registro inserido com sucesso!!!", Toast.LENGTH_LONG ).show();
    }
}
