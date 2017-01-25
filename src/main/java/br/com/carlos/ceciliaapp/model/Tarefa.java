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

    @DatabaseField(id = true, canBeNull = false, unique = true)
    private Long id;

    @DatabaseField(canBeNull = false, width = 30, unique = true)
    private String titulo;

    @DatabaseField(
            canBeNull = false,
            foreign = true,
            foreignAutoRefresh = true
    )
    private TarefaPeriodicidade periodicidade;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Grupo grupo;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Usuario responsavel;

    @DatabaseField
    private Long id_usuario;

    @DatabaseField(canBeNull = false)
    private Date dt_cadastro;

    public Tarefa() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TarefaPeriodicidade getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(TarefaPeriodicidade periodicidade) {
        this.periodicidade = periodicidade;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    public String decidePeriodicidadeTitle(){

        if(this.periodicidade.getId() == 1L){
            return "Diária";
        } else if(this.periodicidade.getDt_a_partir() != null && this.periodicidade.getQt_passo() != null){
            return "Customizada";
        } else if(this.periodicidade.getId_dia_mes() != null){
            return "Mensal";
        } else if(this.periodicidade.getId_dia_semana() != null){
            return "Semanal";
        }

        return null;

    }
}
