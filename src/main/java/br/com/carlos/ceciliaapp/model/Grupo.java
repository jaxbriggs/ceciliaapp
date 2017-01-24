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

    @JsonProperty("ID")
    @DatabaseField(id = true, canBeNull = false, unique = true)
    private Long ID;

    @JsonProperty("NOME")
    @DatabaseField(canBeNull = false, width = 30, unique = true)
    private String NOME;

    @JsonProperty("DT_CADASTRO")
    @DatabaseField(canBeNull = false)
    private Date DT_CADASTRO;

    public Grupo() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public Date getDT_CADASTRO() {
        return DT_CADASTRO;
    }

    public void setDT_CADASTRO(Date DT_CADASTRO) {
        this.DT_CADASTRO = DT_CADASTRO;
    }
}
