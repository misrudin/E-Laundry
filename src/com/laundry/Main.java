package com.laundry;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        String title = "\n" +
                "               ███████     ██       █████  ██    ██ ███    ██ ██████  ██████  ██    ██ \n" +
                "               ██          ██      ██   ██ ██    ██ ████   ██ ██   ██ ██   ██  ██  ██  \n" +
                "               █████       ██      ███████ ██    ██ ██ ██  ██ ██   ██ ██████    ████   \n" +
                "               ██          ██      ██   ██ ██    ██ ██  ██ ██ ██   ██ ██   ██    ██    \n" +
                "               ███████     ███████ ██   ██  ██████  ██   ████ ██████  ██   ██    ██    \n";
        System.out.println("=============================================== HALO ================================================");
        System.out.println(title);
        System.out.println("======================================================================================================");

        boolean isRunningApp = true;
        List<Transaksi> listTransaction = new ArrayList<>();

        do {
            int selectedMenu = Utils.showListMenu();
            switch (selectedMenu) {
                case 1:
                    Transaksi item = TambahTransaksi.tambahTransaksi();
                    listTransaction.add(item);
                    break;
                case 2:
                    AmbilCucian.ambilCucian(listTransaction);
                    break;
                case 3:
                    LaporanHarian.laporanHarian(listTransaction);
                    break;
                case 4:
                    isRunningApp = false;
                    break;
                default:
                    System.out.println("+-----------------------+");
                    System.out.println("+  Menu tidak tersedia! +");
                    System.out.println("+-----------------------+");
                    break;
            }
        } while (isRunningApp);
    }
}
