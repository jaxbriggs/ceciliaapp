package br.com.carlos.ceciliaapp.model;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

import br.com.carlos.ceciliaapp.BuildConfig;
import br.com.carlos.ceciliaapp.activity.LoginActivity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Created by Carlos Henrique on 1/7/2017.
 */
public class Usuario {

    private long id;
    private String nome;
    private String login;

    private String token;

    public Usuario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {

        byte[] decoded = Base64.decode(BuildConfig.SECRET_KEY, Base64.DEFAULT);

        Claims claims = Jwts.parser()
                .setSigningKey(decoded)
                .parseClaimsJws(token).getBody();

        this.token = token;
        this.id = (int)((LinkedHashMap)claims.get("data")).get("usuarioId");
        this.nome = ((LinkedHashMap)claims.get("data")).get("usuarioNome").toString();
    }
}
