package edu.upc.eetac.dsa.jcancer.librosjavi;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsa.jcancer.librosjavi.api.AppException;
import edu.upc.eetac.dsa.jcancer.librosjavi.api.Libros;
import edu.upc.eetac.dsa.jcancer.librosjavi.api.LibrosAPI;
import edu.upc.eetac.dsa.jcancer.librosjavi.api.LibrosCollection;


public class LibrosMainActivity extends ListActivity {


    private class FetchStingsTask extends AsyncTask<Void, Void, LibrosCollection> {
        private ProgressDialog pd;

        @Override
        protected LibrosCollection doInBackground(Void... params) {
            LibrosCollection libros = null;
            try {
                libros = LibrosAPI.getInstance(LibrosMainActivity.this).getLibros();
            } catch (AppException e) {
                e.printStackTrace();
            }
            return libros;
        }

        @Override
        protected void onPostExecute(LibrosCollection result) {
            addLibros(result);
            if (pd != null) {
                pd.dismiss();
            }
        }
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(LibrosMainActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }


    private final static String TAG = LibrosMainActivity.class.toString();
    private ArrayList<Libros> librosList;
    private LibrosAdapter adapter;


    private void addLibros (LibrosCollection libros) {
        Log.d(TAG, "hola2");
        librosList.addAll(libros.getLibros());
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros_main);

        librosList = new ArrayList<Libros>();
        adapter = new LibrosAdapter(this, librosList);
        setListAdapter(adapter);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("alicia", "alicia"
                        .toCharArray());
            }
        });
        (new FetchStingsTask()).execute();
    }


}