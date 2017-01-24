package br.com.carlos.ceciliaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Carlos Henrique on 1/21/2017.
 */

@DatabaseTable(tableName = "tarefa_periodicidade")
public class TarefaPeriodicidade {

    @JsonProperty("ID")
    @DatabaseField(id = true, canBeNull = false, unique = true)
    private Long ID;

    @JsonProperty("ID_DIA_SEMANA")
    @DatabaseField
    private Short ID_DIA_SEMANA;

    @JsonProperty("ID_DIA_MES")
    @DatabaseField
    private Short ID_DIA_MES;

    @JsonProperty("DT_A_PARTIR")
    @DatabaseField
    private Date DT_A_PARTIR;

    @JsonProperty("QT_PASSO")
    @DatabaseField
    private Short QT_PASSO;

    public TarefaPeriodicidade() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Short getID_DIA_SEMANA() {
        return ID_DIA_SEMANA;
    }

    public void setID_DIA_SEMANA(Short ID_DIA_SEMANA) {
        this.ID_DIA_SEMANA = ID_DIA_SEMANA;
    }

    public Short getID_DIA_MES() {
        return ID_DIA_MES;
    }

    public void setID_DIA_MES(Short ID_DIA_MES) {
        this.ID_DIA_MES = ID_DIA_MES;
    }

    public Date getDT_A_PARTIR() {
        return DT_A_PARTIR;
    }

    public void setDT_A_PARTIR(Date DT_A_PARTIR) {
        this.DT_A_PARTIR = DT_A_PARTIR;
    }

    public Short getQT_PASSO() {
        return QT_PASSO;
    }

    public void setQT_PASSO(Short QT_PASSO) {
        this.QT_PASSO = QT_PASSO;
    }
}
