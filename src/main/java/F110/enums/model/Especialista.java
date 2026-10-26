package com.navantia.f110.enums.model;

import lombok.Getter;

@Getter
public enum Especialista {
    TUBERO("Oficial de Tubería y Montaje"),
    SOLDADOR("Oficial de Soldadura"),
    ENCARGADO("Encargado");

    private final String descripcion;

    Especialista(String descripcion) {
        this.descripcion = descripcion;
    }
}
