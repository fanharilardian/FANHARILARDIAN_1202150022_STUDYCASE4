package android.fanharil.com.fanharilardian_1202150022_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListNamaMahasiswa extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nama_mahasiswa);
        listView = (ListView) findViewById(R.id.listView);
    }

    public void loadMahasiswa(View view) {
        // Memanggil subclass asynctask
        new subclassAsynctask(listView).execute();
    }

    //subclass assynctask
    class subclassAsynctask extends AsyncTask<String, Integer, String> {
        ListView listView;
        ArrayAdapter arrayAdapter;
        ArrayList<String> daftarNama;
        ProgressDialog progressDialog;

        // Konstruktor
        public subclassAsynctask(ListView listView) {
            this.listView = listView;
            progressDialog = new ProgressDialog(ListNamaMahasiswa.this);
            daftarNama = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            // memmunculkan progress dialog ketika tombol di klik
            progressDialog.setTitle("Loading Data");
            progressDialog.setIndeterminate(true);
            // mengatur progress menjadi 0 agar dimulai dari awal
            progressDialog.setProgress(0);
            // mengatur progress maksimal
            progressDialog.setMax(100);
            // mengatur gaya munculnya progress dalam bentuk spinner
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // memberikan tombol agar dapat di cancel
            progressDialog.setCancelable(true);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL PROCESS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // ketika tombol cancel process di klik, maka akan menutup progress dialog
                    progressDialog.dismiss();
                    // proses pada subclass di batalkan dengan status boolean true
                    subclassAsynctask.this.cancel(true);
                }
            });
            // munculkan progress dialog
            progressDialog.show();
        }

        // proses latar belakang
        @Override
        protected String doInBackground(String... strings) {
            arrayAdapter = new ArrayAdapter<>(ListNamaMahasiswa.this, android.R.layout.simple_list_item_1, daftarNama); //membuat adapter

            // ambil array yang sudah disedikan di dalam file String, lalu masukan ke dalam variabel arrayNama dalam bentuk array bertipe string
            String[] arrayNama = getResources().getStringArray(R.array.ARRAY);
            //perulangan untuk menyimpan array
            //diulang dari 0 hingga lebih kecil dari panjang arraynya, sehingga jumlah perulangan akan sama dengan jumlah data pada array yang sudah disiapkan
            for (int counter = 0; counter<arrayNama.length; counter++) {
                // membuat variabel persentasi bertipe Long
                // membagi besarnya counter dengan panjang array
                // jika counter = 0, maka 0 dibagi dengan 40 hasilnya 0 persen
                // jika counter = 1, maka 1 dibagi dengan 40 hasilnya 0.025 persen
                // begitu seterusnya
                // 100L memunculkan persentase saat loading
                final long persentase = (100L * counter) / arrayNama.length;
                // melihat isi dari arrayNama pada indeks ke sekian (counter) lalu masukan ke dalam variable nama bertipe string
                final String nama = arrayNama[counter];
                try {
                    Runnable runnable = new Runnable() {
                        // munculkan nama yang di muat ke dalam list view dalam bentuk message pada progressDialog
                        @Override
                        public void run() {
                            progressDialog.setMessage((int) persentase+" % - loading "+nama);
                        }
                    };
                    // menjalankan thread agar tidak memberatkan main thread pada aplikasi
                    runOnUiThread(runnable);
                    // skip waktu selama 0.3 detik
                    Thread.sleep(200);
                    // menambahkan isi dari arrayNama pada indeks ke sekian (counter) ke dalam arraylist daftarNama
                    daftarNama.add(arrayNama[counter]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        // lakukan method ini setelah semua data sudah di muat
        @Override
        protected void onPostExecute(String string) {
            // memmunculkan array yang sudha dimuat kedalam listView pada xml file
            listView.setAdapter(arrayAdapter);
            // menutup progressdialog dengan perintah dismiss
            progressDialog.dismiss();
        }
    }



}

