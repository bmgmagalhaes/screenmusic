package br.com.curso.screenmusic.model;

import jakarta.persistence.*;

@Entity
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoMusica tipoMusica;
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    public Musica() {
    }

    public Musica(String nome, String tipoMusica, Album album) {
        this.nome = nome;
        this.tipoMusica = TipoMusica.fromTextoToEnum(tipoMusica);
        this.album = album;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoMusica getTipoMusica() {
        return tipoMusica;
    }

    public void setTipoMusica(TipoMusica tipoMusica) {
        this.tipoMusica = tipoMusica;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }


}
