package com.kirillpolyakov.printthreadsfx.model;

public class Png extends Document {
    @Override
    public int setPrintDuration() {
        return 7;
    }

    @Override
    public String setName() {
        return "png";
    }

    @Override
    public String setPaperSize() {
        return "A5";
    }
}
