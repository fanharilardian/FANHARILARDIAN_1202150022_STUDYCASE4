package android.fanharil.com.fanharilardian_1202150022_studycase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
    }

    public void listNamaMahasiswa(View view) {
        // buat intent agar berpindah ke class ListNamaMahasiswa
        Intent intent = new Intent(this, ListNamaMahasiswa.class);
        startActivity(intent);
        Toast.makeText(this, "List Data Mahasiswa terpilih", Toast.LENGTH_SHORT).show();
    }

    public void pencariGambar(View view) {
        // buat intent agar berpindah ke class PencariGambar
        Intent intent = new Intent(this, PencariGambar.class);
        startActivity(intent);
        Toast.makeText(this, "Pencari Gambar terpilih", Toast.LENGTH_SHORT).show();
    }
}
