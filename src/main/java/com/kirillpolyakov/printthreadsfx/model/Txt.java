package com.kirillpolyakov.printthreadsfx.model;

public class Txt extends Document {
    @Override
    public int setPrintDuration() {
        return 8;
    }

    @Override
    public String setName() {
        return "txt";
    }

    @Override
    public String setPaperSize() {
        return "A4";
    }
}
