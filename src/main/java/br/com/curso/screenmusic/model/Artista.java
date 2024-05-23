package br.com.curso.screenmusic.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoCarreira tipoCarreira;
    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Album> albuns = new ArrayList();

    public Artista() {
    }

    public Artista(String nome, String tipoCarreira) {
        this.nome = nome;
        this.tipoCarreira = TipoCarreira.fromTextoToEnum(tipoCarreira);
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

    public TipoCarreira getTipoCarreira() {
        return tipoCarreira;
    }

    public void setTipoCarreira(TipoCarreira tipoCarreira) {
        this.tipoCarreira = tipoCarreira;
    }

    public List<Album> getAlbuns() {
        return albuns;
    }

    public void setAlbuns(List<Album> albuns) {
        albuns.forEach(a-> a.setArtista(this));
        this.albuns = albuns;
    }

    @Override
    public String toString() {
        return "nome = '" + nome + '\'' +
                ", tipoCarreira = " + tipoCarreira;// +
//                ", albuns = " + albuns;
    }
}
