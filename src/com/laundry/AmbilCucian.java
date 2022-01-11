package com.laundry;

import java.util.List;
import java.util.Scanner;

public class AmbilCucian {
    static void ambilCucian(List<Transaksi> listTransaction) {
        Scanner input = new Scanner(System.in);
        boolean isRunning = true;

        do {
            System.out.println("Apakah ada kwitansi ? ");
            System.out.println("+===========================+");
            System.out.println("|     [1] YA | [2] TIDAK    |");
            System.out.println("+===========================+");
            System.out.print("Masukan pilihan anda: ");

            int kwitansiStatus = input.nextInt();


            switch (kwitansiStatus) {
                case 1:
                    ambilCucianByKwitansi(listTransaction);
                    break;
                case 2:
                    ambilCucianByPhone(listTransaction);
                    break;
                default:
                    break;
            }
            System.out.println("Ambil cucian lagi?");
            System.out.println("+===========================+");
            System.out.println("|     [1] YA | [2] TIDAK    |");
            System.out.println("+===========================+");
            System.out.print("Masukan pilihan anda: ");
            isRunning = input.nextInt() == 1;
        } while (isRunning);
    }

    static void ambilCucianByPhone(List<Transaksi> listTransaction) {
        Scanner input = new Scanner(System.in);
        boolean isRunning = true;

        do {
            System.out.print("Silahkan masukan nomor hp: ");
            String phone = input.nextLine();
            List<Transaksi> hasRows = Utils.findTransaksiByPhone(phone, listTransaction);
            if(hasRows.size() != 0) {
                Utils.printTransaksiIntoTable(hasRows);
                System.out.print("Silahkan masukan nomor kwitansi yang akan di ambil: ");
                String noKwitansi = input.nextLine();
                Transaksi hasRow = Utils.findTransaksiByKwitansi(noKwitansi, hasRows);
                if (hasRow != null) {
                    boolean changeStatus = ambilCucian(hasRow);
                    if(changeStatus) {
                        int index = getIndex(hasRow, listTransaction);
                        listTransaction.get(index).setStatus(StatusTransaksi.DONE);
                        listTransaction.get(index).setUpdateAt(Utils.getDateTimeNow());
                        System.out.println("+-------------------------------------+");
                        System.out.println("+    Status cucian berhasil diubah    +");
                        System.out.println("+-------------------------------------+");
                        isRunning = false;
                    } else {
                        isRunning = false;
                    }
                } else {
                    System.out.println("+-------------------------+");
                    System.out.println("+  Data tidak ditemukan   +");
                    System.out.println("+-------------------------+");
                    isRunning = false;
                }
            }
        } while (isRunning);
    }

    static void ambilCucianByKwitansi(List<Transaksi> listTransaction) {
        Scanner input = new Scanner(System.in);
        boolean isRunning = true;
        do {
            System.out.print("Silahkan masukan nomor kwitansi: ");
            String kwitansi = input.nextLine();
            Transaksi hasRow = Utils.findTransaksiByKwitansi(kwitansi, listTransaction);
            if (hasRow != null) {
                boolean changeStatus = ambilCucian(hasRow);
                if(changeStatus) {
                    int index = getIndex(hasRow, listTransaction);
                    listTransaction.get(index).setStatus(StatusTransaksi.DONE);
                    listTransaction.get(index).setUpdateAt(Utils.getDateTimeNow());
                    System.out.println("+-------------------------------------+");
                    System.out.println("+    Status cucian berhasil diubah    +");
                    System.out.println("+-------------------------------------+");
                    isRunning = false;
                } else {
                    isRunning = false;
                }
            } else {
                System.out.println("+-------------------------+");
                System.out.println("+  Data tidak ditemukan   +");
                System.out.println("+-------------------------+");
                isRunning = false;
            }
        } while (isRunning);
    }

    static int getIndex(Transaksi transaksi, List<Transaksi> listTransaction) {
        return listTransaction.indexOf(transaksi);
    }

    static boolean ambilCucian(Transaksi transaksi) {
        if(transaksi.status == StatusTransaksi.DONE) {
            System.out.println("+-------------------------------------------------------------+");
            System.out.println("+      Cucian sudah diambil pada tanggal: " + transaksi.getUpdateAt() + "    +");
            System.out.println("+-------------------------------------------------------------+");
            return false;
        }
        Scanner input = new Scanner(System.in);
        boolean changeStatus = false;
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
        System.out.println("");
        System.out.println("Apakah data sudah benar? ");
        System.out.println("+===========================+");
        System.out.println("|   [1] BENAR | [2] SALAH   |");
        System.out.println("+===========================+");
        System.out.print("Masukan pilihan anda: ");
        changeStatus = input.nextInt() == 1;
        return changeStatus;
    }
}