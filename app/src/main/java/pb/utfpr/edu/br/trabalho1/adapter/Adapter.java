package pb.utfpr.edu.br.trabalho1.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

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
        ImageView ivFoto =  (ImageView) v.findViewById(R.id.ivFotoListar);
        ImageButton btEditarLista = v.findViewById( R.id.btEditarLista );

        registros.moveToPosition( position );

        final int cod = registros.getInt( registros.getColumnIndex( "_id" ) );
        String titulo = registros.getString( registros.getColumnIndex( "titulo" ) );
        String descricao = registros.getString( registros.getColumnIndex( "descricao" ) );
        String endereco = registros.getString( registros.getColumnIndex( "endereco" ) );
        String foto = registros.getString(registros.getColumnIndex("imagem"));

        tvTituloLista.setText( titulo );
        tvDescricaoLista.setText( descricao );
        tvEndereco.setText( endereco );

        if(foto != null){
            Bitmap bitmap = convertToBitmap(foto);
            ivFoto.setImageBitmap(bitmap);
        }

        return v;
    }

    public static Bitmap convertToBitmap(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
