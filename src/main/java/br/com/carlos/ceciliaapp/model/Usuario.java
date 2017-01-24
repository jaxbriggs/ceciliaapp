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

    @JsonProperty("ID")
    @DatabaseField(id = true, canBeNull = false, unique = true)
    private long ID;

    @JsonProperty("NOME")
    @DatabaseField(canBeNull = false, width = 30, unique = true)
    private String NOME;

    @JsonProperty("LOGIN")
    @DatabaseField(canBeNull = false, width = 30, unique = true)
    private String LOGIN;

    private String token;

    public Usuario() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getLOGIN() {
        return LOGIN;
    }

    public void setLOGIN(String LOGIN) {
        this.LOGIN = LOGIN;
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
            this.ID = (int) ((LinkedHashMap) claims.get("data")).get("usuarioId");
            this.NOME = ((LinkedHashMap) claims.get("data")).get("usuarioNome").toString();
        } else {
            this.token = null;
        }

    }
}
