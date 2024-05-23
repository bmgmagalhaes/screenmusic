package br.com.curso.screenmusic;

import br.com.curso.screenmusic.principal.Principal;
import br.com.curso.screenmusic.repository.AlbumRepository;
import br.com.curso.screenmusic.repository.ArtistaRepository;
import br.com.curso.screenmusic.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelaDeMusicaApplication implements CommandLineRunner {

	@Autowired
	private ArtistaRepository repositoryArtista;
	@Autowired
	private AlbumRepository repositoryAlbum;
	@Autowired
	private MusicaRepository repositoryMusica;


	public static void main(String[] args) {
		SpringApplication.run(TelaDeMusicaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(repositoryArtista, repositoryAlbum, repositoryMusica);
		principal.exibeMenu();
	}
}
