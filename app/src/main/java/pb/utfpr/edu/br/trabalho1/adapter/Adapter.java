package pb.utfpr.edu.br.trabalho1.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pb.utfpr.edu.br.trabalho1.AlterarActivity;
import pb.utfpr.edu.br.trabalho1.CadastroActivity;
import pb.utfpr.edu.br.trabalho1.ListarActivity;
import pb.utfpr.edu.br.trabalho1.R;

public class Adapter extends BaseAdapter {


    private Context c;
    private Cursor registros;

    public Adapter( Context c, Cursor registros ) {
        this.c = c;
        this.registros = registros;
    }

    @Override
    public int getCount() {
        return registros.getCount();
    }

    @Override
    public Object getItem(int position) {

        registros.moveToPosition( position );
        return registros;

    }

    @Override
    public long getItemId(int position) {
        registros.moveToPosition( position );
        return registros.getInt( registros.getColumnIndex( "_id" ) );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                c.getSystemService( c.LAYOUT_INFLATER_SERVICE );

        View v = inflater.inflate(R.layout.elemento_lista, null );

        TextView tvTituloLista = v.findViewById( R.id.tvTituloLista );
        TextView tvDescricaoLista = v.findViewById( R.id.tvDescricaoLista );
        TextView tvEndereco = v.findViewById( R.id.tvEnderecoLista );
        Button btEditarLista = v.findViewById( R.id.btEditarLista );

        registros.moveToPosition( position );

        final int cod = registros.getInt( registros.getColumnIndex( "_id" ) );
        String titulo = registros.getString( registros.getColumnIndex( "titulo" ) );
        String descricao = registros.getString( registros.getColumnIndex( "descricao" ) );
        String endereco = registros.getString( registros.getColumnIndex( "endereco" ) );

        tvTituloLista.setText( titulo );
        tvDescricaoLista.setText( descricao );
        tvEndereco.setText( endereco );


//        btEditarLista.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText( c,
//                        "Elemento "+ cod, Toast.LENGTH_LONG ).show();
//            }
//        });

        return v;
    }

}
