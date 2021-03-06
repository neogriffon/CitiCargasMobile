package com.evo.citicargasmobile.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Daniel on 23/03/2015.
 */
public class TransportadorSqlHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbCitiCargas";
    private static final int DATABASE_VERSION =1;

    public static final String TABLE_NAME = "Transportador";
    public static final String TABLE_NAME_VEICULO = "Veiculo";

    private static final String DATABASE_CREATE =
            "create table "+TABLE_NAME+" ( " +
                    "_id integer primary key," +
                    "nome text not null, " +
                    "rntrc text not null," +
                    "cpfCnpj text not null, " +
                    "tipoTransportador text, " +
                    "dataValidade text, " +
                    "dataRecadastramento text," +
                    "dataEmissao text, " +
                    "situacaoRntrc text, " +
                    "uf text, " +
                    "contatoCelular text, " +
                    "contatoEmail text, " +
                    "contatoFixo text, " +
                    "contatoFax text, " +
                    "enderecoComercial text, " +
                    "enderecoCorrespondencia text);";

    private static final String DATABASE_CREATE_VEICULO =
            "create table "+TABLE_NAME_VEICULO+" ( _id integer primary key, _idTransportador integer, placa text not null, renavam text not null," +
                    " marca text not null, anoFabricacao text, propriedade text," +
                    "FOREIGN KEY(_idTransportador) REFERENCES Transportador(_id));";

    public TransportadorSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE_VEICULO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TransportadorSqlHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Transportador");
        db.execSQL("DROP TABLE IF EXISTS Veiculo");
        onCreate(db);
    }
}
