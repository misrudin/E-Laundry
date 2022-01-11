package com.laundry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Utils {
    //push array
    public static <T> T[] push(T[] arr, T item) {
        T[] tmp = Arrays.copyOf(arr, arr.length + 1);
        tmp[tmp.length - 1] = item;
        return tmp;
    }

    //output main menu
    public static int showListMenu() {
        System.out.println("SELAMAT DATANG DI MENU UTAMA");
        Scanner input = new Scanner(System.in);
        String space = "   ";
        String[] menuTemporary = new String[0];
        String[] listMenu = new String[]{"Tambah Transaksi", "Ambil Cucian", "Laporan Harian", "Keluar Aplikasi"};
        System.out.println("+----------------------------------------------------------------------------------------------------+");
        for (int i = 0; i < listMenu.length; i++) {
            String menu = listMenu[i];
            menuTemporary = push(menuTemporary, "[" + String.valueOf(i + 1) + "] " + menu);
        }
        System.out.println("|" + space + String.join(space + "|" + space, menuTemporary) + space + "|");
        System.out.println("+----------------------------------------------------------------------------------------------------+");

        System.out.print("Masukan pilihan kamu: ");
        return input.nextInt();
    }

    //output laoundry list
    public static void showLaundryList(List<Laundry> laundryList) {
        System.out.println("Mau nyuci apa ?");
        Table table = new Table();
        List<String> headers = new ArrayList<>();
        headers.add("No.");
        headers.add("Jenis");
        headers.add("Harga");
        ;
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

    //ouput service list
    public static void showServiceList(List<Service> serviceList) {
        System.out.println("Mau pakai service apa ?");
        Table table = new Table();
        List<String> headers = new ArrayList<>();
        headers.add("No.");
        headers.add("Nama Service");
        headers.add("Harga+");
        ;
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

    //selected item laundry by index input
    public static Laundry findLaundryItem(int selected, List<Laundry> laundryList) {
        return laundryList.get(selected - 1);
    }

    //selected item service by index input
    public static Service findServiceItem(int selected, List<Service> serviceList) {
        return serviceList.get(selected - 1);
    }

    //find transaksi by nomor kwitansi
    public  static Transaksi findTransaksiByKwitansi(String noKwitansi, List<Transaksi> listTransaction) {
        for (Transaksi transaksi : listTransaction) {
            if (transaksi.noKwitansi.equals(noKwitansi)) {
                return transaksi;
            }
        }
        return null;
    }

    //filter list transaksi bt phone number
    public static List<Transaksi> findTransaksiByPhone(String phone, List<Transaksi> listTransaction) {
        return listTransaction.stream()
                .filter(item -> item.pelanggan.phone.equals(phone))
                .collect(Collectors.toList());
    }

    //output kwitansi/invoice
    public static void showKwitansi(Transaksi transaksi, float kembalian) throws ParseException {
        List<Cart> carts = transaksi.getCarts();
        float totalBill = 0;

        System.out.println("============================================================");
        System.out.println("No. Kwitansi    : " + transaksi.getNoKwitansi());
        System.out.println("Tanggal         : " + transaksi.getCreatedAt());
        System.out.println("Nama            : " + transaksi.getPelanggan().getName());
        System.out.println("No. Hp          : " + transaksi.getPelanggan().getPhone());
        System.out.println("Service         : " + transaksi.getService().getName());
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < carts.size(); i++) {
            Cart item = carts.get(i);
            String qty = item.getLaundry().getType() == TypeLaundry.KILOAN ? String.valueOf(item.getWeight()) + " Kg"
                    : String.valueOf(item.getQty()) + " Pcs";
            System.out.println(String.valueOf(i + 1) + ". " + item.getLaundry().getName() + " " + qty + " " +
                    "(" + item.getService().getName() + ") Rp" + String.format("%,.0f", item.getTotalPrice()));
            totalBill += item.getTotalPrice();
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Status              : "+ transaksi.getStatus().status);
        System.out.println("Total Tagihan       : Rp" + String.format("%,.0f", totalBill));
        System.out.println("Dapat diambil pada  : " + getEstimateFrom(transaksi.getCreatedAt(), transaksi.getService().getEstimate()));

        System.out.println("============================================================");
        if (kembalian > 0) {
            System.out.println("Nominal Kembalian: Rp" + String.format("%,.0f", kembalian));
        }
    }

    //return a string now date +estimate by hour
    public static String getEstimateFrom(String dateString, double estimate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH).parse(dateString);
        cal.setTime(date);
        int estimete = (int) estimate;
        cal.add(Calendar.HOUR, + estimete);
        Date result = cal.getTime();
        DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return dtf.format(result);
    }

    //print table transaksi
    public static void printTransaksiIntoTable(List<Transaksi> listTransaction) {
        Table table = new Table();
        List<String> headers = new ArrayList<>();
        headers.add("No. Kwitansi");
        headers.add("Tgl. Transaksi");
        headers.add("Tgl. Ambil");
        headers.add("Service");
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
            row.add(item.getService().getName());
            row.add(item.getPelanggan().getName());
            row.add(item.getPelanggan().getPhone());
            row.add("Rp" + String.format("%,.0f", item.getTotalPrice()));
            row.add(item.getStatus().status);

            rows.add(row);
        }

        System.out.println(table.generateTable(headers, rows));
    }

    //time now in string
    public static String getDateTimeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    //kwitansi string
    public static String generateKwitansi() {
        DateTimeFormatter dateFormatterKwitansi = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return dateFormatterKwitansi.format(now);
    }

    //diference date now && transaksi created at
    public static long dateDiff(String createdAt) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date dateNow = new Date();
        Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH).parse(formatter.format(dateNow));
        Date date2 = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH).parse(createdAt);

        long diffInMillies = date2.getTime() - date1.getTime();
        TimeUnit timeUnit = TimeUnit.MINUTES;
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
