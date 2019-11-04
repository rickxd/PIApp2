package br.g3.piapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Result;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText raEditText;
    private EditText senhaEditText;
    private Button loginProfessor;
    private Button loginAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        raEditText = findViewById(R.id.raEditText);
        senhaEditText = findViewById(R.id.senhaEditText);
        loginAluno = findViewById(R.id.login_aluno);
        loginProfessor = findViewById(R.id.login_professor);
        mAuth = FirebaseAuth.getInstance();

        loginAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerLogin(view);
            }
        });

        loginProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent professorIntent = new Intent(MainActivity.this, ProfessorActivity.class);
                startActivity(professorIntent);
            }
        });
    }

    public void fazerLogin(View view){
        String login = raEditText.getEditableText().toString();
        String senha = senhaEditText.getEditableText().toString();
        mAuth.signInWithEmailAndPassword(login, senha).addOnSuccessListener((Result) -> {
            startActivity (new Intent (this, MateriaActivity.class));
        }).addOnFailureListener((exception) -> {
            exception.printStackTrace();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


}
