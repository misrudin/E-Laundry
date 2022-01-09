package com.laundry;

public enum StatusTransaksi {
    PENDING("Belum Dicuci"),
    PROCESS("Dicuci"),
    WAITING("Siap Diambil"),
    DONE("Diambil");

    public String status;

    StatusTransaksi(String status) {
        this.status = status;
    }
}