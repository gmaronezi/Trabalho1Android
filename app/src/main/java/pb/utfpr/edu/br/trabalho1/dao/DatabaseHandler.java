package pb.utfpr.edu.br.trabalho1.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context c ) {
        super( c, "bd", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE IF NOT EXISTS pontoTuristico ( _id INTEGER PRIMARY KEY, titulo TEXT, " +
                "descricao TEXT, endereco TEXT, latitude REAL, longitude REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE lancamento" );
        onCreate( db );
    }
}
