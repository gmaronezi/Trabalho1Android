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
    private Long teste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        dao = new DatabaseHandler( this );

        btEditarLista = findViewById(R.id.btEditarLista);


        registros = dao.listar();

        //ListView lvRegistros = new ListView( this );

        lvRegistros = (ListView) findViewById(R.id.lvLista);

        adapter = new Adapter( this, registros );

        lvRegistros.setAdapter( adapter );

        lvRegistros.setOnItemClickListener(this);

//        btEditarLista.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                teste = registros.getInt(registros.getColumnIndex("_id"));
//            }
//        });

//        AlertDialog.Builder alerta = new AlertDialog.Builder( this );
//        alerta.setTitle( "Registros" );
//        alerta.setView( lvRegistros );
//        alerta.setCancelable( false );
//        alerta.setNeutralButton( "Ok", null );
//        alerta.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        registros = (Cursor) adapter.getItem(position);
        int teste = registros.getInt(registros.getColumnIndex("_id"));

        i = new Intent(this, AlterarActivity.class);
         i.putExtra("id", teste);
        startActivity(i);
    }


    public void btEditarOnClick(View view) {
        teste = adapter.getItemId(lvRegistros.getPositionForView(view));
        i = new Intent(this, AlterarActivity.class);
        i.putExtra("id", teste);
        startActivity(i);
    }
}
