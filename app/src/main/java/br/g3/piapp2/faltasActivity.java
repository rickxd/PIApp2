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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Result;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class faltasActivity extends AppCompatActivity {
    private RecyclerView presencasRecyclerView;
    private List<Presenca> presencas;
    private Materia materia;
    private Aluno aLogado;
    private PresencaAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faltas);
        presencasRecyclerView = findViewById(R.id.presencasRecyclerView);
        requestQueue = Volley.newRequestQueue(this);
        Intent origemIntent = getIntent();
        aLogado = (Aluno) origemIntent.getSerializableExtra("aLogado");
        //obterLogin(aLogado.getRa(), aLogado.getSenha());
        presencas = aLogado.getPresencas();
        adapter = new PresencaAdapter(faltasActivity.this, presencas);
        LinearLayoutManager llm = new LinearLayoutManager(faltasActivity.this);
        presencasRecyclerView.setAdapter(adapter);
        presencasRecyclerView.setLayoutManager(llm);
        materia = (Materia) origemIntent.getSerializableExtra("materia");

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

    public String montaUrl (String... args){
        StringBuilder sb = new StringBuilder();
        for (String s : args){
            sb.append(s);
        }
        return sb.toString();
    }

    public void obterLogin(String login, String senha){
        String url = montaUrl(getString(R.string.host_address),getString(R.string.host_port),getString(R.string.endpoint_base_aluno),getString(R.string.endpoint_login), getString(R.string.endpoint_email), getString(R.string.endpoint_senha));
        Gson gson = new GsonBuilder().create();
        requestQueue.add(
                new JsonObjectRequest(
                        Request.Method.GET,
                        String.format(url, login, senha),
                        null,
                        new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response){
                                aLogado = gson.fromJson(response.toString(), Aluno.class);
                                presencas = aLogado.getPresencas();
                                adapter = new PresencaAdapter(faltasActivity.this, presencas);
                                LinearLayoutManager llm = new LinearLayoutManager(faltasActivity.this);
                                presencasRecyclerView.setAdapter(adapter);
                                presencasRecyclerView.setLayoutManager(llm);
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
