package ru.spmi.temnov.lab11;

public class RandomGenerator {
    private final static String[] type = new String[]{"Пылесос", "Холодильник", "Телевизор", "Чайник"};
    private final static String[] company = {"LG", "Haier", "Sharp", "Samsung", "Bosch", "Siemens", "Hitachi"};

    public static String getType(){
        return type[(int)(Math.random() * type.length)];
    }

    public static String getCompany(){
        return company[(int)(Math.random() * company.length)];
    }

    public static double getCost(){//? как тестировать
        return (int)((Math.random() * 300.0 + 100.0) * 100) / 100.00;
    }

    public static String[] getTypeAll(){
        return type;
    }

    public static String[] getCompanyAll(){
        return company;
    }
}