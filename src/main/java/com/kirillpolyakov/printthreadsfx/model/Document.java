package com.kirillpolyakov.printthreadsfx.model;

import lombok.Data;

/**
 * Каждый тип документа должен иметь уникальные реквизиты:
 * продолжительность печати, наименование типа документа, размер бумаги.
 */
@Data
public abstract class Document {



    private long printDuration;

    private String name;

    private String paperSize;

    private boolean isPrinted;

    public Document() {
        this.printDuration = setPrintDuration();
        this.name = setName();
        this.paperSize = setPaperSize();
    }



    public abstract int setPrintDuration();

    public abstract String setName();

    public abstract String setPaperSize();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "printDuration=" + printDuration +
                ", name='" + name + '\'' +
                ", paperSize='" + paperSize + '\'' +
                '}';
    }
}
