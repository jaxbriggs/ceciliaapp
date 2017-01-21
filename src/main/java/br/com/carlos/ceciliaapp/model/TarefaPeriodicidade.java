package br.com.carlos.ceciliaapp.model;

import java.util.Date;

/**
 * Created by Carlos Henrique on 1/21/2017.
 */

public class TarefaPeriodicidade {
    private Long id;
    private Short diaSemana;
    private Short diaMes;
    private Date diaPartir;
    private Short passo;

    public TarefaPeriodicidade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Short diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Short getDiaMes() {
        return diaMes;
    }

    public void setDiaMes(Short diaMes) {
        this.diaMes = diaMes;
    }

    public Date getDiaPartir() {
        return diaPartir;
    }

    public void setDiaPartir(Date diaPartir) {
        this.diaPartir = diaPartir;
    }

    public Short getPasso() {
        return passo;
    }

    public void setPasso(Short passo) {
        this.passo = passo;
    }
}
