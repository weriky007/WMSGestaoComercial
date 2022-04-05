package com.alphazer0.wmsgestaocomercial.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TotalContasAPagar implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
}
