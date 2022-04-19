package com.alphazer0.wmsgestaocomercial.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ContaRecebida implements Serializable {
//==================================================================================================
    //VARIAVEIS
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String codigoBarras;
    private String conta;
    private String vlConta;
    private String dataRecebimento;
//==================================================================================================
    public ContaRecebida() {
    }

    public ContaRecebida(int id, String codigoBarras, String conta, String vlConta, String dataRecebimento) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.conta = conta;
        this.vlConta = vlConta;
        this.dataRecebimento = dataRecebimento;
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

    public String getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(String dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }
    //==================================================================================================
}
