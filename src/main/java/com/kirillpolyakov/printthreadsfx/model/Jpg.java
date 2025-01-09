package com.kirillpolyakov.printthreadsfx.model;

public class Jpg extends Document {


    @Override
    public int setPrintDuration() {
        return 10;
    }

    @Override
    public String setName() {
        return "jpg";
    }

    @Override
    public String setPaperSize() {
        return "A3";
    }
}
