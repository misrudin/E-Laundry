package com.laundry;

import java.util.List;
import java.util.Scanner;

public class AmbilCucian {
    static Transaksi ambilCucian(List<Transaksi> listTransaction) {
        Scanner input = new Scanner(System.in);
        boolean isRunning = true;

        do {
            System.out.print("Apakah ada kwitansi? [ 1. Ya / 2. Tidak ]: ");
            boolean isHaveKwitansi = true;

            isHaveKwitansi = input.nextInt() == 1;
            if (isHaveKwitansi) {
                System.out.print("Silahkan masukan nomor kwitansi: ");
                String noKwitansi = input.nextLine();
                input.nextInt();
                Transaksi hasRow = Utils.findTransaksiByKwitansi(noKwitansi, listTransaction);
                if (hasRow != null) {
                    System.out.println(hasRow);
                    return ambilCucian(hasRow);
                } else {
                    System.out.println("Data tidak ditemukan");
                }
            } else {
                System.out.print("Silahkan masukan nomor hp: ");
                String phone = input.nextLine();
                input.nextInt();
                List<Transaksi> hasRows = Utils.findTransaksiByPhone(phone, listTransaction);
                if(hasRows.size() != 0) {
                    System.out.println(hasRows);
                    System.out.print("Silahkan masukan nomor kwitansi: ");
                    String noKwitansi = input.nextLine();
                    input.nextInt();
                    Transaksi hasRow = Utils.findTransaksiByKwitansi(noKwitansi, listTransaction);
                    if (hasRow != null) {
                        System.out.println(hasRow);
                        return ambilCucian(hasRow);
                    } else {
                        System.out.println("Data tidak ditemukan");
                    }
                }
            }

            System.out.print("Ambil cucian lagi? [ 1. Ya / 2. Tidak ]: ");
            isRunning = input.nextInt() == 1;
        } while (isRunning);
        return null;
    }

    static Transaksi ambilCucian(Transaksi transaksi) {
        return transaksi;
    }
}

//Transaksi hasRow = cari transaksi by noKwitansi
//if hasRow != null then
//