# Sistem Manajemen Cuti (Leave Management System) - LMS CoolYeah

Aplikasi *mobile* berbasis Android (Kotlin & XML) yang dikembangkan untuk memenuhi tugas pembuatan antarmuka aplikasi sistem autentikasi dan *dashboard multi-role*. Aplikasi ini mengambil tema **Sistem Manajemen Cuti Karyawan**, di mana pengguna akan mendapatkan hak akses dan tampilan *dashboard* yang berbeda secara dinamis sesuai dengan jabatannya (*role*).

*Catatan:*
*Versi saat ini (v1.0) difokuskan pada penyelesaian arsitektur User Interface (UI), validasi form login, dan logika Dynamic Layout (hide/show komponen). Data yang ditampilkan pada tabel sisa cuti, riwayat cuti, dan daftar approval saat ini masih berupa data statis (Mockup/Dummy) dan belum terintegrasi dengan Backend API atau Database pada iterasi tugas ini.*

## Fitur Utama
- **Autentikasi Sederhana (Statik):** Login sistem dengan validasi input kosong dan pengecekan *username/password* secara *hardcoded* untuk keperluan demonstrasi UI.
- **Dynamic Multi-Role Dashboard:** Memiliki 2 *role* berbeda (Admin & Karyawan). Tampilan komponen UI seperti, *Button*, dan *Table* di dalam satu *layout* Dashboard akan otomatis menyesuaikan (*hide/show*) berdasarkan *role* yang sedang login.
- **Modern Material UI:** Antarmuka dibangun menggunakan komponen standar industri dari `Google Material Design` (`TextInputLayout`, `MaterialButton`) serta `TableLayout` untuk menampilkan data tabular yang responsif.
- **Navigation & Backstack Management:** Fungsionalitas *Logout* yang aman dengan menerapkan pembersihan riwayat aktivitas (`Intent.FLAG_ACTIVITY_CLEAR_TASK`), mencegah pengguna kembali ke halaman *dashboard* menggunakan tombol *back* *smartphone* setelah *logout*.
- **Form Pengajuan Cuti (3rd Activity):** Halaman khusus pengajuan cuti yang dilengkapi dengan *Dropdown* (AutoCompleteTextView) dan validasi kalender (*DatePickerDialog*).
- **Validasi Tanggal Pintar:** Implementasi `minDate` pada kalender sehingga pengguna tidak bisa memilih tanggal di masa lalu (sebelum hari ini), dan tanggal selesai tidak bisa kurang dari tanggal mulai.
- **Navigation Flow:** Alur perpindahan antar 3 halaman (Login -> Dashboard -> Form) menggunakan `Intent` dan penanganan *backstack* saat *logout*.

## Struktur Hak Akses (2 Roles)
Sistem ini membagi pengguna ke dalam 2 tingkatan *role* utama dengan visibilitas komponen *dashboard* sebagai berikut:

| Role | Info Sisa Cuti | Pengajuan Cuti | Riwayat Cuti Pribadi | Approval Cuti | Riwayat Semua Karyawan | Kelola Data Karyawan |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **KARYAWAN** | V | V | V | x | x | x |
| **ADMIN** | x | x | x | V | V | V |

## Teknologi yang Digunakan
* **Bahasa Pemrograman:** Kotlin
* **Markup Antarmuka:** XML
* **Minimum SDK:** API 31 (Android 12.0 S)
* **IDE:** Android Studio
* **Library Tambahan:** Google Material Components (MDC)

## Cara Instalasi dan Pengujian

### 1. Persiapan Project di Android Studio
1. Ekstrak file *source code* (ZIP) aplikasi ini, atau *clone* dari repositori GitHub.
2. Buka **Android Studio**, pilih **File -> Open**, dan arahkan ke folder proyek `LMS CoolYeah` yang sudah diekstrak.
3. Tunggu hingga proses **Gradle Sync** selesai secara otomatis (pastikan koneksi internet stabil untuk mengunduh *dependencies* material design).

### 2. Konfigurasi dan Pengujian Login
Karena versi ini belum menggunakan *database* eksternal, pengecekan kredensial dilakukan di dalam `MainActivity.kt`. Untuk menguji coba masing-masing *role*, jalankan aplikasi menggunakan Emulator atau *Device* fisik, lalu gunakan data *dummy* berikut pada form Login:

**Akses sebagai ADMIN:**
* **Username:** `admin`
* **Password:** `admin123`

**Akses sebagai KARYAWAN:**
* **Username:** `hilman`
* **Password:** `user123`

---
*Dibuat untuk memenuhi UTS - Pemrograman Mobile I.*

**Nama:** Mochamad Hilman Fahmi
**NIM:** 24552011058