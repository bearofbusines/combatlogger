package org.bear.combatlogger.data;

import org.bukkit.inventory.Inventory;

import java.util.*;

public class StaticHashMap implements Map<String, Inventory>{
    public HashMap<String, Inventory> inventoryHashMap;

    public StaticHashMap(){
        this.inventoryHashMap = new HashMap<String, Inventory>();
    }

    public int size() {
        return inventoryHashMap.size();
    }

    public boolean isEmpty() {
        return inventoryHashMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return inventoryHashMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return inventoryHashMap.containsValue(value);
    }

    @Override
    public Inventory get(Object key) {
        return inventoryHashMap.get(key);
    }

    public boolean containsKey(String key) {
        return inventoryHashMap.containsKey(key);
    }

    public boolean containsValue(Inventory value) {
        return inventoryHashMap.containsValue(value);
    }

    public Inventory get(String key) {
        return inventoryHashMap.get(key);
    }

    public Inventory put(String key, Inventory value) {
        return inventoryHashMap.put(key, value);
    }

    @Override
    public Inventory remove(Object key) {
        return inventoryHashMap.remove(key);
    }

    public Object remove(String key) {
        return inventoryHashMap.remove(key);
    }

    public void putAll(Map m) {
        inventoryHashMap.putAll(m);
    }

    public void clear() {
        inventoryHashMap.clear();
    }

    public Set<String> keySet() {
        return inventoryHashMap.keySet();
    }

    public Collection<Inventory> values() {
        return inventoryHashMap.values();
    }

    @Override
    public Set<Entry<String,Inventory>> entrySet() {
        return inventoryHashMap.entrySet();
    }

    public boolean remove(String key, Inventory value) {
        return inventoryHashMap.remove(key, value);
    }

    public boolean replace(String key, Inventory oldValue, Inventory newValue) {
        return inventoryHashMap.replace(key, oldValue, newValue);
    }

    public Inventory replace(String  key, Inventory value) {
        return inventoryHashMap.replace(key, value);
    }
}
