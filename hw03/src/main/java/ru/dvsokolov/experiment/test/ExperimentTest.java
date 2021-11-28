package ru.dvsokolov.experiment.test;

import ru.dvsokolov.experiment.app.Experiment;
import ru.dvsokolov.experiment.app.ExperimentImpl;
import ru.dvsokolov.testfw.Assertions;
import ru.dvsokolov.testfw.annotations.After;
import ru.dvsokolov.testfw.annotations.Before;
import ru.dvsokolov.testfw.annotations.Test;

public class ExperimentTest {

    private Experiment experiment;

    @Before
    public void beforeTest(){
        experiment = new ExperimentImpl();
    }

    @Test
    public void test1(){
        this.experiment.setA(4);
        this.experiment.setB(2);
        int actual = this.experiment.getModulo();
        int expected = 0;
        Assertions.checkEquals(expected,actual);
    }

    @Test
    public void test2(){
        this.experiment.setA(4);
        this.experiment.setB(3);
        int actual = this.experiment.getModulo();
        int expected = 1;
        Assertions.checkEquals(expected,actual);
    }

    @Test
    public void test3(){
        this.experiment.setA(4);
        this.experiment.setB(1);
        int actual = this.experiment.getModulo();
        int expected = 1;
        Assertions.checkEquals(expected,actual);
    }

    @After
    public void afterTest(){
        System.out.println(this + " finished test");
    }


}
