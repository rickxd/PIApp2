package br.g3.piapp2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AlunoActivity extends AppCompatActivity {
    private MateriaDAO materiaDAO;
    private ArrayList<Materia> lista;
    private ListView aulasListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        materiaDAO = new MateriaDAO(this);
        aulasListView = findViewById(R.id.aulasListView);
        lista = materiaDAO.busca();
        MateriaArrayAdapter adapter = new MateriaArrayAdapter(this, lista);
        aulasListView.setAdapter(adapter);
        aulasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Materia materiaSelecionada = lista.get(position);
                Intent intent = new Intent (AlunoActivity.this, AulaActivity.class );
                intent.putExtra("aula_escolhida", materiaSelecionada);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static ArrayList<String> gerarLista(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Projeto Integrado : 19h10 - 21h00");
        return list;
    }

}
