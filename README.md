# Aplikasi Kasir Kantin Kampus

## Identitas
- Nama: Asti Atiyah
- NIM: C2483207016
- Kelas: PTI5
- Mata Kuliah: Pemrograman Visual

## Deskripsi
Aplikasi desktop sederhana untuk mencatat transaksi satu jenis makanan atau minuman, menghitung total harga, menerima pembayaran, dan menghitung uang kembalian. Aplikasi dibuat menggunakan Java Swing tanpa database.

## Fitur
- Input nama makanan/minuman
- Input harga satuan dan jumlah pembelian
- Menghitung total harga
- Memproses pembayaran dan menghitung kembalian
- Memberikan peringatan jika pembayaran kurang
- Validasi input kosong dan input angka
- Reset formulir transaksi

## Komponen Java Swing
JFrame, JPanel, JLabel, JTextField, JButton, JOptionPane, BorderLayout, GridLayout, FlowLayout.

## Cara Menjalankan di Apache NetBeans
1. Buka Apache NetBeans.
2. Pilih **File > New Project > Java with Ant > Java Application**.
3. Beri nama proyek `KasirKantin`.
4. Buat Java Class bernama `KasirKantin`.
5. Salin isi file `KasirKantin.java` ke class tersebut.
6. Klik kanan file `KasirKantin.java`, lalu pilih **Run File**.

## Pengujian
| Nama Barang | Harga | Jumlah | Pembayaran | Hasil |
|---|---:|---:|---:|---|
| Nasi Goreng | 15000 | 2 | 50000 | Total Rp30.000, kembalian Rp20.000 |
| Mie Ayam | 12000 | 3 | 40000 | Total Rp36.000, kembalian Rp4.000 |
| Es Teh | 5000 | 4 | 10000 | Total Rp20.000, pembayaran kurang Rp10.000 |

Simpan tangkapan layar tampilan utama, hasil perhitungan total, dan hasil pembayaran ke folder `screenshots`, lalu tampilkan di README dengan Markdown.
