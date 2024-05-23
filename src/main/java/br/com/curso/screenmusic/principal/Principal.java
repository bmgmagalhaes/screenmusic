package br.com.curso.screenmusic.principal;

import br.com.curso.screenmusic.model.*;
import br.com.curso.screenmusic.repository.AlbumRepository;
import br.com.curso.screenmusic.repository.ArtistaRepository;
import br.com.curso.screenmusic.repository.MusicaRepository;
import br.com.curso.screenmusic.service.ConsultaChatGpt;

import java.util.*;

public class Principal {

    private Scanner leitor = new Scanner(System.in);

    private ArtistaRepository repositoryArtista;
    private AlbumRepository repositoryAlbum;
    private MusicaRepository repositoryMusica;

    private Artista artista = new Artista();
    private List<Artista> artistasListados = new ArrayList<>();
    private Optional<Artista> artistaBuscado;

    private Album album = new Album();
    private List<Album> albunsListados = new ArrayList<>();
    private Optional<Album> albumBuscado;

    public Principal(ArtistaRepository repositoryArtista, AlbumRepository repositoryAlbum, MusicaRepository repositoryMusica) {
        this.repositoryArtista = repositoryArtista;
        this.repositoryAlbum = repositoryAlbum;
        this.repositoryMusica = repositoryMusica;
    }

    public void exibeMenu() {

        int opcao = -1;

        while (opcao != 0){

            System.out.println("\n" +
                    "1- Cadastrar artistas\n" +
                    "2- Criar album\n" +
                    "3- Gravar músicas em album\n" +
                    "4- Pesquisar álbum\n"+
                    "5- Buscar músicas por artistas\n"+
                    "6- Buscar músicas por estilo\n"+
                    "7- Discografia de artista\n"+
                    "8- Lista de Artistas Cadastrados\n"+
                    "9- Pesquisar dados sobre um artista\n"+

                    "\n0 - Sair");
            opcao = leitor.nextInt();

            switch (opcao){
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    criarAlbum();
                    break;
                case 3:
                    gravarMusicaEmAlbum();
                    break;
                case 4:
                    pesquisarAlbum();
                    break;
                case 5:
                    buscarMusicaPorArtista();
                    break;
                case 6:
                    buscarMusicaPorEstilo();
                    break;
                case 7:
                    listarAlbunsDeArtista();
                    break;
                case 8:
                    listarArtistasCadastrados();
                    break;
                case 9:
                    pesquisarSobreArtista();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida");
            }
        }

    }

    private void cadastrarArtista() {

        System.out.println("Informe o nome do artista: ");
        leitor.nextLine();
        var nome = leitor.nextLine();

        System.out.print("Informe um tipo de carreira: ");
        Arrays.stream(TipoCarreira.values()).forEach(t -> System.out.print(t + " "));
        var tipoCarreira = leitor.nextLine();
        Artista artista = new Artista(nome, tipoCarreira);
        repositoryArtista.save(artista);

    }

    private void criarAlbum() {
        System.out.println("Nome do novo album: ");
        leitor.nextLine();
        var nomeAlbum = leitor.nextLine();

        listarArtistasCadastrados();
        System.out.println("Nome do artista do album");
        var nomeArtista = leitor.nextLine();
        artistaBuscado = buscarArtistasPorNome(nomeArtista);

        if (artistaBuscado.isPresent()){

            artista = artistaBuscado.get();
            Album album = new Album(nomeAlbum, artista);
            repositoryAlbum.save(album);
        }


    }

    private void gravarMusicaEmAlbum() {
        listarAlbunsDeArtista();
        System.out.println("Escolha o nome do album");
        var nomeAlbum = leitor.nextLine();
        albumBuscado = buscarAlbumPorNome(nomeAlbum);

        if (albumBuscado.isPresent()){
            album = albumBuscado.get();

            System.out.println("Nome da música");
            var nomeMusica = leitor.nextLine();

            Arrays.stream(TipoMusica.values()).forEach(t -> System.out.print(t + " "));
            System.out.println("\nInforme o estilo de música: ");
            var tipoMusica = leitor.nextLine();

            Musica musica = new Musica(nomeMusica, tipoMusica, album);
            repositoryMusica.save(musica);

        }

    }

