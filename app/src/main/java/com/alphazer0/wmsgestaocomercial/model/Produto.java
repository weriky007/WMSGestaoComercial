package com.alphazer0.wmsgestaocomercial.model;
//==================================================================================================
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//==================================================================================================
@Entity
public class Produto implements Serializable {
//==================================================================================================
    //VARIAVEIS
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String idCod;
    private String categoria;
    private String produto;
    private String marca;
    private String discriminacao;
    private String precoCompra;
    private String precoVenda;
    private String quantidade;
    private String unidadeMedida;
//==================================================================================================
    @Ignore
    //METODOS SOBRECARGA
    public Produto(int id, String idCod, String categoria, String produto, String marca, String discriminacao, String precoCompra, String precoVenda, String quantidade, String unidadeMedida) {
        this.id = id;
        this.idCod = idCod;
        this.categoria = categoria;
        this.produto = produto;
        this.marca = marca;
        this.discriminacao = discriminacao;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
    }

    public Produto(){}
//==================================================================================================
//SOBREESCREVENDO O TOSTRING
    @NonNull
    @Override
    public String toString() {
        return produto;
    }
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

    public String getIdCod() {
        return idCod;
    }

    public void setIdCod(String idCod) {
        this.idCod = idCod;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDiscriminacao() {
        return discriminacao;
    }

    public void setDiscriminacao(String discriminacao) {
        this.discriminacao = discriminacao;
    }

    public String getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(String precoCompra) {
        this.precoCompra = precoCompra;
    }

    public String getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(String precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

//==================================================================================================
}
