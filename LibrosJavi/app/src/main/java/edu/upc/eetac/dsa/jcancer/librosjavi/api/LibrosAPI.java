package edu.upc.eetac.dsa.jcancer.librosjavi.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * Created by Javier on 11/12/2014.
 */
public class LibrosAPI {
    private final static String TAG = LibrosAPI.class.getName();
    private static LibrosAPI instance = null;
    private URL url;

    private LibrosRootAPI rootAPI = null;

    private LibrosAPI(Context context) throws IOException, AppException {
        super();

        AssetManager assetManager = context.getAssets();
        Properties config = new Properties();
        config.load(assetManager.open("config.properties"));
        String urlHome = config.getProperty("libros.home");
        url = new URL(urlHome);

        Log.d("LINKS", url.toString());
        getRootAPI();
    }

    public final static LibrosAPI getInstance(Context context) throws AppException {
        if (instance == null)
            try {
                instance = new LibrosAPI(context);
            } catch (IOException e) {
                throw new AppException(
                        "Can't load configuration file");
            }
        return instance;
    }


    private void getRootAPI() throws AppException {
        Log.d(TAG, "getRootAPI()");
        rootAPI = new LibrosRootAPI();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            Log.d(TAG,urlConnection.toString());
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Libros API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, rootAPI.getLinks());
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Libros API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Libros Root API");
        }

    }


    public LibrosCollection getLibros() throws AppException {
        Log.d(TAG, "getLibros()");
        LibrosCollection libros = new LibrosCollection();
        Log.d(TAG, "hola1");
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("libros").getTarget()).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Libros API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");

            parseLinks(jsonLinks, libros.getLinks());

            //libros.setNewestTimestamp(jsonObject.getLong("NewestTimestamp"));
            //libros.setOldestTimestamp(jsonObject.getLong("OldestTimestamp"));
            JSONArray jsonLibros = jsonObject.getJSONArray("libros");
            for (int i = 0; i < jsonLibros.length(); i++) {
                Libros libro = new Libros();
                JSONObject jsonLibro = jsonLibros.getJSONObject(i);
                libro.setLibroid(jsonLibro.getInt("libroid"));
                libro.setTitle(jsonLibro.getString("title"));
                libro.setAutor(jsonLibro.getString("autor"));
                libro.setLanguage(jsonLibro.getString("language"));
                libro.setEdition(jsonLibro.getString("edition"));
                libro.setDateCreation(jsonLibro.getInt("dateCreation"));
                libro.setDateImpresion(jsonLibro.getInt("dateImpresion"));
                libro.setEditorial(jsonLibro.getString("editorial"));



                jsonLinks = jsonLibro.getJSONArray("links");
                parseLinks(jsonLinks, libro.getLinks());
                libros.getLibros().add(libro);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Libros API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Libros Root API");
        }

        return libros;
    }



    private void parseLinks(JSONArray jsonLinks, Map<String, Link> map)
            throws AppException, JSONException {
        for (int i = 0; i < jsonLinks.length(); i++) {
            Link link = null;
            try {
                link = SimpleLinkHeaderParser
                        .parseLink(jsonLinks.getString(i));
            } catch (Exception e) {
                throw new AppException(e.getMessage());
            }
            String rel = link.getParameters().get("rel");
            String rels[] = rel.split("\\s");
            for (String s : rels)
                map.put(s, link);
        }
    }


}