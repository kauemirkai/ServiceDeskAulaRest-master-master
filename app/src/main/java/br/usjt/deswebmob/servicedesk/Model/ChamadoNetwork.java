package br.usjt.deswebmob.servicedesk.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLData;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.usjt.deswebmob.servicedesk.Model.Chamado;
import br.usjt.deswebmob.servicedesk.Model.Fila;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by arqdsis on 28/03/2018.
 */

public class ChamadoNetwork {

    public static ArrayList<Fila> _filas =null ;

    public static ArrayList<Fila> getFilas(String urlRest, String urlImg) throws IOException {
        if( _filas == null){
            _filas = buscarFilas(urlRest);

        }
        for (Fila fila:_filas){
           fila.setImagem(getFigura(urlImg));


        }
        return _filas;

    }

    public static ArrayList<Chamado> buscarChamados(String url) throws IOException {
        ArrayList<Chamado> chamados = new ArrayList<>();
        DateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String json = response.body().string();

        try {
            JSONArray lista = new JSONArray(json);
            for (int i = 0; i < lista.length(); i++) {
                JSONObject item = (JSONObject) lista.get(i);
                Chamado chamado = new Chamado();
                chamado.setNumero(item.getInt("numero"));
                chamado.setDescricao(item.getString("descricao"));
                chamado.setDescricao(item.getString("status"));
                String sDataAbertura = (item.getString("dataAbertura"));
                String sDataFechamento = (item.getString("dataFechamento"));

                try {
                    chamado.setDataAbertura((Date) formater.parse(sDataAbertura));
                } catch (ParseException e) {
                    e.printStackTrace();
                    chamado.setDataAbertura(null);
                }
                try {
                    chamado.setDataAbertura((Date) formater.parse(sDataFechamento));
                } catch (ParseException e) {
                    chamado.setDataAbertura(null);
                    e.printStackTrace();
                }

                JSONObject filaItem = item.getJSONObject("fila");

                Fila fila = new Fila();
                fila.setId(filaItem.getInt("id"));
                fila.setNome(filaItem.getString("nome"));
                fila.setFigura(filaItem.getString("figura"));

                chamado.setFila(fila);
                chamados.add(chamado);


            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }


        return chamados;
    }


    public static ArrayList<Fila> buscarFilas(String url) throws  IOException{
        ArrayList<Fila> filas = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();


        try{
            JSONArray lista = new JSONArray();
            for (int i = 0  ; i<lista.length();i++){
                JSONObject item = (JSONObject) lista.get(i);
                Fila fila = new Fila();
                fila.setId(item.getInt("id"));
                fila.setNome(item.getString("nome"));
                fila.setFigura(item.getString("figura"));
                filas.add(fila);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return filas;
    }

    public static Bitmap getFigura(String url) throws IOException{
        Bitmap img = null;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        InputStream is = response.body().byteStream();

        img = BitmapFactory.decodeStream(is);
        is.close();

        return img;
    }

}