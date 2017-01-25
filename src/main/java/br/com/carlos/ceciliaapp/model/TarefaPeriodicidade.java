package br.com.carlos.ceciliaapp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Carlos Henrique on 1/21/2017.
 */

@DatabaseTable(tableName = "tarefa_periodicidade")
public class TarefaPeriodicidade {

    @DatabaseField(id = true, canBeNull = false, unique = true)
    private Long id;

    @DatabaseField
    private Short id_dia_semana;

    @DatabaseField
    private Short id_dia_mes;

    @DatabaseField
    private Date dt_a_partir;

    @DatabaseField
    private Short qt_passo;

    public TarefaPeriodicidade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getId_dia_semana() {
        return id_dia_semana;
    }

    public void setId_dia_semana(Short id_dia_semana) {
        this.id_dia_semana = id_dia_semana;
    }

    public Short getId_dia_mes() {
        return id_dia_mes;
    }

    public void setId_dia_mes(Short id_dia_mes) {
        this.id_dia_mes = id_dia_mes;
    }

    public Date getDt_a_partir() {
        return dt_a_partir;
    }

    public void setDt_a_partir(Date dt_a_partir) {
        this.dt_a_partir = dt_a_partir;
    }

    public Short getQt_passo() {
        return qt_passo;
    }

    public void setQt_passo(Short qt_passo) {
        this.qt_passo = qt_passo;
    }
}
