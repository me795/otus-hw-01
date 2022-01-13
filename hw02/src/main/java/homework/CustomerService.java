package homework;


import java.util.*;

public class CustomerService {

    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final NavigableMap<Customer, String> customerMap = new TreeMap<>(new ComparatorByScores());

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> value = customerMap.firstEntry();
        NavigableMap<Customer, String> copyMap = new TreeMap<>(new ComparatorByScores());
        copyMap.put(value.getKey().clone(), value.getValue());

        return copyMap.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        Map.Entry<Customer, String> value = customerMap.higherEntry(customer);
        if (value == null){
            return null;
        }else {
            NavigableMap<Customer, String> copyMap = new TreeMap<>(new ComparatorByScores());
            copyMap.put(value.getKey().clone(), value.getValue());

            return copyMap.firstEntry();
        }
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }
}
