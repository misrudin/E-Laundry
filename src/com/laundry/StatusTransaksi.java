package com.laundry;

public enum StatusTransaksi {
    PROCESS("Poses Laundry"),
    DONE("Diambil");

    public String status;

    StatusTransaksi(String status) {
        this.status = status;
    }
}