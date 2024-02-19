package ru.spmi.temnov.lab11;

public class CompThread extends Thread{//поток расчета количества товаров заданной фирмы
    Main prod;
    String needed;
    private int result;
    CompThread(Main prod, String needed){
        this.prod = prod;
        this.needed = needed;
        System.out.println("Поток расчета товаров создан");
    }
    @Override
    public void run(){
        int num = 0;
        for (Appliances<String> app: prod.getCompList()){
            if (app.getFeature().equals(needed))
                num++;
        }
        result = num;

    }
    public int getResult(){
        return result;
    }
}
