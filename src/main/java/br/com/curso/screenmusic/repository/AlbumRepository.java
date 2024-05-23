package br.com.curso.screenmusic.repository;

import br.com.curso.screenmusic.model.Album;
import br.com.curso.screenmusic.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtista(Artista artista);

    Optional<Album> findByNomeContainingIgnoreCase(String nomeAlbum);
}
