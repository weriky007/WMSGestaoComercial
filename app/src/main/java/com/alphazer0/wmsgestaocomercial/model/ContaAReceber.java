package com.alphazer0.wmsgestaocomercial.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ContaAReceber implements Serializable {
//==================================================================================================
    //VARIAVEIS
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String codigoBarras;
    private String conta;
    private String vlConta;
    private String dataVencimento;
//==================================================================================================
    public ContaAReceber() {
    }

    public ContaAReceber(int id, String codigoBarras, String conta, String vlConta, String dataVencimento) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.conta = conta;
        this.vlConta = vlConta;
        this.dataVencimento = dataVencimento;
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

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
//==================================================================================================
}
