package com.laundry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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

        Pelanggan pelanggan1 = new Pelanggan("udin", "083844015132");
        Pelanggan pelanggan2 = new Pelanggan("eka", "083123");

        Laundry item1 = new Laundry(1, "Pakaian", 8000, TypeLaundry.KILOAN);
        Laundry item2 = new Laundry(2, "Selimut", 20000, TypeLaundry.KILOAN);

        Service service1 = new Service(1, "Regular", 0, 72);
        Service service2 = new Service(2, "Express", 5000, 12);

        List<Cart> carts1 = new ArrayList<>();
        carts1.add(new Cart(1, item1, service1, 0, 1));
        carts1.add(new Cart(2, item2, service1, 0, 1));

        List<Cart> carts2 = new ArrayList<>();
        carts2.add(new Cart(1, item1, service2, 0, 1));
        carts2.add(new Cart(2, item2, service2, 0, 1));

        listTransaction.add(new Transaksi("12345",pelanggan1, carts1, "2020/01/10 18:00", 10000, service1));
        listTransaction.add(new Transaksi("12222",pelanggan2, carts2, "2020/01/10 19:00", 10000, service2));

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
