package com.alphazer0.wmsgestaocomercial.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ContaPaga implements Serializable {
//==================================================================================================
    //VARIAVEIS
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String codigoBarras;
    private String conta;
    private String vlConta;
    private String dataPagamento;
    private String horaPagamento;
//==================================================================================================
    public ContaPaga() {
    }

    public ContaPaga(int id, String codigoBarras, String conta, String vlConta, String dataPagamento, String horaPagamento) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.conta = conta;
        this.vlConta = vlConta;
        this.dataPagamento = dataPagamento;
        this.horaPagamento = horaPagamento;
    }
//==================================================================================================
    //SOBREESCREVENDO O TOSTRING
    @NonNull
    @Override
    public String toString() {
        return conta;
    }
//==================================================================================================
    public boolean temIdValido() {
        return id > 0;
    }
//==================================================================================================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getVlConta() {
        return vlConta;
    }

    public void setVlConta(String vlConta) {
        this.vlConta = vlConta;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getHoraPagamento() {
        return horaPagamento;
    }

    public void setHoraPagamento(String horaPagamento) {
        this.horaPagamento = horaPagamento;
    }
//==================================================================================================
}
