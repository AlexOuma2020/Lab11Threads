package ru.spmi.temnov.lab11;
public class Appliances <T>{//обобщенный класс товар
    private final T feature;//характеристика для рачета ВП
    private final String type;
    Appliances(String type, T feature){
        this.type = type;
        this.feature = feature;
    }
    public T getFeature(){
        return feature;
    }
    public void showComp(){
        System.out.println(type + " компании " + feature);
    }
    public void showPrice(){
        System.out.println(type + " со стоимостью " + feature);
    }
}