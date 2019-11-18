package br.g3.piapp2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AulaActivity extends AppCompatActivity {
    private TextView tituloTextView;
    private TextView horaTextView;
    private Button lerQrButton;
    private Button consultarFaltasButton;
    private Aluno logado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);
        logado = (Aluno) getIntent().getSerializableExtra("logado");
        tituloTextView = findViewById(R.id.tituloTextView);
        horaTextView = findViewById(R.id.horaTextView);
        lerQrButton = findViewById(R.id.lerQrButton);
        consultarFaltasButton = findViewById(R.id.consultarFaltasButton);
        Intent origemIntent = getIntent();
        Materia aulaEscolhida = (Materia) origemIntent.getSerializableExtra("aula_escolhida");
        tituloTextView.setText(aulaEscolhida.getNome());
        //horaTextView.setText(aulaEscolhida.getHorario());
        final Activity activity = this;

        lerQrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Camera Scan");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });

        consultarFaltasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AulaActivity.this, faltasActivity.class);
                intent.putExtra("materia", aulaEscolhida);
                intent.putExtra("logado", logado);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){
                alert(result.getContents());
            }else{
                alert("Scan cancelado.");
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alert (String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
