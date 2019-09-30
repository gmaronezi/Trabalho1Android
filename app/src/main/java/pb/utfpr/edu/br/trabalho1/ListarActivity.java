package pb.utfpr.edu.br.trabalho1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import pb.utfpr.edu.br.trabalho1.adapter.Adapter;
import pb.utfpr.edu.br.trabalho1.dao.DatabaseHandler;

public class ListarActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Button btEditarLista;

    private DatabaseHandler dao;
    Adapter adapter;
    ListView lvRegistros;
    Intent i;
    Cursor registros;
    private Long idRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        dao = new DatabaseHandler( this );

        btEditarLista = findViewById(R.id.btEditarLista);

        registros = dao.listar();

        lvRegistros = findViewById(R.id.lvLista);

        adapter = new Adapter( this, registros );

        lvRegistros.setAdapter( adapter );

        lvRegistros.setOnItemClickListener(this);
    }

    @Override
    public void onResume(){
        refreshLista();
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        registros = (Cursor) adapter.getItem(position);
        int idRegistro = registros.getInt(registros.getColumnIndex("_id"));

        i = new Intent(this, AlterarActivity.class);
         i.putExtra("id", idRegistro);
        startActivity(i);
    }

    public void btEditarOnClick(View view) {
        idRegistro = adapter.getItemId(lvRegistros.getPositionForView(view));
        i = new Intent(this, AlterarActivity.class);
        i.putExtra("id", idRegistro);
        startActivity(i);
    }

    public void btVerMapaOnClick(View view) {
        idRegistro = adapter.getItemId(lvRegistros.getPositionForView(view));
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("id",idRegistro);
        startActivity(intent);
    }

    public void btExcluirOnclick(View view) {
        try{
            idRegistro = adapter.getItemId(lvRegistros.getPositionForView(view));
            dao.excluir(String.valueOf(idRegistro));
        }
        catch(Exception ex){
            Toast.makeText( this, "Erro ao excluir", Toast.LENGTH_LONG ).show();
        } finally {
            Toast.makeText( this, "Registro excluído com sucesso!!!", Toast.LENGTH_LONG ).show();

            refreshLista();
        }
    }

    public void refreshLista(){
        registros = dao.listar();
        adapter = new Adapter( this, registros );
        lvRegistros.setAdapter( adapter );
    }
}
