package br.g3.piapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class faltasActivity extends AppCompatActivity {
    private RecyclerView presencasRecyclerView;
    private List<Presenca> presencas;
    private Materia materia;
    private Aluno logado;
    private PresencaAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faltas);
        Intent origemIntent = getIntent();
        logado = (Aluno) origemIntent.getSerializableExtra("logado");
        materia = (Materia) origemIntent.getSerializableExtra("materia");
        presencas = logado.getPresencas();
        presencasRecyclerView = findViewById(R.id.presencasRecyclerView);
        presencas = new ArrayList<>();
        adapter = new PresencaAdapter(this, presencas);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        presencasRecyclerView.setAdapter(adapter);
        presencasRecyclerView.setLayoutManager(llm);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //obterFaltas();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }
    /*
        public void obterMaterias(){
            String url = montaUrl(getString(R.string.host_address),getString(R.string.host_port),getString(R.string.endpoint_base_materia),getString(R.string.endpoint_listar));
            requestQueue.add(
                    new JsonArrayRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONArray>(){
                                @Override
                                public void onResponse(JSONArray response){
                                    materias.clear();
                                    for (int i = 0; i < response.length(); i++){
                                        try {
                                            JSONObject iesimo = response.getJSONObject(i);
                                            int id = iesimo.getInt("idMateria");
                                            String nome = iesimo.getString("nome");
                                            materias.add(new Materia (id, nome));
                                        }
                                        catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
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
        */
}
