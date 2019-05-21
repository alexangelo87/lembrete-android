package br.com.alex.lembrete.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.alex.lembrete.model.Lembrete;


public class ServiceLembrete {
    SQLiteDatabase bancoDados;
    List<Lembrete> lista = new ArrayList<>();

    public ServiceLembrete(SQLiteDatabase bancoDados){
        this.bancoDados=bancoDados;
    }


    public List<Lembrete> getLembretes(){
        try{
            Cursor cursor = bancoDados.rawQuery("SELECT id,tarefa,status FROM lembretes",null);
            int indiceTarefa = cursor.getColumnIndex("tarefa");
            int indiceStatus = cursor.getColumnIndex("status");
            int indiceId = cursor.getColumnIndex("id");

            cursor.moveToFirst();
            while(cursor !=null){
                lista.add(new Lembrete(cursor.getString(indiceTarefa),cursor.getString(indiceStatus)));
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  lista;
    }

    public boolean addLembrete(String tarefa, Context context){
        boolean sucesso;
        try {
            bancoDados.execSQL("INSERT INTO lembretes(tarefa,status) VALUES('" + tarefa + "','Em aberto')");
            sucesso =true;
        }catch (Exception e){
            e.printStackTrace();
            sucesso = false;
        }

        return sucesso;
    }

    public void deleteLembrete(int id){

    }

    public void updateLembrete(int id){

    }
}
