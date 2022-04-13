package com.alphazer0.wmsgestaocomercial.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TotalContasAReceber implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String total="0.00";

    @Ignore
    public TotalContasAReceber(int id, String total) {
        this.id = id;
        this.total = total;
    }

    public TotalContasAReceber() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
