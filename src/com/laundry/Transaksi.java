package com.laundry;

import java.util.ArrayList;
import java.util.List;

public class Transaksi {
    private String noKwitansi;
    Pelanggan pelanggan;
    List<Cart> carts = new ArrayList<>();
    private String createdAt;
    private double totalPrice;
    StatusTransaksi status;

    public Transaksi(String noKwitansi, Pelanggan pelanggan, List<Cart> carts, String createdAt, double totalPrice) {
        this.noKwitansi = noKwitansi;
        this.pelanggan = pelanggan;
        this.carts = carts;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.status = StatusTransaksi.PENDING;
    }

    public String getNoKwitansi() {
        return noKwitansi;
    }

    public void setNoKwitansi(String noKwitansi) {
        this.noKwitansi = noKwitansi;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public StatusTransaksi getStatus() {
        return status;
    }

    public void setStatus(StatusTransaksi status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaksi{" +
                "noKwitansi='" + noKwitansi + '\'' +
                ", pelanggan=" + pelanggan +
                ", carts=" + carts +
                ", createdAt='" + createdAt + '\'' +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
