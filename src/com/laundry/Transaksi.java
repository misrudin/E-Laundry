package com.laundry;

import java.util.ArrayList;
import java.util.List;

public class Transaksi {
    String noKwitansi;
    Pelanggan pelanggan;
    List<Cart> carts = new ArrayList<>();
    private String createdAt;
    double totalPrice;
    StatusTransaksi status;
    private String updateAt;
    Service service;

    public Transaksi(String noKwitansi, Pelanggan pelanggan, List<Cart> carts, String createdAt, double totalPrice, Service service) {
        this.noKwitansi = noKwitansi;
        this.pelanggan = pelanggan;
        this.carts = carts;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.status = StatusTransaksi.PROCESS;
        this.service = service;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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
                ", updateAt='" + updateAt + '\'' +
                ", service=" + service +
                '}';
    }
}
