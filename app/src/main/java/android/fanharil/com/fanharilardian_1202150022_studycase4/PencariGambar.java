package android.fanharil.com.fanharilardian_1202150022_studycase4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class PencariGambar extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencari_gambar);

        editText = (EditText) findViewById(R.id.ed_cari_gambar);
        button = (Button) findViewById(R.id.btn_cari);
        imageView = (ImageView) findViewById(R.id.iv_cari);
    }

    public void cariGambar(View view) {
        text = editText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "Masukkan URL gambar terlebih dahulu", Toast.LENGTH_LONG).show();
        } else {
            new subclassCariGambar().execute(text);
        }
    }

    private class subclassCariGambar extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(PencariGambar.this);
            progressDialog.setTitle("Cari gambar");
            progressDialog.setMessage("Memuat Gambar");
            // atur total yang yang muat agar tidak ditentukan
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String URLGambar = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(URLGambar).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (Exception e) {
                Log.e("Terdapat Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
    }
}
