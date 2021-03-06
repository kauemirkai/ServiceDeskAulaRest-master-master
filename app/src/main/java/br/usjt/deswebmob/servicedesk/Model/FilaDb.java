package br.usjt.deswebmob.servicedesk.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import static br.usjt.deswebmob.servicedesk.Model.FilaDbContract.FilaBanco;

/**
 * Created by arqdsis on 11/04/2018.
 */

public class FilaDb {
    private FilaDbHelper dbHelper;

    public FilaDb(Context context) {
        dbHelper = new FilaDbHelper(context);

    }

    public void inserirFila(ArrayList<Fila> filas){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(Fila fila:filas){
            values.put(FilaBanco.ID_FILA, fila.getId());
            values.put(FilaBanco.NM_FILA, fila.getNome());
            values.put(FilaBanco.NM_FIGURA, fila.getFigura());

            db.insert(FilaBanco.TABLE_NAME,null,values);

        }
        db.close();


    }

    public ArrayList<Fila> listarFilas(){
        ArrayList<Fila> filas  = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] colunas = {FilaBanco.ID_FILA, FilaBanco.NM_FILA, FilaBanco.NM_FIGURA};

        String orderBy = FilaBanco.ID_FILA;

        Cursor c ;
        c = db.query(FilaBanco.TABLE_NAME, colunas, null, null, null, null,orderBy,null);

        while (c.moveToNext()){
            Fila fila = new Fila();
            fila.setId(c.getInt(c.getColumnIndex(FilaBanco.ID_FILA)));
            fila.setNome(c.getString(c.getColumnIndex(FilaBanco.NM_FILA)));
            fila.setFigura(c.getString(c.getColumnIndex(FilaBanco.NM_FIGURA)));
            filas.add(fila);

        }

        c.close();
        db.close();

        return filas;




    }

}
