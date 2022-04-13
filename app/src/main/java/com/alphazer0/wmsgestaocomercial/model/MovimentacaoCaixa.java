package com.alphazer0.wmsgestaocomercial.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class MovimentacaoCaixa implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String tipo = "";
    private String data = "";
    private String descricao = "";
    private String valor = "";

    @Ignore
    public MovimentacaoCaixa(int id, String tipo, String data, String descricao, String valor) {
        this.id = id;
        this.tipo = tipo;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
    }

    public MovimentacaoCaixa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
