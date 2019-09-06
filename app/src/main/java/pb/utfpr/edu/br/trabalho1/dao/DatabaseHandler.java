package pb.utfpr.edu.br.trabalho1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pb.utfpr.edu.br.trabalho1.entidade.PontosTuristicos;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context c ) {
        super( c, "bd", null, 4 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE IF NOT EXISTS pontoTuristico ( _id INTEGER PRIMARY KEY, titulo TEXT, " +
                "descricao TEXT, endereco TEXT, latitude REAL, longitude REAL)");

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

        bd.update( "pontoTuristico", registro, "_id = " + pt.get_id(),
                null );
    }

    public void excluir( String cod ) {
        SQLiteDatabase bd = this.getWritableDatabase();

        bd.delete( "pontoTuristico", "_id = " + Integer.parseInt(cod), null );
    }

    public PontosTuristicos pesquisar( String cod ) {
        SQLiteDatabase bd = this.getWritableDatabase();

        Cursor registro = bd.query( "pontoTuristico", null,
                "_id = " + Integer.parseInt(cod), null, null,
                null, null );

        if ( registro.moveToNext() ) {
            PontosTuristicos pt = new PontosTuristicos();
            pt.set_id( Integer.parseInt(cod) );
            pt.setTitulo(registro.getString(registro.getColumnIndex("titulo")));
            pt.setDescricao(registro.getString(registro.getColumnIndex("descricao")));
            pt.setEndereco(registro.getString(registro.getColumnIndex("endereco")));
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

//    public Cursor listarWhere(int id){
//        SQLiteDatabase bd = this.getWritableDatabase();
//
//        String sql = "SELECT * FROM pontoTuristico WHERE _id = ?";
//
//        Cursor registros = bd.rawQuery(sql, new String[] {String.valueOf(id)});
//
//        return registros.po;
//    }

}
