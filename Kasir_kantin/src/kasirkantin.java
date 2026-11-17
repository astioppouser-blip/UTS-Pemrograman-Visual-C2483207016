import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class kasirkantin extends JFrame {

    private JTextField txtNama;
    private JTextField txtHarga;
    private JTextField txtJumlah;
    private JTextField txtPembayaran;

    private JLabel lblTotal;
    private JLabel lblKembalian;

    private long totalHarga = 0;
    private boolean totalSudahDihitung = false;

    private final NumberFormat rupiah =
            NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public kasirkantin() {
        setTitle("Aplikasi Kasir Kantin Kampus");
        setSize(520, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        buatTampilan();
    }

    private void buatTampilan() {
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(new EmptyBorder(20, 25, 20, 25));
        panelUtama.setBackground(new Color(245, 248, 252));

        JLabel judul = new JLabel("KASIR KANTIN KAMPUS", SwingConstants.CENTER);
        judul.setFont(new Font("Arial", Font.BOLD, 22));
        judul.setForeground(new Color(25, 75, 120));
        panelUtama.add(judul, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 12));
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 230)),
                new EmptyBorder(20, 15, 20, 15)
        ));

        txtNama = new JTextField();
        txtHarga = new JTextField();
        txtJumlah = new JTextField();
        txtPembayaran = new JTextField();

        lblTotal = new JLabel("Rp0");
        lblKembalian = new JLabel("Rp0");

        tambahBaris(panelForm, "Nama Makanan/Minuman", txtNama);
        tambahBaris(panelForm, "Harga Satuan (Rp)", txtHarga);
        tambahBaris(panelForm, "Jumlah Pembelian", txtJumlah);
        tambahBaris(panelForm, "Uang Pembayaran (Rp)", txtPembayaran);
        tambahBaris(panelForm, "Total Harga", lblTotal);
        tambahBaris(panelForm, "Uang Kembalian", lblKembalian);

        panelUtama.add(panelForm, BorderLayout.CENTER);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        panelTombol.setBackground(new Color(245, 248, 252));

        JButton btnHitung = new JButton("Hitung");
        JButton btnBayar = new JButton("Bayar");
        JButton btnReset = new JButton("Reset");

        btnHitung.setBackground(new Color(45, 120, 190));
        btnBayar.setBackground(new Color(40, 150, 90));
        btnReset.setBackground(new Color(190, 70, 70));

        btnHitung.setForeground(Color.WHITE);
        btnBayar.setForeground(Color.WHITE);
        btnReset.setForeground(Color.WHITE);

        btnHitung.setFocusPainted(false);
        btnBayar.setFocusPainted(false);
        btnReset.setFocusPainted(false);

        panelTombol.add(btnHitung);
        panelTombol.add(btnBayar);
        panelTombol.add(btnReset);

        panelUtama.add(panelTombol, BorderLayout.SOUTH);

        btnHitung.addActionListener(e -> hitungTotal());
        btnBayar.addActionListener(e -> prosesPembayaran());
        btnReset.addActionListener(e -> resetForm());

        setContentPane(panelUtama);
        getRootPane().setDefaultButton(btnHitung);
    }

    private void tambahBaris(JPanel panel, String teks, JComponent komponen) {
        JLabel label = new JLabel(teks);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(label);

        if (komponen instanceof JLabel) {
            ((JLabel) komponen).setFont(new Font("Arial", Font.BOLD, 14));
            ((JLabel) komponen).setForeground(new Color(25, 100, 65));
        } else {
            ((JTextField) komponen).setFont(new Font("Arial", Font.PLAIN, 14));
        }

        panel.add(komponen);
    }

    private boolean validasiDataBarang() {
        String nama = txtNama.getText().trim();
        String hargaText = txtHarga.getText().trim();
        String jumlahText = txtJumlah.getText().trim();

        if (nama.isEmpty() || hargaText.isEmpty() || jumlahText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nama barang, harga, dan jumlah wajib diisi.",
                    "Input Belum Lengkap", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!hargaText.matches("\\d+") || !jumlahText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "Harga dan jumlah harus diisi menggunakan angka saja.",
                    "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            long harga = Long.parseLong(hargaText);
            long jumlah = Long.parseLong(jumlahText);

            if (harga < 0) {
                JOptionPane.showMessageDialog(this,
                        "Harga tidak boleh bernilai negatif.",
                        "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Jumlah pembelian harus lebih dari nol.",
                        "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            totalHarga = Math.multiplyExact(harga, jumlah);
            return true;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Angka terlalu besar atau format angka tidak valid.",
                    "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(this,
                    "Total harga terlalu besar untuk dihitung.",
                    "Kesalahan Perhitungan", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void hitungTotal() {
        if (!validasiDataBarang()) {
            totalSudahDihitung = false;
            return;
        }

        lblTotal.setText(rupiah.format(totalHarga));
        lblKembalian.setText("Rp0");
        totalSudahDihitung = true;

        JOptionPane.showMessageDialog(this,
                "Total harga " + txtNama.getText().trim()
                        + " adalah " + rupiah.format(totalHarga),
                "Hasil Perhitungan", JOptionPane.INFORMATION_MESSAGE);
    }

    private void prosesPembayaran() {
        if (!validasiDataBarang()) {
            totalSudahDihitung = false;
            return;
        }

        String pembayaranText = txtPembayaran.getText().trim();

        if (pembayaranText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Uang pembayaran wajib diisi.",
                    "Input Belum Lengkap", JOptionPane.WARNING_MESSAGE);
            txtPembayaran.requestFocus();
            return;
        }

        if (!pembayaranText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "Uang pembayaran harus berupa angka saja.",
                    "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
            txtPembayaran.requestFocus();
            return;
        }

        try {
            long pembayaran = Long.parseLong(pembayaranText);

            if (pembayaran < 0) {
                JOptionPane.showMessageDialog(this,
                        "Uang pembayaran tidak boleh negatif.",
                        "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
                return;
            }

            lblTotal.setText(rupiah.format(totalHarga));
            totalSudahDihitung = true;

            if (pembayaran < totalHarga) {
                long kekurangan = totalHarga - pembayaran;
                lblKembalian.setText("Pembayaran kurang");
                JOptionPane.showMessageDialog(this,
                        "Pembayaran kurang " + rupiah.format(kekurangan)
                                + ". Silakan tambahkan pembayaran.",
                        "Pembayaran Tidak Cukup", JOptionPane.WARNING_MESSAGE);
            } else {
                long kembalian = pembayaran - totalHarga;
                lblKembalian.setText(rupiah.format(kembalian));

                JOptionPane.showMessageDialog(this,
                        "Pembayaran berhasil!\n"
                                + "Nama barang: " + txtNama.getText().trim() + "\n"
                                + "Total harga: " + rupiah.format(totalHarga) + "\n"
                                + "Uang dibayar: " + rupiah.format(pembayaran) + "\n"
                                + "Uang kembalian: " + rupiah.format(kembalian),
                        "Transaksi Berhasil", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Nominal pembayaran terlalu besar atau tidak valid.",
                    "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        txtNama.setText("");
        txtHarga.setText("");
        txtJumlah.setText("");
        txtPembayaran.setText("");

        lblTotal.setText("Rp0");
        lblKembalian.setText("Rp0");

        totalHarga = 0;
        totalSudahDihitung = false;

        txtNama.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            kasirkantin aplikasi = new kasirkantin();
            aplikasi.setVisible(true);
            aplikasi.txtNama.requestFocusInWindow();
        });
    }
}
