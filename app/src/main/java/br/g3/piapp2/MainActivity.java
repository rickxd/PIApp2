package br.g3.piapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button loginProfessor;
    private Button loginAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginAluno = findViewById(R.id.login_aluno);
        loginProfessor = findViewById(R.id.login_professor);

        loginAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alunoIntent = new Intent(MainActivity.this, AlunoActivity.class);
                startActivity(alunoIntent);
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


}
