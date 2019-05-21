package br.com.alex.lembrete.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.alex.lembrete.R;
import br.com.alex.lembrete.service.ServiceLembrete;

public class NovoLembreteActivity extends AppCompatActivity {
    EditText editTextNovaTarefa;
    SQLiteDatabase bancoDados;
    ServiceLembrete serviceLembrete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_lembrete);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTextNovaTarefa = (EditText) findViewById(R.id.editTextNovoNome);
        bancoDados = openOrCreateDatabase("bdLembretes", MODE_PRIVATE, null);
        serviceLembrete = new ServiceLembrete(bancoDados);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextNovaTarefa.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
                }else {
                    if(serviceLembrete.addLembrete(editTextNovaTarefa.getText().toString(),getApplicationContext())){
                        Toast.makeText(getApplicationContext(),"Lembrete salvo", Toast.LENGTH_SHORT).show();
                        editTextNovaTarefa.getText().clear();
                    }else{
                        Toast.makeText(getApplicationContext(),"Erro ao salvar o lembrete", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
