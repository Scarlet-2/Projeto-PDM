package com.example.projetopdm;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    String URL_BASE_CADASTRO = "https://mfpledon.com.br/contatos2025/cadastrarContatoTexto.php";
    String URL_BASE_ELIMINAR = "https://mfpledon.com.br/contatos2025/eliminarContato.php";
    String senha = "35wsx@3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TextView Console = findViewById(R.id.console);
        Console.setMovementMethod(new ScrollingMovementMethod());
        Button autor = findViewById(R.id.autores);
        Button consulta = findViewById(R.id.consulta);
        Button cadastra = findViewById(R.id.cadastro);
        Button deleta = findViewById(R.id.deleta);

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
                        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
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
                new Thread(() -> {
                    try {
                        URL url = new URL("https://mfpledon.com.br/contatos2025/contatosJSON.php");
                        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

                        int responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String inputLine;
                            StringBuilder response = new StringBuilder();
                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            String resposta = "";
                            JSONObject reader = new JSONObject(response.toString());
                            JSONArray contatos = reader.getJSONArray("pessoas");
                            for (int i = 0; i < contatos.length(); i++) {
                                JSONObject contato = contatos.getJSONObject(i);
                                String contatoString = contato.getString("contato");
                                String celularString = contato.getString("celular");
                                String emailString = contato.getString("email");

                                Console.append("contato: " + contatoString + "\n" + "celular: " + celularString + "\n" + "email: " + emailString + "\n\n");
                            }
                            in.close();
                        } else {
                            Console.setText(responseCode);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        // botão de deletar cadastro
        deleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Console = (TextView)findViewById(R.id.console);
                new Thread(() -> {
                    try {
                        String nome = ((EditText) findViewById(R.id.name)).getText().toString();
                        String nomeCodificado = URLEncoder.encode(nome, "UTF-8");

                        String urlCompleta = URL_BASE_ELIMINAR + "?nome=" + nomeCodificado + "&senha=" + senha;
                        URL url = new URL(urlCompleta);
                        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
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

    }
}