package com.kirillpolyakov.printthreadsfx.model;

public class DocumentFactory {

    public Document create(String type) {
        switch (type) {
            case "TXT" -> {
                return new Txt();
            }
            case "PNG" -> {
                return new Png();
            }
            case "JPG" -> {
                return new Jpg();
            }
        } throw new IllegalArgumentException();
    }
}
