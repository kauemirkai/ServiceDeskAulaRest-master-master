package br.usjt.deswebmob.servicedesk.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by arqdsis on 21/03/2018.
 */

public class Fila implements Serializable{
    private int id;
    private String nome, figura;
    private Bitmap imagem;




    public int getId() {
        return id;
    }

    public Fila() {
    }

    public String getFigura() {
        return figura;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Fila{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", figura='" + figura + '\'' +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Fila(int id, String nome, String figura) {

        this.id = id;
        this.nome = nome;
        this.figura=figura;
    }
}