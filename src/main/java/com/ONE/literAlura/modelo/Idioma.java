package com.ONE.literAlura.modelo;

public enum Idioma {
    es("es"),
    en("en"),
    fr("fr"),
    pt("pt");

    private String idiomaLibro;

    Idioma(String idioma) {
        this.idiomaLibro = idioma;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaLibro.equalsIgnoreCase(text)) {
                return idioma;
            }
        }

        throw new IllegalArgumentException("Ninguna categoria fue encontrada: " + text);
    }
}
