package br.com.carlos.ceciliaapp.model;

import java.util.Date;

/**
 * Created by Carlos Henrique on 1/13/2017.
 */

public class Grupo {

    private Long id;
    private String nome;
    private Date dtCadastro;

    public Grupo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
}
