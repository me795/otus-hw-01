package ru.dvsokolov.experiment.app;

public class ExperimentImpl implements Experiment {

    private int a;
    private int b;

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getModulo() {
        return a % b;
    }
}
