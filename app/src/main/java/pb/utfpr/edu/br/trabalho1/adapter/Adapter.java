package pb.utfpr.edu.br.trabalho1.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        return null;
    }
}
