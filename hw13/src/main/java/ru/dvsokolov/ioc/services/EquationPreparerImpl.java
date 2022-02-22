package ru.dvsokolov.ioc.services;

import ru.dvsokolov.ioc.model.DivisionEquation;
import ru.dvsokolov.ioc.model.Equation;
import ru.dvsokolov.ioc.model.MultiplicationEquation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EquationPreparerImpl implements EquationPreparer {
    @Override
    public List<Equation> prepareEquationsFor(int base) {
        List<Equation> equations = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            var multiplicationEquation = new MultiplicationEquation(base, i);
            var divisionEquation = new DivisionEquation(multiplicationEquation.getResult(), base);
            equations.add(multiplicationEquation);
            equations.add(divisionEquation);

        }
        Collections.shuffle(equations);
        return equations;
    }
}