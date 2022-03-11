package com.alphazer0.wmsgestaocomercial.model;
//==================================================================================================

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//==================================================================================================
@Entity
public class FornecedorPJ implements Serializable {
//==================================================================================================
    //VARIAVEIS
    @PrimaryKey(autoGenerate = true)
    private int id =0;

    //DADOS
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String ie;

    //CONTATO
    private String celular1;
    private String celular2;
    private String telefone;
    private String email;

    //ENDERECO
    private String rua;
    private String numero;
    private String quadra;
    private String lote;
    private String bairro;
    private String cep;
    private String complemento;
//==================================================================================================
    //METODOS DE SOBRECARGA
    public FornecedorPJ(){}

    @Ignore
    public FornecedorPJ(int id, String razaoSocial, String nomeFantasia, String cnpj, String ie, String celular1, String celular2, String telefone, String email, String rua, String numero, String quadra, String lote, String bairro, String cep, String complemento) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.ie = ie;
        this.celular1 = celular1;
        this.celular2 = celular2;
        this.telefone = telefone;
        this.email = email;
        this.rua = rua;
        this.numero = numero;
        this.quadra = quadra;
        this.lote = lote;
        this.bairro = bairro;
        this.cep = cep;
        this.complemento = complemento;
    }
//==================================================================================================
    //SOBREESCREVENDO O TOSTRING
    @NonNull
    @Override
    public String toString(){
        return razaoSocial;
    }
//==================================================================================================
    public boolean temIdValido(){
        return id >0;
    }
//==================================================================================================
    //GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getCelular1() {
        return celular1;
    }

    public void setCelular1(String celular1) {
        this.celular1 = celular1;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getQuadra() {
        return quadra;
    }

    public void setQuadra(String quadra) {
        this.quadra = quadra;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }


//==================================================================================================
}