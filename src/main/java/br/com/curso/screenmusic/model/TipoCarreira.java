package br.com.curso.screenmusic.model;

public enum TipoCarreira {
    SOLO ("Solo"),
    DUPLA ("Dupla"),
    BANDA ("Banda");

    private String tipoCarreiraTexto;

    TipoCarreira(String tipoCarreiraTexto) {
        this.tipoCarreiraTexto = tipoCarreiraTexto;
    }

    public static TipoCarreira fromTextoToEnum(String tipoCarreira) {

        for (TipoCarreira t: TipoCarreira.values()){
            if(t.tipoCarreiraTexto.equalsIgnoreCase(tipoCarreira)){
                return t;
            }
        }
        throw new IllegalArgumentException("Nenhum tipo de carreira encontrado");
    }
}
