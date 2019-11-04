package br.g3.piapp2;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlunoActivity extends AppCompatActivity {
    //private MateriaDAO materiaDAO;
    private ArrayList<Materia> lista;
    private ListView aulasListView;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //materiaDAO = new MateriaDAO(this);
        aulasListView = findViewById(R.id.aulasListView);
        lista = new ArrayList<>();
        MateriaArrayAdapter adapter = new MateriaArrayAdapter(this, lista);
        //aulasListView.setAdapter(adapter);
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

    public String montaUrl (String... args){
        StringBuilder sb = new StringBuilder();
        for (String s : args){
            sb.append(s);
        }
        return sb.toString();
    }

    public void obterMaterias(){
        String url = montaUrl(getString(R.string.host_address),getString(R.string.host_port),getString(R.string.endpoint_base),getString(R.string.endpoint_listar));
        requestQueue.add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONArray>(){
                            @Override
                            public void onResponse(JSONArray response){
                                lista.clear();
                                for (int i = 0; i < response.length(); i++){
                                    try {
                                        JSONObject iesimo = response.getJSONObject(i);
                                        int id = iesimo.getInt("idMateria");
                                        String nome = iesimo.getString("nome");
                                        lista.add(new Materia (id, nome));
                                    }
                                    catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //adapter.notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                )
        );
    }

}
