package ru.spmi.temnov.lab11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    private ArrayList<Appliances<String>> arrComp= new ArrayList<Appliances<String>>();
    private ArrayList<Appliances<Double>> arrCost= new ArrayList<Appliances<Double>>();

    Main(){
        for (int i = 0; i < 5; ++i){
            arrComp.add(new Appliances<>(RandomGenerator.getType(), RandomGenerator.getCompany()));
            arrCost.add(new Appliances<>(RandomGenerator.getType(), RandomGenerator.getCost()));
        }
    }
    private boolean find(String inp){
        for (String comp: RandomGenerator.getCompanyAll()){
            if (comp.equals(inp))
                return true;
        }
        return false;
    }


    private void printList(){
        System.out.println("Список товров с названием компании-производителя: ");
        for (Appliances<String> app: arrComp){
            app.showComp();
        }
        System.out.println("\nСписок товров со стоимостью: ");
        for (Appliances<Double> app: arrCost){
            app.showPrice();
        }
        System.out.println();
    }
    private String inputCompany() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    private String menu(){//без понятия как сделать тест
        String needed = null;
        boolean excep;
        do{
            excep = true;
            System.out.print("Введите название фирмы, количество товаров которой хотите узнать {LG, Haier, Sharp, Samsung, Bosch, Siemens, Hitachi}: ");
            try {
                needed = inputCompany();
            } catch (IOException e) {
                excep = false;
                System.out.println("Неверный формат ввода!");
            }
            if (!find(needed)){
                System.out.println("Несуществующая фирма " + needed);
                excep = false;
            }
        }while(!excep);
        return needed;
    }
    public ArrayList<Appliances<String>> getCompList(){
        return arrComp;
    }

    public ArrayList<Appliances<Double>> getCostList(){
        return arrCost;
    }
    public void setArrComp(ArrayList<Appliances<String>> arr){
        arrComp = arr;
    }

    public void setArrCost(ArrayList<Appliances<Double>> arr){
        arrCost = arr;
    }
    private void count(String needed){//расчет ВП
        CompThread compThread = new CompThread(this, needed);
        CostThread costThread = new CostThread(this);
        costThread.start();
        compThread.start();
        try {
            compThread.join();
            costThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Общая сумма = %.2f\n", costThread.getResult());
        System.out.printf("Количество товаров фирмы %s = %d\n", needed, compThread.getResult());
    }

    public static void main(String[] args){
        Main m = new Main();
        m.printList();
        m.count(m.menu());
    }
}