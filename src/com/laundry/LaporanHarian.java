package com.laundry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class LaporanHarian {
    static void laporanHarian(List<Transaksi> listTransaction) {
        if (listTransaction.size() == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        Scanner input = new Scanner(System.in);
        float total = 0;
        boolean isRunning = true;
        for (Transaksi item: listTransaction) {
            total += item.getTotalPrice();
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String timeNow = dtf.format(now);
        Utils.printTransaksiIntoTable(listTransaction);
        System.out.println("Laporan Keuangan Tangal " + timeNow + " : Rp" + String.format("%,.0f", total));
        do {
            System.out.println("+-----------------------------------------------------+");
            System.out.println("+   [1] Detail    |    [2] Ambil   |   [3] Kembali    +");
            System.out.println("+-----------------------------------------------------+");
            System.out.print("Masukan pilihan kamu: ");
            int selected = input.nextInt();
            switch (selected) {
                case 1:
                    System.out.print("Silahkan masukan no. kwitansi: ");
                    String kwitansi = input.nextLine();
                    Transaksi hasRow = Utils.findTransaksiByKwitansi(kwitansi, listTransaction);
                    if (hasRow != null) {
                        showDetail(hasRow);
                    } else {
                        System.out.println("+-------------------------+");
                        System.out.println("+  Data tidak ditemukan   +");
                        System.out.println("+-------------------------+");
                    }
                    break;
                case 2:
                    AmbilCucian.ambilCucian(listTransaction);
                    break;
                case 3:
                    isRunning = false;
                    break;
                default:
                    System.out.println("+-----------------------+");
                    System.out.println("+  Menu tidak tersedia! +");
                    System.out.println("+-----------------------+");
                    break;
            }
        } while (isRunning);
    }

    static void showDetail(Transaksi transaksi) {
        List<Cart> carts = transaksi.getCarts();
        System.out.println("Cucian yang akan di ambil");
        System.out.println("");
        System.out.println("=====================================================");
        System.out.println("No. Kwitansi    : "+ transaksi.getNoKwitansi());
        System.out.println("Nama            : "+ transaksi.getPelanggan().getName());
        System.out.println("No. Hp          : "+ transaksi.getPelanggan().getPhone());
        System.out.println("-----------------------------------------------------");
        for (int i = 0; i < carts.size(); i++) {
            Cart item = carts.get(i);
            String qty = item.getLaundry().getType() == TypeLaundry.KILOAN ? String.valueOf(item.getWeight()) + " Kg"
                    : String.valueOf(item.getQty()) + " Pcs";
            Utils.print(String.valueOf(i + 1) + ". " + item.getLaundry().getName() + " " + qty + " " +
                    "("+item.getService().getName() + ") Rp" + String.format("%,.0f", item.getTotalPrice()));
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("Total Tagihan     : " + String.format("%,.0f", transaksi.getTotalPrice()));
        System.out.println("Status Pembayaran : LUNAS");
        System.out.println("=====================================================");
    }
}
