package com.laundry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    static void print(String value) {
        System.out.println(value);
    }

    static int showListMenu() {
        System.out.println("=== MENU UTAMA E-LAUNDRY ===");
        Scanner input = new Scanner(System.in);
        String[] listMenu = new String[]{"Tambah Transaksi", "Ambil Cucian", "Laporan Harian", "Keluar Aplikasi"};

        for (int i = 0; i < listMenu.length; i++) {
            String menu = listMenu[i];
            System.out.println(i + 1 + ". " + menu);
        }

        System.out.print("Masukan pilihan kamu: ");
        return input.nextInt();
    }

    public static <T> T[] push(T[] arr, T item) {
        T[] tmp = Arrays.copyOf(arr, arr.length + 1);
        tmp[tmp.length - 1] = item;
        return tmp;
    }

    static void showLaundryList(List<Laundry> laundryList) {
        String[] laundryString = {};
        for (Laundry value : laundryList) {
            laundryString = push(laundryString, String.valueOf(value.getId()) +
                    ". " + value.getName() +
                    " - Rp" + String.format("%,.0f", value.getPrice()));
        }
        System.out.println("Mau nyuci apa ?\n" + String.join("\n", laundryString));
        System.out.print("Masukan pilihan anda: ");
    }

    static void showServiceList(List<Service> serviceList) {
        String[] serviceString = {};
        for (Service value : serviceList) {
            String price = value.getPrice() > 0 ? " +Rp" + String.format("%,.0f", value.getPrice()) : "";
            serviceString = push(serviceString, String.valueOf(value.getId()) +
                    ". " + value.getName() + price);
        }
        System.out.println("Mau pakai service apa ?\n" + String.join("\n", serviceString));
        System.out.print("Masukan pilihan anda: ");
    }

    static Laundry findLaundryItem(int selected, List<Laundry> laundryList) {
        return laundryList.get(selected - 1);
    }

    static Service findServiceItem(int selected, List<Service> serviceList) {
        return serviceList.get(selected - 1);
    }

    static boolean isBackToMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("1. Kembali ke Menu Utama\n2. Keluar Aplikasi");
        System.out.print("Masukan pilihan anda: ");
        return input.nextInt() == 1;
    }

    static Transaksi findTransaksiByKwitansi(String noKwitansi, List<Transaksi> listTransaction) {
        for (Transaksi transaksi : listTransaction) {
            if (transaksi.getNoKwitansi().equals(noKwitansi)) {
                return transaksi;
            }
        }

        return null;
    }

    static List<Transaksi> findTransaksiByPhone(String phone, List<Transaksi> listTransaction) {
        List<Transaksi> tempListTransaksi = new ArrayList<>();
        for (Transaksi transaksi : listTransaction) {
            if (transaksi.getPelanggan().getPhone().equals(phone)) {
                tempListTransaksi.add(transaksi);
            }
        }


        return tempListTransaksi;
    }

    static boolean addMoreLaundry() {
        Scanner input = new Scanner(System.in);
        System.out.println("1. Tambah Cucian\n2. Bayar");
        System.out.print("Masukan pilihan anda: ");
        return input.nextInt() == 1;
    }

    static void showKwitansi(Transaksi transaksi) throws ParseException {
        List<Cart> carts = transaksi.getCarts();
        float totalBill = 0;

        System.out.println("---------------------------------------------");
        System.out.println("No. Kwitansi: " + transaksi.getNoKwitansi());
        System.out.println("Tanggal: " + transaksi.getCreatedAt());
        System.out.println("Nama: " + transaksi.getPelanggan().getName());
        System.out.println("No. Hp: " + transaksi.getPelanggan().getPhone());
        System.out.println("---------------------------------------------");
        for (int i = 0; i < carts.size(); i++) {
            Cart item = carts.get(i);
            String qty = item.getLaundry().getType() == TypeLaundry.KILOAN ? String.valueOf(item.getWeight()) + " Kg"
                    : String.valueOf(item.getQty()) + " Pcs";
            Utils.print(String.valueOf(i + 1) + ". " + item.getLaundry().getName() + " " + qty + " " +
                    "(" + item.getService().getName() + ") Rp" + String.format("%,.0f", item.getTotalPrice()));
            Utils.print("Estimasi ambil cucian : " + getEstimateFrom(transaksi.getCreatedAt(), item.getService().getEstimate()));
            totalBill += item.getTotalPrice();
        }
        System.out.println("---------------------------------------------");
        System.out.println("TOTAL BAYAR: Rp" + String.format("%,.0f", totalBill));
//        System.out.println("ESTIMASI AMBIL CUCIAN: " + getEstimateFrom(transaksi.getCreatedAt()));
        System.out.println("---------------------------------------------");
        System.out.println("");
    }

    static String getEstimateFrom(String dateString, int estimate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH).parse(dateString);
        cal.setTime(date);
        cal.add(Calendar.HOUR, +estimate);
        Date result = cal.getTime();
        DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dtf.format(result);
    }
}
