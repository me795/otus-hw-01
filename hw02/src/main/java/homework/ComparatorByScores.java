package homework;

import java.util.Comparator;
import java.util.NavigableMap;

class ComparatorByScores implements Comparator<Customer> {

    @Override
    public int compare(Customer c1, Customer c2) {
        return Long.compare(c1.getScores(), c2.getScores());
    }


}
