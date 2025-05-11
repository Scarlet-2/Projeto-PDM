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

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
                        String foneCodificado = URLEncoder.encode(fone, "UTF-8");
                        String emailCodificado = URLEncoder.encode(email, "UTF-8");
                        String senha = "35wsx@3";
                        String URL_BASE = "https://mfpledon.com.br/contatos2025/cadastrarContatoTexto.php";


                        Console.setText("Nome: "+nomeCodificado+"\nTelefone: "+foneCodificado+"\nE-mail: "+emailCodificado);

                        String urlCompleta = URL_BASE +
                                "?nome=" + nomeCodificado +
                                "&fone" + foneCodificado +
                                "&email" + emailCodificado +
                                "&senha" + senha;
//                        URL url =

                    } catch (Exception  e) {
                        e.printStackTrace();
                    }
                }).start();;
            }
        });

        // botão para consultar os cadastros
        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        // botão de deletar cadastro
        deleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = ((EditText) findViewById(R.id.name)).getText().toString();

            }
        });

    }
}