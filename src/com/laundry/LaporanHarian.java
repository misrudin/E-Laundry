package com.laundry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LaporanHarian {
    static void laporanHarian(List<Transaksi> listTransaction) {
        if (listTransaction.size() == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        Table table = new Table();
        List<String> headers = new ArrayList<>();
        headers.add("No. Kwitansi");
        headers.add("Tanggal");
        headers.add("Nama");
        headers.add("No. Hp");
        headers.add("Jumlah");
        headers.add("Status");
        List<List<String>> rows = new ArrayList<>();
        float total = 0;

        for (int i = 0; i < listTransaction.size(); i++) {
            Transaksi item = listTransaction.get(i);
            total += item.getTotalPrice();
            List<String> row = new ArrayList<>();
            row.add(item.getNoKwitansi());
            row.add(item.getCreatedAt());
            row.add(item.getPelanggan().getName());
            row.add(item.getPelanggan().getPhone());
            row.add("Rp"+String.format("%,.0f", item.getTotalPrice()));
            row.add(item.getStatus().status);

            rows.add(row);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String timeNow = dtf.format(now);
        System.out.println(table.generateTable(headers, rows));
        System.out.println("Laporan Keuangan Tangal " + timeNow + " : Rp" + String.format("%,.0f", total));
    }
}
