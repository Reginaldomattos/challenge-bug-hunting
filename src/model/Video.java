package model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Video {
    private String titulo;
    private String descricao;
    private int duracao; // em minutos
    private String categoria;
    private LocalDate dataPublicacao;

    public Video(String titulo, String descricao, int duracao, String categoria, LocalDate dataPublicacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.duracao = duracao;
        this.categoria = categoria;
        this.dataPublicacao = dataPublicacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return titulo + ";" + descricao + ";" + duracao + ";" + categoria + ";" + sdf.format(dataPublicacao);
    }

    public static Video fromString(String linha) {
        try {
            String[] partes = linha.split(";");
            LocalDate data = LocalDate.parse(partes[4]);
            return new Video(partes[0], partes[1], Integer.parseInt(partes[2]), partes[3], data);
        } catch (Exception e) {
            return null; // Ignora erros de parsing
        }
    }
}