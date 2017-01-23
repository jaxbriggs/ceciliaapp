package br.com.carlos.ceciliaapp.model;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Carlos Henrique on 1/12/2017.
 */

public class Tarefa {
    private Long id;
    private String titulo;
    private TarefaPeriodicidade periodicidade;
    private Grupo grupo;
    private Usuario responsavel;
    private Long usuarioId;
    private Date dtCadastro;

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

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

}
