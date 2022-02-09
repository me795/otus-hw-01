package ru.dvsokolov.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        event(key,value,"put");
    }

    @Override
    public void remove(K key) {
        var value = cache.get(key);
        if (value != null) {
            event(key,value,"remove");
            cache.remove(key);
        }
    }

    @Override
    public V get(K key) {
        var value = cache.get(key);
        if (value != null) {
            event(key,value,"get");
            return value;
        }else{
            event(key,null,"not found in cache");
            return null;
        }

    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void event(K key, V value, String action) {
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception ex) {
                throw new RuntimeException("Listener error");
            }
        });
    }
}
