package br.com.carlos.ceciliaapp.model;

import android.util.Base64;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.LinkedHashMap;
import java.util.List;

import br.com.carlos.ceciliaapp.BuildConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Created by Carlos Henrique on 1/7/2017.
 */
@DatabaseTable(tableName = "usuario")
public class Usuario {

    @DatabaseField(id = true, canBeNull = false, unique = true)
    private long id;

    @DatabaseField(canBeNull = false, width = 30, unique = true)
    private String nome;

    @DatabaseField(canBeNull = false, width = 30, unique = true)
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

        if(token != null) {
            byte[] decoded = Base64.decode(BuildConfig.SECRET_KEY, Base64.DEFAULT);

            Claims claims = Jwts.parser()
                    .setSigningKey(decoded)
                    .parseClaimsJws(token).getBody();

            this.token = token;
            this.id = (int) ((LinkedHashMap) claims.get("data")).get("usuarioId");
            this.nome = ((LinkedHashMap) claims.get("data")).get("usuarioNome").toString();
        } else {
            this.token = null;
        }

    }
}
