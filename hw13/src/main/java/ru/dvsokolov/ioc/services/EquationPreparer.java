package ru.dvsokolov.ioc.services;

import ru.dvsokolov.ioc.model.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
