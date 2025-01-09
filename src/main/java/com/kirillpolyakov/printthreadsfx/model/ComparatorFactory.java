package com.kirillpolyakov.printthreadsfx.model;

import java.util.Comparator;

public class ComparatorFactory {

    public ComparatorFactory() {
    }

    public Comparator<Document> comparator(String by) {
        switch (by) {
            case "type" -> {
                return Comparator.comparing(Document::getName);
            }
            case "duration" -> {
                return Comparator.comparing(Document::getPrintDuration);
            }
            case "paper size" -> {
                return new Comparator<Document>() {
                    @Override
                    public int compare(Document o1, Document o2) {
                        return -o1.getPaperSize().compareTo(o2.getPaperSize());
                    }
                };
            }
            case "in order" -> {
                return (o1, o2) -> 0;
            }
        }
        throw new IllegalArgumentException();
    }
}