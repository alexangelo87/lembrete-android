package br.com.alex.lembrete.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import br.com.alex.lembrete.R;
import br.com.alex.lembrete.RecyclerItemClickListener;
import br.com.alex.lembrete.adapter.AdapterLembrete;
import br.com.alex.lembrete.model.Lembrete;
import br.com.alex.lembrete.service.ServiceLembrete;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ServiceLembrete serviceLembrete;
    private AdapterLembrete adapterLembrete;
    private SQLiteDatabase bancoDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NovoLembreteActivity.class);
                startActivity(intent);
            }
        });

        //banco de dados
        bancoDados = openOrCreateDatabase("bdLembretes",MODE_PRIVATE,null);
        //database(bancoDados);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        serviceLembrete = new ServiceLembrete(bancoDados);
        adapterLembrete = new AdapterLembrete(serviceLembrete.getLembretes());
        recyclerView.setAdapter(adapterLembrete);



        //eventos de click
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        List<Lembrete> lembretes = serviceLembrete.getLembretes();

                        Intent intent = new Intent(getApplicationContext(),DetalhesActivity.class);
                        intent.putExtra("lembrete",lembretes.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        serviceLembrete = new ServiceLembrete(bancoDados);
        adapterLembrete = new AdapterLembrete(serviceLembrete.getLembretes());
        recyclerView.setAdapter(adapterLembrete);
    }

    @Override
    protected  void onRestart(){
        super.onRestart();
        adapterLembrete.notifyDataSetChanged();

    }

    protected void database(SQLiteDatabase bancoDados){
        try{

            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS lembretes(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR,status VARCHAR)");
            //bancoDados.execSQL("INSERT INTO lembretes(tarefa,status) VALUES('Tarefa 1','Concluído')");
            //bancoDados.execSQL("INSERT INTO lembretes(tarefa,status) VALUES('Tarefa 2','Em andamento')");

            Cursor cursor = bancoDados.rawQuery("SELECT id,tarefa,status FROM lembretes",null);

            int indiceTarefa = cursor.getColumnIndex("tarefa");
            int indiceStatus = cursor.getColumnIndex("status");
            int indiceId = cursor.getColumnIndex("id");

            cursor.moveToFirst();
            while(cursor !=null){
                Log.i("RESULTADO: ", cursor.getString(indiceId));
                Log.i("RESULTADO: ", cursor.getString(indiceTarefa));
                Log.i("RESULTADO: ", cursor.getString(indiceStatus));
                cursor.moveToNext();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
