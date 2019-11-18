package br.g3.piapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MateriaActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private RecyclerView materiasRecyclerView;
    private MateriaAdapter adapter;
    private List<Materia> materias;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Aluno logado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);
        logado = (Aluno) getIntent().getSerializableExtra("logado");
        requestQueue = Volley.newRequestQueue(this);
        materiasRecyclerView = findViewById(R.id.materiasRecyclerView);
        materias = new ArrayList<Materia>();
        adapter = new MateriaAdapter(this, materias);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        materiasRecyclerView.setAdapter(adapter);
        materiasRecyclerView.setLayoutManager(llm);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        obterMaterias();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        materiasRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, materiasRecyclerView, new ClickListener(){
            @Override
            public void onClick(View view, final int position){
                Materia materiaSelecionada = materias.get(position);
                Intent intent = new Intent (MateriaActivity.this, AulaActivity.class );
                intent.putExtra("aula_escolhida", materiaSelecionada);
                intent.putExtra("logado", logado);
                startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position){

            }
        }
        )
        );
    }

    public String montaUrl (String... args){
        StringBuilder sb = new StringBuilder();
        for (String s : args){
            sb.append(s);
        }
        return sb.toString();
    }

    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

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
}
