package pb.utfpr.edu.br.trabalho1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pb.utfpr.edu.br.trabalho1.entidade.PontosTuristicos;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context c ) {
        super( c, "bd", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE IF NOT EXISTS pontoTuristico ( _id INTEGER PRIMARY KEY, titulo TEXT, " +
                "descricao TEXT, endereco TEXT, latitude REAL, longitude REAL, estado TEXT, cidade TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE pontoTuristico" );
        onCreate( db );
    }

    public void incluir(PontosTuristicos pt){
        SQLiteDatabase bd = this.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("titulo",pt.getTitulo());
        registro.put("descricao", pt.getDescricao());
        registro.put("endereco",pt.getEndereco());
        registro.put("latitude", pt.getLatitude());
        registro.put("longitude",pt.getLongitude());

        bd.insert("pontoTuristico", null, registro);
    }

    public void alterar( PontosTuristicos pt ) {
        SQLiteDatabase bd = this.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("titulo",pt.getTitulo());
        registro.put("descricao", pt.getDescricao());
        registro.put("endereco",pt.getEndereco());
        registro.put("latitude", pt.getLatitude());
        registro.put("longitude",pt.getLongitude());
        registro.put("estado",pt.getEstado());
        registro.put("cidade",pt.getCidade());

        bd.update( "pontoTuristico", registro, "_id = " + pt.get_id(),
                null );
    }

    public void excluir( int cod ) {
        SQLiteDatabase bd = this.getWritableDatabase();

        bd.delete( "pontoTuristico", "_id = " + cod, null );
        bd.delete( "pontoTuristico", "_id = " + cod, null );
    }

    public PontosTuristicos pesquisar( int cod ) {
        SQLiteDatabase bd = this.getWritableDatabase();

        Cursor registro = bd.query( "pontoTuristico", null,
                "_id = " + cod, null, null,
                null, null );

        if ( registro.moveToNext() ) {
            PontosTuristicos pt = new PontosTuristicos();
            pt.set_id( cod );
            pt.setTitulo(registro.getString(registro.getColumnIndex("titulo")));
            pt.setDescricao(registro.getString(registro.getColumnIndex("descricao")));
            pt.setEndereco(registro.getString(registro.getColumnIndex("endereco")));
            pt.setEstado(registro.getString(registro.getColumnIndex("estado")));
            pt.setCidade(registro.getString(registro.getColumnIndex("cidade")));
            pt.setLatitude(registro.getDouble(registro.getColumnIndex("latitude")));
            pt.setLongitude(registro.getDouble(registro.getColumnIndex("longitude")));
            return pt;
        } else {
            return null;
        }
    }

    public Cursor listar() {
        SQLiteDatabase bd = this.getWritableDatabase();

        Cursor registros = bd.query( "pontoTuristico", null,
                null, null, null,
                null, null );

        return registros;
    }
}
