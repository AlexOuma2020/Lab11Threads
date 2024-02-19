package ru.spmi.temnov.lab11;

public class CostThread extends Thread{//поток расчета стоимости
    Main prod;
    private double result;
    CostThread(Main prod){
        this.prod = prod;
        System.out.println("Поток стоимости создан");
    }

    @Override
    public void run() {
        double sum = 0.0;
        for (Appliances<Double> app: prod.getCostList()){
            sum += app.getFeature();
        }
        result = sum;

    }

    public double getResult(){
        return result;
    }

}
