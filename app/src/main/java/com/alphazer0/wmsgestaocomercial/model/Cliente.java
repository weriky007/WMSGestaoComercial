package com.alphazer0.wmsgestaocomercial.model;
//==================================================================================================

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//==================================================================================================
@Entity
public class Cliente implements Serializable {
//==================================================================================================
    //VARIAVEIS
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    //DADOS PESSOAIS
    private String nomeCompleto;
    private String dataNascimento;
    private String cpf;
    private String rg;
    private String nomePai;
    private String nomeMae;

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

    private String divida = "0";
//==================================================================================================
    //METODOS DE SOBRECARGA
    public Cliente(){}


    public Cliente(int id, String nomeCompleto,  String dataNascimento, String cpf, String rg, String nomePai, String nomeMae, String celular1, String celular2, String telefone, String email, String rua, String numero, String quadra, String lote, String bairro, String cep, String complemento) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.rg = rg;
        this.nomePai = nomePai;
        this.nomeMae = nomeMae;
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
        return nomeCompleto;
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

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
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

    public String getDivida() {
        return divida;
    }

    public void setDivida(String divida) {
        this.divida = divida;
    }

    //==================================================================================================
}
