package com.laundry;

import java.sql.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    static void print(String value) {
        System.out.println(value);
    }

    public static <T> T[] push(T[] arr, T item) {
        T[] tmp = Arrays.copyOf(arr, arr.length + 1);
        tmp[tmp.length - 1] = item;
        return tmp;
    }

    static int showListMenu() {
        System.out.println("SELAMAT DATANG DI MENU UTAMA");
        Scanner input = new Scanner(System.in);
        String space = "   ";
        String[] menuTemporary = new String[0];
        String[] listMenu = new String[]{"Tambah Transaksi", "Ambil Cucian", "Laporan Harian", "Keluar Aplikasi"};
        System.out.println("+----------------------------------------------------------------------------------------------------+");
        for (int i = 0; i < listMenu.length; i++) {
            String menu = listMenu[i];
            menuTemporary = push(menuTemporary, "["+String.valueOf(i+1)+"] " + menu);
        }
        System.out.println("|"+space + String.join(space + "|" + space, menuTemporary)+ space + "|");
        System.out.println("+----------------------------------------------------------------------------------------------------+");

        System.out.print("Masukan pilihan kamu: ");
        return input.nextInt();
    }

    static void showLaundryList(List<Laundry> laundryList) {
        System.out.println("Mau nyuci apa ?");
        Table table = new Table();
        List<String> headers = new ArrayList<>();
        headers.add("No.");
        headers.add("Jenis");
        headers.add("Harga");;
        List<List<String>> rows = new ArrayList<>();

        for (int i = 0; i < laundryList.size(); i++) {
            Laundry item = laundryList.get(i);
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(item.getId()));
            row.add(item.getName());
            row.add(String.format("%,.0f", item.getPrice()));
            rows.add(row);
        }

        System.out.println(table.generateTable(headers, rows));
        System.out.print("Masukan pilihan anda: ");
    }

    static void showServiceList(List<Service> serviceList) {
        System.out.println("Mau pakai service apa ?");
        Table table = new Table();
        List<String> headers = new ArrayList<>();
        headers.add("No.");
        headers.add("Nama Service");
        headers.add("Harga+");;
        List<List<String>> rows = new ArrayList<>();

        for (int i = 0; i < serviceList.size(); i++) {
            Service item = serviceList.get(i);
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(item.getId()));
            row.add(item.getName());
            row.add(String.format("%,.0f", item.getPrice()));
            rows.add(row);
        }

        System.out.println(table.generateTable(headers, rows));
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
        System.out.println("+=================================+");
        System.out.println("|   [1] MENU UTAMA | [2] KELUAR   |");
        System.out.println("+=================================+");
        System.out.print("Masukan pilihan anda: ");
        return input.nextInt() == 1;
    }

    static Transaksi findTransaksiByKwitansi(String noKwitansi, List<Transaksi> listTransaction) {
        for (Transaksi transaksi : listTransaction) {
            if (transaksi.noKwitansi.equals(noKwitansi)) {
                return transaksi;
            }
        }
        return null;
    }

    static List<Transaksi> findTransaksiByPhone(String phone, List<Transaksi> listTransaction) {
        return listTransaction.stream()
                .filter(item -> item.pelanggan.phone.equals(phone))
                .collect(Collectors.toList());
    }

    static boolean addMoreLaundry() {
        Scanner input = new Scanner(System.in);
        System.out.println("+===================================+");
        System.out.println("|   [1] TAMBAH CUCIAN | [2] BAYAR   |");
        System.out.println("+===================================+");
        System.out.print("Masukan pilihan anda: ");
        return input.nextInt() == 1;
    }

    static void showKwitansi(Transaksi transaksi, float kembalian) throws ParseException {
        List<Cart> carts = transaksi.getCarts();
        float totalBill = 0;

        System.out.println("============================================================");
        System.out.println("No. Kwitansi: " + transaksi.getNoKwitansi());
        System.out.println("Tanggal: " + transaksi.getCreatedAt());
        System.out.println("Nama: " + transaksi.getPelanggan().getName());
        System.out.println("No. Hp: " + transaksi.getPelanggan().getPhone());
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < carts.size(); i++) {
            Cart item = carts.get(i);
            String qty = item.getLaundry().getType() == TypeLaundry.KILOAN ? String.valueOf(item.getWeight()) + " Kg"
                    : String.valueOf(item.getQty()) + " Pcs";
            Utils.print(String.valueOf(i + 1) + ". " + item.getLaundry().getName() + " " + qty + " " +
                    "(" + item.getService().getName() + ") Rp" + String.format("%,.0f", item.getTotalPrice()));
            Utils.print("   Dapat diambil pada : " + getEstimateFrom(transaksi.getCreatedAt(), item.getService().getEstimate()));
            totalBill += item.getTotalPrice();
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("TOTAL BAYAR: Rp" + String.format("%,.0f", totalBill));
        System.out.println("============================================================");
        if(kembalian > 0) {
            System.out.println("Nominal Kembalian: Rp"+ String.format("%,.0f", kembalian));
        }
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

    //print table transaksi
    static void printTransaksiIntoTable(List<Transaksi> listTransaction) {
        Table table = new Table();
        List<String> headers = new ArrayList<>();
        headers.add("No. Kwitansi");
        headers.add("Tgl. Transaksi");
        headers.add("Tgl. Ambil");
        headers.add("Nama");
        headers.add("No. Hp");
        headers.add("Jumlah");
        headers.add("Status");
        List<List<String>> rows = new ArrayList<>();


        for (int i = 0; i < listTransaction.size(); i++) {
            Transaksi item = listTransaction.get(i);
            List<String> row = new ArrayList<>();
            row.add(item.getNoKwitansi());
            row.add(item.getCreatedAt());
            row.add(item.getStatus() == StatusTransaksi.PROCESS ? "-" : item.getUpdateAt());
            row.add(item.getPelanggan().getName());
            row.add(item.getPelanggan().getPhone());
            row.add("Rp"+String.format("%,.0f", item.getTotalPrice()));
            row.add(item.getStatus().status);

            rows.add(row);
        }

        System.out.println(table.generateTable(headers, rows));
    }

    static String getDateTimeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    static String generateKwitansi() {
        DateTimeFormatter dateFormatterKwitansi = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return dateFormatterKwitansi.format(now);
    }
}
