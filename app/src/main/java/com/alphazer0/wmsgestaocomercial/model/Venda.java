package com.alphazer0.wmsgestaocomercial.model;
//==================================================================================================
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.alphazer0.wmsgestaocomercial.database.converter.ConversorDeObjetosParaJSON;

import java.io.Serializable;
import java.util.List;

//==================================================================================================
@Entity
public class Venda implements Serializable {
//==================================================================================================
    //VARIAVEIS
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String data;
    private String hora;
    private String cliente;
    private String vlTotal;
    private String formaPagamento;
    private String parcelasCC;
    private String vlParcelas;

    @TypeConverters(ConversorDeObjetosParaJSON.class)
    private List<Produto> listaCompras;
//==================================================================================================
    @Ignore
    //METODOS SOBRECARGA
    public Venda(){}

    public Venda(int id, String data, String hora, String cliente, String vlTotal, String formaPagamento, String parcelasCC, String vlParcelas, List<Produto> listaCompras) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.cliente = cliente;
        this.vlTotal = vlTotal;
        this.formaPagamento = formaPagamento;
        this.parcelasCC = parcelasCC;
        this.vlParcelas = vlParcelas;
        this.listaCompras = listaCompras;
    }
//==================================================================================================
//SOBREESCREVENDO O TOSTRING
//    @NonNull
//    @Override
//    public String toString() {
//        return produto;
//    }
//==================================================================================================
    public boolean temIdValido(){
    return id >0;
}
//==================================================================================================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(String vlTotal) {
        this.vlTotal = vlTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getParcelasCC() {
        return parcelasCC;
    }

    public void setParcelasCC(String parcelasCC) {
        this.parcelasCC = parcelasCC;
    }

    public String getVlParcelas() {
        return vlParcelas;
    }

    public void setVlParcelas(String vlParcelas) {
        this.vlParcelas = vlParcelas;
    }

    public List<Produto> getListaCompras() {
        return listaCompras;
    }

    public void setListaCompras(List<Produto> listaCompras) {
        this.listaCompras = listaCompras;
    }


//==================================================================================================
}
