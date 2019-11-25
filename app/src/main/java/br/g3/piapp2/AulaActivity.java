package br.g3.piapp2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AulaActivity extends AppCompatActivity {
    private TextView tituloTextView;
    private TextView horaTextView;
    private ImageView qrImageView;
    private Button lerQrButton;
    private Button consultarFaltasButton;
    private Aluno aLogado;
    private Professor pLogado;
    private RequestQueue requestQueue;
    private Materia aulaEscolhida;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);
        requestQueue = Volley.newRequestQueue(this);
        Intent origem = getIntent();
        if(origem.getSerializableExtra("aLogado") != null) {
            aLogado = (Aluno) origem.getSerializableExtra("aLogado");
        }else{
            pLogado = (Professor) origem.getSerializableExtra("pLogado");
        }
        tituloTextView = findViewById(R.id.tituloTextView);
        horaTextView = findViewById(R.id.horaTextView);
        qrImageView = findViewById(R.id.qrImageView);
        lerQrButton = findViewById(R.id.lerQrButton);
        consultarFaltasButton = findViewById(R.id.consultarFaltasButton);
        if(pLogado != null){
            lerQrButton.setText("Gerar QR Code");
            consultarFaltasButton.setText("Editar Lista");
        }
        Intent origemIntent = getIntent();
        aulaEscolhida = (Materia) origemIntent.getSerializableExtra("aula_escolhida");
        tituloTextView.setText(aulaEscolhida.getNome());
        //horaTextView.setText(aulaEscolhida.getHorario());
        final Activity activity = this;

        lerQrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aLogado != null) {
                    IntentIntegrator integrator = new IntentIntegrator(activity);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    integrator.setPrompt("Camera Scan");
                    integrator.setCameraId(0);
                    integrator.initiateScan();
                }else{
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String sData = formatter.format(new Date());
                    QRCodeWriter writer = new QRCodeWriter();
                    try {
                        BitMatrix bitMatrix = writer.encode(sData, BarcodeFormat.QR_CODE, 512, 512);
                        int width = bitMatrix.getWidth();
                        int height = bitMatrix.getHeight();
                        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        for (int x = 0; x < width; x++) {
                            for (int y = 0; y < height; y++) {
                                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                            }
                        }
                        qrImageView.setImageBitmap(bmp);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        consultarFaltasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AulaActivity.this, faltasActivity.class);
                intent.putExtra("materia", aulaEscolhida);
                intent.putExtra("aLogado", aLogado);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){
                String resultado = result.getContents();
                fazPresenca(resultado, aLogado.getId(), aulaEscolhida.getId());
            }else{
                alert("Scan cancelado.");
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
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

    public void fazPresenca(String d, int a, int m){
        String url = montaUrl(getString(R.string.host_address),getString(R.string.host_port),getString(R.string.endpoint_base_presenca),getString(R.string.endpoint_atualizar));
        requestQueue.add(
                new JsonObjectRequest(
                        Request.Method.POST,
                        String.format(url, d, a, m),
                        null,
                        new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response){
                                alert("Presença confirmada!");
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

    private void alert (String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
