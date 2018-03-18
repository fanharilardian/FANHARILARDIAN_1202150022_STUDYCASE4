package android.fanharil.com.fanharilardian_1202150022_studycase4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PencariGambar extends AppCompatActivity {

    private EditText editTextURL;
    private Button tombolCariGambar;
    private ImageView imageViewHasil;
    private ProgressDialog progressDialogLoading;
    private int memuatGambar=0;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_pencari_gambar);

        // variabel untuk menyimpan data berdasarkan id yang terdaftar
        editTextURL=(EditText)findViewById(R.id.txtImgURL);
        tombolCariGambar=(Button)findViewById(R.id.btnImgLoad);
        imageViewHasil=(ImageView)findViewById(R.id.lblImg);
        // jika tombol cari gambar di klik maka :
        tombolCariGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // jalankan metod
                muatGambarNyaa();
            }
        });

        // periksa ini bundle
        if(saved!=null){
            if(saved.getInt("IMAGE_IS_LOADED")!=0 && !saved.getString("EXTRA_TEXT_URL").isEmpty()){
                editTextURL.setText(""+saved.getString("EXTRA_TEXT_URL"));
                muatGambarNyaa();
            }
        }
    }


    //Method yang berguna untuk melakukan penyimpanan sesuatu pada package
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Simpan untuk URL TEXT agar input tidak dilakukan kembali
        outState.putString("EXTRA_TEXT_URL",editTextURL.getText().toString());
        //Simpan untuk respon pada savedinstancestate
        outState.putInt("IMAGE_IS_LOADED",memuatGambar);
    }

    // metod untuk menjalankan pemuatan gambar berdasarkan URL
    private void muatGambarNyaa(){
        //Mengambil nilai input (String)
        String URLGambar = editTextURL.getText().toString();
        // proses memuat gambar dari internet
        new subclassMemuatGambar().execute(URLGambar);
    }

    // asynctask dengan input berupa string, proses nya integer, dan hasilnya bitmat
    public class subclassMemuatGambar extends AsyncTask<String, Integer, Bitmap>{
        // metod yang dijalankan pertama kali swsaat tombol di tap, akan memunculkaN progres dialog sperti pada kode dibawah ini
        @Override
        protected void onPreExecute() {
            progressDialogLoading=new ProgressDialog(PencariGambar.this);
            progressDialogLoading.setMessage("Memuat gambar...");
            progressDialogLoading.setMax(100);
            progressDialogLoading.incrementProgressBy(1);
            progressDialogLoading.show();
        }

        //Method untuk mencari gambar dari internet berdasarkan alamat URL gambar valid dengan mengubahnya menjadi bitmap

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e("doInBackground() - ", e.getMessage());
            }
            return bitmap;
        }

        //Method untuk pengambilan data berlangsung dan menampilkan progressbar

        @Override
        protected void onProgressUpdate(Integer... values) {
            try {
                Thread.sleep(300);
                progressDialogLoading.setMessage("Mengambil data...");
                progressDialogLoading.incrementProgressBy(values[0]);
                progressDialogLoading.setProgress(values[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // metoda yang dijalankan setelah proses download gambar telah selesai
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageViewHasil.setImageBitmap(bitmap);
            memuatGambar=1;
            progressDialogLoading.dismiss();
        }
    }

}
