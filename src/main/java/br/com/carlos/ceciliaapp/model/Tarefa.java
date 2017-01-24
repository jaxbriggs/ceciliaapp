package br.com.carlos.ceciliaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Carlos Henrique on 1/12/2017.
 */

@DatabaseTable(tableName = "tarefa")
public class Tarefa {

    @JsonProperty("ID")
    @DatabaseField(id = true, canBeNull = false, unique = true, columnName = "ID")
    private Long ID;

    @JsonProperty("TITULO")
    @DatabaseField(canBeNull = false, width = 30, unique = true)
    private String TITULO;

    @JsonProperty("PERIODICIDADE")
    @DatabaseField(canBeNull = false, foreign = true)
    private TarefaPeriodicidade PERIODICIDADE;

    @JsonProperty("GRUPO")
    @DatabaseField(canBeNull = false, foreign = true)
    private Grupo GRUPO;

    @JsonProperty("RESPONSAVEL")
    @DatabaseField(canBeNull = false, foreign = true)
    private Usuario RESPONSAVEL;

    @JsonProperty("USUARIO")
    @DatabaseField
    private Long ID_USUARIO;

    @JsonProperty("DT_CADASTRO")
    @DatabaseField(canBeNull = false)
    private Date DT_CADASTRO;

    public Tarefa() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTITULO() {
        return TITULO;
    }

    public void setTITULO(String TITULO) {
        this.TITULO = TITULO;
    }

    public TarefaPeriodicidade getPERIODICIDADE() {
        return PERIODICIDADE;
    }

    public void setPERIODICIDADE(TarefaPeriodicidade PERIODICIDADE) {
        this.PERIODICIDADE = PERIODICIDADE;
    }

    public Grupo getGRUPO() {
        return GRUPO;
    }

    public void setGRUPO(Grupo GRUPO) {
        this.GRUPO = GRUPO;
    }

    public Usuario getRESPONSAVEL() {
        return RESPONSAVEL;
    }

    public void setRESPONSAVEL(Usuario RESPONSAVEL) {
        this.RESPONSAVEL = RESPONSAVEL;
    }

    public Long getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(Long ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public Date getDT_CADASTRO() {
        return DT_CADASTRO;
    }

    public void setDT_CADASTRO(Date DT_CADASTRO) {
        this.DT_CADASTRO = DT_CADASTRO;
    }

}
