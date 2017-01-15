package br.com.carlos.ceciliaapp.model;

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
        this.token = token;
        this.id = 666;
        this.nome = "Zeca";
    }
}
