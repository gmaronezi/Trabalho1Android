package pb.utfpr.edu.br.trabalho1;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import pb.utfpr.edu.br.trabalho1.adapter.Adapter;
import pb.utfpr.edu.br.trabalho1.dao.DatabaseHandler;

public class ListarActivity extends AppCompatActivity {

    private DatabaseHandler dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        dao = new DatabaseHandler( this );

        Cursor registros = dao.listar();

        //ListView lvRegistros = new ListView( this );

        ListView lvRegistros = (ListView) findViewById(R.id.lvLista);

        Adapter adapter = new Adapter( this, registros );

        lvRegistros.setAdapter( adapter );

//        AlertDialog.Builder alerta = new AlertDialog.Builder( this );
//        alerta.setTitle( "Registros" );
//        alerta.setView( lvRegistros );
//        alerta.setCancelable( false );
//        alerta.setNeutralButton( "Ok", null );
//        alerta.show();
    }
}
