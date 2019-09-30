package br.g3.piapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AulaActivity extends AppCompatActivity {
    private TextView tituloTextView;
    private TextView horaTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);
        tituloTextView = findViewById(R.id.tituloTextView);
        horaTextView = findViewById(R.id.horaTextView);
        Intent origemIntent = getIntent();
        Materia aulaEscolhida = (Materia) origemIntent.getSerializableExtra("aula_escolhida");

        tituloTextView.setText(aulaEscolhida.getNome());
        horaTextView.setText(aulaEscolhida.getHorario());
    }
}
