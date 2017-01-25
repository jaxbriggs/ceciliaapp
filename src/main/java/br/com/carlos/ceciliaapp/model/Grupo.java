package br.com.carlos.ceciliaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Carlos Henrique on 1/13/2017.
 */

@DatabaseTable(tableName = "grupo")
public class Grupo {

    @DatabaseField(id = true, canBeNull = false, unique = true)
    private Long id;

    @DatabaseField(canBeNull = false, width = 30, unique = true)
    private String nome;

    @DatabaseField(canBeNull = false)
    private Date dt_cadastro;

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

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }
}
