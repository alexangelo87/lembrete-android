package br.com.alex.lembrete.model;

import java.io.Serializable;

public class Lembrete implements Serializable {
    private String tarefa;
    private String status;

    public Lembrete() {
    }

    public Lembrete(String tarefa, String status) {
        this.tarefa = tarefa;
        this.status = status;
    }

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
