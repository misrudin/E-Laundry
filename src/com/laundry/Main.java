package com.laundry;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        System.out.println("---------------------");
        System.out.println("----- E Laundry -----");
        System.out.println("---------------------");

        boolean isRunningApp = true;
        List<Transaksi> listTransaction = new ArrayList<>();

        do {
            int selectedMenu = Utils.showListMenu();
            switch (selectedMenu) {
                case 1:
                    Transaksi item = TambahTransaksi.tambahTransaksi();
                    listTransaction.add(item);
                    isRunningApp = Utils.isBackToMenu();
                    break;
                case 2:
                    AmbilCucian.ambilCucian(listTransaction);
                    isRunningApp = Utils.isBackToMenu();
                    break;
                case 3:
                    LaporanHarian.laporanHarian(listTransaction);
                    isRunningApp = Utils.isBackToMenu();
                    break;
                case 4:
                    isRunningApp = false;
                    break;
                default:
                    System.out.println("Pilihan menu tidak sesuai");
                    break;
            }
        } while (isRunningApp);
    }
}
