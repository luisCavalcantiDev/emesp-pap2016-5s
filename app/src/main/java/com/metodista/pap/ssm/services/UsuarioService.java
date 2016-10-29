package com.metodista.pap.ssm.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.metodista.pap.ssm.model.Usuario;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UsuarioService {

    private static final String _URL = "http://localhost:3000/ssm/api/tests/'"; //TODO: alterar para url players
    private List<Usuario> usuarios = null;

    public List<Usuario> autenticar(String email) throws IOException {
        List<Usuario> result = null;
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        Scanner s = null;

        try {
            URL url = new URL(_URL);
            urlConnection = (HttpURLConnection) url.openConnection();

            in = new BufferedInputStream(urlConnection.getInputStream());
            s = new Scanner(in);
            String conteudo = s.useDelimiter("\\A").next();

            Gson gson = new Gson();
            result = gson.fromJson(conteudo, new TypeToken<List<Usuario>>() {}.getType());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (in != null) {
                in.close();
            }

            if (s != null) {
                s.close();
            }
        }

        if (result.get(0).getEmail().equals((email))) {
            usuarios = new ArrayList<>();
            usuarios.add(result.get(0));
        }

        return this.usuarios;
    }
}