package br.g3.piapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Result;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RequestQueue requestQueue;
    private Aluno alunoLogado;
    private Professor professorLogado;
    private EditText raEditText;
    private EditText senhaEditText;
    private Button loginProfessor;
    private Button loginAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        raEditText = findViewById(R.id.raEditText);
        senhaEditText = findViewById(R.id.senhaEditText);
        loginAluno = findViewById(R.id.login_aluno);
        loginProfessor = findViewById(R.id.login_professor);
        mAuth = FirebaseAuth.getInstance();

        loginAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerLoginAluno(view);
            }
        });

        loginProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerLoginProfessor(view);
            }
        });
    }

    public void fazerLoginAluno(View view){
        String login = raEditText.getEditableText().toString();
        String senha = senhaEditText.getEditableText().toString();
        mAuth.signInWithEmailAndPassword(login, senha).addOnSuccessListener((Result) -> {
            obterLoginAluno(login, senha);

            /*
            alunoLogado = new Aluno();
            alunoLogado.setRa(login);
            alunoLogado.setSenha(senha);
            Intent intent = new Intent (this, MateriaActivity.class);
            intent.putExtra("alunoLogado", alunoLogado);
            startActivity (intent);
             */

        }).addOnFailureListener((exception) -> {
            exception.printStackTrace();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    public void fazerLoginProfessor(View view){
        String login = raEditText.getEditableText().toString();
        String senha = senhaEditText.getEditableText().toString();
        mAuth.signInWithEmailAndPassword(login, senha).addOnSuccessListener((Result) -> {
            //obterLogin(login, senha);
            professorLogado = new Professor();
            professorLogado.setRa(login);
            professorLogado.setSenha(senha);
            Intent intent = new Intent (this, MateriaActivity.class);
            intent.putExtra("professorLogado", professorLogado);
            startActivity (intent);

        }).addOnFailureListener((exception) -> {
            exception.printStackTrace();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


    public String montaUrl (String... args){
        StringBuilder sb = new StringBuilder();
        for (String s : args){
            sb.append(s);
        }
        return sb.toString();
    }

    public void obterLoginAluno(String login, String senha){
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
                                alunoLogado = gson.fromJson(response.toString(), Aluno.class);
                                Intent intent = new Intent (MainActivity.this, MateriaActivity.class);
                                intent.putExtra("alunoLogado", alunoLogado);
                                startActivity (intent);
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

    public void obterLoginProfessor(String login, String senha){
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
                                professorLogado = gson.fromJson(response.toString(), Professor.class);
                                Intent intent = new Intent (MainActivity.this, MateriaActivity.class);
                                intent.putExtra("professorLogado", professorLogado);
                                startActivity (intent);
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
