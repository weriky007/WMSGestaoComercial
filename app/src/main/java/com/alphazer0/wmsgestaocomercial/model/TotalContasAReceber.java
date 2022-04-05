package com.alphazer0.wmsgestaocomercial.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TotalContasAReceber implements Serializable {
    private String total="";

    public TotalContasAReceber(String total) {
        this.total = total;
    }

    public TotalContasAReceber() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