    private void pesquisarAlbum() {
        System.out.println("Digite o nome do álbum: ");
        leitor.nextLine();
        var nomeAlbum = leitor.nextLine();
        albumBuscado = buscarAlbumPorNome(nomeAlbum);

        if (albumBuscado.isPresent()) {
            album = albumBuscado.get();
            System.out.println("[" + album.getArtista().getNome() + "] - " + album.getNome());
            album.getMusicas().forEach(m-> System.out.println(m.getNome() + " ["+m.getTipoMusica()+"]"));
        }
    }

    private void buscarMusicaPorArtista() {
        listarArtistasCadastrados();

        System.out.print("Escolha o artista: ");
        leitor.nextLine();
        var nomeArtista = leitor.nextLine();
        artistaBuscado = buscarArtistasPorNome(nomeArtista);

        if (artistaBuscado.isPresent()){
            artista = artistaBuscado.get();

            List<Musica> listaMusicas = new ArrayList<>();
            for (Album a: artista.getAlbuns()){
                a.getMusicas().stream()
                        .forEach(m->listaMusicas.add(m));
            }
            listaMusicas.forEach(m -> System.out.println(m.getNome() + " (" +m.getAlbum().getNome() + ") "));

        }
    }

    private void buscarMusicaPorEstilo() {
        Arrays.stream(TipoMusica.values()).forEach(t -> System.out.print(t + "; "));
        System.out.print("Escolha o estilo: ");
        leitor.nextLine();
        var estiloMusica = leitor.nextLine();

        TipoMusica tipoMusica = TipoMusica.fromTextoToEnum(estiloMusica);

        List<Musica> listaMusicas = repositoryMusica.findByTipoMusica(tipoMusica);
        listaMusicas.forEach(m -> System.out.println(m.getNome() + " (" +m.getAlbum().getNome() + ") "));
    }

    private void listarAlbunsDeArtista() {
        listarArtistasCadastrados();

        System.out.print("Escolha o artista: ");
        leitor.nextLine();
        var nomeArtista = leitor.nextLine();
        artistaBuscado = buscarArtistasPorNome(nomeArtista);

        if (artistaBuscado.isPresent()){
            artista = artistaBuscado.get();
            List<Album> albunsDoArtista = repositoryAlbum.findByArtista(artista);
            albunsDoArtista.forEach(a -> System.out.print("(" +a.getNome() + ") "));
            System.out.println();
        }
    }


    private void pesquisarSobreArtista() {
        System.out.println("Sobre qual artista quer pesquisar? ");
        var sobre = leitor.nextLine();
        var resposta = ConsultaChatGpt.obterInformacao(sobre);
        System.out.println(resposta);
    }


    private void listarArtistasCadastrados() {
        artistasListados = repositoryArtista.findAll();
        artistasListados.forEach(a -> System.out.println(a.getNome() +" ["+a.getTipoCarreira()+"]"));
    }

    private Optional<Artista> buscarArtistasPorNome(String nomeArtista) {

        artistaBuscado = repositoryArtista.findByNomeContainingIgnoreCase(nomeArtista);

        if (artistaBuscado.isPresent()){
            artista = artistaBuscado.get();
            System.out.println(artista.getNome() +" ["+artista.getTipoCarreira()+"]");
        } else {
            System.out.println("Nenhuma artista localizado com esse nome");
        }
        return artistaBuscado;
    }


    private Optional<Album> buscarAlbumPorNome(String nomeAlbum) {

        albumBuscado = repositoryAlbum.findByNomeContainingIgnoreCase(nomeAlbum);

        if (albumBuscado.isPresent()){
            album = albumBuscado.get();
        } else {
            System.out.println("Nenhum álbum localizado com esse nome");
        }
        return albumBuscado;
    }

}
