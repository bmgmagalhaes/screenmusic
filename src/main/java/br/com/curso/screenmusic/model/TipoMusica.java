package br.com.curso.screenmusic.model;

public enum TipoMusica {
    ROCK ("Rock"),
    SAMBA("Samba"),
    RAP("Rap"),
    MPB("Mpb"),
    SERTANEJO("Sertanejo"),
    POP("Pop"),
    OUTROS("Outros");

    private String tipoMusicaTexto;

    TipoMusica(String tipoMusicaTexto) {
        this.tipoMusicaTexto = tipoMusicaTexto;
    }

    public static TipoMusica fromTextoToEnum(String tipoMusicaTexto){

        for (TipoMusica t: TipoMusica.values()){

            if (t.tipoMusicaTexto.equalsIgnoreCase(tipoMusicaTexto)){

                return t;
            }
            else {
                System.out.println("Estilo não encontrado. Atribuído valor OUTROS. = "+t.tipoMusicaTexto);
            }
        }
        return TipoMusica.OUTROS;
    }

}
