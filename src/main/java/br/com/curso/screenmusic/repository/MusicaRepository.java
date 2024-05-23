package br.com.curso.screenmusic.repository;

import br.com.curso.screenmusic.model.Musica;
import br.com.curso.screenmusic.model.TipoMusica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicaRepository extends JpaRepository <Musica, Long> {
    List<Musica> findByTipoMusica(TipoMusica tipoMusica);
//    List<Musica> findByArtista(Artista artista);
}
