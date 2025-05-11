package com.example.projetopdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    String URL_BASE_CADASTRO = "https://mfpledon.com.br/contatos2025/cadastrarContatoTexto.php";
    String senha = "35wsx@3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button autor = (Button)findViewById(R.id.autores);
        Button consulta = (Button)findViewById(R.id.consulta);
        Button cadastra = (Button)findViewById(R.id.cadastro);
        Button deleta = (Button)findViewById(R.id.deleta);

        autor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Autores.class));
            }
        });

        // botão para cadastrar
        cadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    try {
                        TextView Console = (TextView)findViewById(R.id.console);
                        String nome = ((EditText) findViewById(R.id.name)).getText().toString();
                        String fone = ((EditText) findViewById(R.id.fone)).getText().toString();
                        String email = ((EditText) findViewById(R.id.email)).getText().toString();
                        String nomeCodificado = URLEncoder.encode(nome, "UTF-8");

                        String urlCompleta = URL_BASE_CADASTRO +
                                "?nome=" + nomeCodificado +
                                "&fone=" + fone +
                                "&email=" + email +
                                "&senha=" + senha;
                        URL url = new URL(urlCompleta);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

                        int responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK){
                            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String inputLine;
                            StringBuilder response = new StringBuilder();
                            while ((inputLine = in.readLine()) != null){
                                response.append(inputLine);
                            }
                            in.close();
                            Console.setText(response.toString());
                        } else {
                            Console.setText(responseCode);
                        }

                    } catch (Exception  e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        // botão para consultar os cadastros
        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Criar sistema de consulta

            }
        });

        // botão de deletar cadastro
        deleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = ((EditText) findViewById(R.id.name)).getText().toString();
                // TODO: Criar sistema de Cadastro
            }
        });

    }
}