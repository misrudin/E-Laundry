package com.laundry;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TambahTransaksi {
    public static Transaksi tambahTransaksi() throws ParseException {
        Scanner input = new Scanner(System.in);
        Pelanggan pelanggan = new Pelanggan("", "");
        List<Cart> carts = new ArrayList<>();
        List<Laundry> laundryList = new ArrayList<>();
        List<Service> serviceList = new ArrayList<>();
        boolean isInputLaundry = true;
        int id = 1;
        float totalBill = 0;

        laundryList.add(new Laundry(1, "Pakaian", 8000, TypeLaundry.KILOAN));
        laundryList.add(new Laundry(2, "Selimut", 20000, TypeLaundry.SATUAN));
        laundryList.add(new Laundry(3, "Boneka", 8000, TypeLaundry.KILOAN));
        laundryList.add(new Laundry(4, "Sepatu Dewasa", 13000, TypeLaundry.SATUAN));
        laundryList.add(new Laundry(5, "Sepatu Anak", 7000, TypeLaundry.SATUAN));

        serviceList.add(new Service(1, "Regular", 0, 72));
        serviceList.add(new Service(2, "Express", 5000, 12));
        serviceList.add(new Service(3, "Regular - Antar Jemput", 7000, 72));
        serviceList.add(new Service(4, "Express - Antar Jemput", 7000, 12));
        serviceList.add(new Service(5, "Super Express", 7000, 0.05));

        System.out.print("Silahkan masukan nama pelanggan: ");
        String name = input.nextLine();
        pelanggan.setName(name);
        System.out.print("Silahkan masukan nomor hp pelanggan: ");
        String phone = input.nextLine();
        pelanggan.setPhone(phone);

        Utils.showServiceList(serviceList);
        int selectedService = input.nextInt();
        Service service = Utils.findServiceItem(selectedService, serviceList);

        do {
            float weight = 0;
            int qty = 0;

            Utils.showLaundryList(laundryList);
            int selectedLaundry = input.nextInt();
            Laundry laundry = Utils.findLaundryItem(selectedLaundry, laundryList);

            switch (laundry.getType()) {
                case KILOAN:
                    System.out.print("Silahkan masukan berat: ");
                    weight = input.nextInt();
                    break;
                case SATUAN:
                    System.out.print("Silahkan masukan jumlah: ");
                    qty = input.nextInt();
                    break;
                default:
                    break;
            }

            carts.add(new Cart(id, laundry, service, qty, weight));
            id++;

            isInputLaundry = addMoreLaundry();
        } while (isInputLaundry);

        System.out.println("=============== DETAIL TRANSAKSI ==================");
        for (int i = 0; i < carts.size(); i++) {
            Cart item = carts.get(i);
            String qty = item.getLaundry().getType() == TypeLaundry.KILOAN ? String.valueOf(item.getWeight()) + " Kg"
                    : String.valueOf(item.getQty()) + " Pcs";
            System.out.println(String.valueOf(i + 1) + ". " + item.getLaundry().getName() + " " + qty + " " +
                    "(" + item.getService().getName() + ") Rp" + String.format("%,.0f", item.getTotalPrice()));
            totalBill += item.getTotalPrice();
        }
        System.out.println("---------------------------------------------------");
        System.out.println("TOTAL HARUS DIBAYAR: Rp" + String.format("%,.0f", totalBill));
        System.out.println("===================================================");

        boolean isPaymentProcess = false;
        float kembalian = 0;

        do {
            System.out.print("Masukan nominal bayar (Rp): ");
            float nominalPay = input.nextInt();
            if (nominalPay < totalBill) {
                System.out.println("Nominal yang anda masukan kurang!");
                isPaymentProcess = true;
            } else {
                System.out.println("                      TRANSAKSI BERHASIL!                       ");
                kembalian = nominalPay - totalBill;
                isPaymentProcess = false;
            }
        } while (isPaymentProcess);


        Transaksi transaksi = new Transaksi(Utils.generateKwitansi(),
                                            pelanggan,
                                            carts,
                                            Utils.getDateTimeNow(),
                                            totalBill,
                                            service);
        Utils.showKwitansi(transaksi, kembalian);
        return transaksi;
    }

    static boolean addMoreLaundry() {
        Scanner input = new Scanner(System.in);
        System.out.println("+===================================+");
        System.out.println("|   [1] TAMBAH CUCIAN | [2] BAYAR   |");
        System.out.println("+===================================+");
        System.out.print("Masukan pilihan anda: ");
        return input.nextInt() == 1;
    }
}
