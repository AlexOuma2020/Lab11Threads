package ru.spmi.temnov.lab11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MainThreadsTest {
    private void provideInput(String data){
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
    Main m;
    @BeforeEach
    void testible(){
        m = new Main();
    }

    @Test
    void getFeatureTest1(){
        Appliances<String> app = new Appliances<>("Холодильник", "LG");
        assertEquals("LG", app.getFeature());
    }

    @Test
    void getFeatureTest2(){
        Appliances<Double> app = new Appliances<>("Холодильник", 123.23);
        assertEquals(123.23, app.getFeature());
    }
    @Test
    void randGetRandomCompanyTest(){
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertTrue((Boolean)method.invoke(m, RandomGenerator.getCompany()));
            System.out.println("Случайное значение массива company работает корректно\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void randGetRandomType(){
        String type = RandomGenerator.getCompany();
        for (String st: RandomGenerator.getTypeAll()){
            if (st.equals(type)){
                assertEquals(st, type);
                break;
            }
        }
        System.out.println("Случайное значение массива company работает корректно\n");
    }

    @Test
    void companyGetAllTest(){
        assertArrayEquals(new String[]{"LG", "Haier", "Sharp", "Samsung", "Bosch", "Siemens", "Hitachi"}, RandomGenerator.getCompanyAll());
        System.out.println("Получение массива фирм работает корректно\n");
    }

    @Test
    public void testFound1(){
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertEquals(true, method.invoke(m, "LG"));
            System.out.println("Товар фирмы LG существует\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFound2(){
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertEquals(false, method.invoke(m, "Qwerty"));
            System.out.println("Товара фирмы lG не существует\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFound3(){
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertEquals(false, method.invoke(m, ""));
            System.out.println("Товара фирмы без названия не существует\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void inputTest1(){
        provideInput("Haier");
        try {
            Method method = Main.class.getDeclaredMethod("inputCompany", null);
            method.setAccessible(true);
            String output = (String) method.invoke(m);
            assertEquals("Haier", output);
            System.out.println("Ввод Haier подерживается\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void inputTest2(){
        provideInput("");
        try {
            Method method = Main.class.getDeclaredMethod("inputCompany", null);
            method.setAccessible(true);
            String output = (String) method.invoke(m);
            assertNull(output);
            System.out.println("Ввод пустой строки подерживается\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private HashMap<String, Integer> countCheck(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String st: RandomGenerator.getCompanyAll()){
            Integer num = 0;
            for (Appliances<String> app: m.getCompList())
                if (app.getFeature().equals(st))
                    ++num;
            hashMap.put(st, num);
        }
        return hashMap;
    }

    private double costCheck(){
        double sum = 0.0;
        for (Appliances<Double> app: m.getCostList())
            sum += app.getFeature();
        return sum;
    }

    @Test
    public void countTestNotRandom() throws RuntimeException {//вычисление показателей для заданных значений
        System.out.println("Заданные значения!\n");
        try {
            ArrayList<Appliances<String>> arrayListComp = new ArrayList<>();
            arrayListComp.add(new Appliances<>(RandomGenerator.getType(), "LG"));
            arrayListComp.add(new Appliances<>(RandomGenerator.getType(), "Bosch"));
            arrayListComp.add(new Appliances<>(RandomGenerator.getType(), "Siemens"));
            arrayListComp.add(new Appliances<>(RandomGenerator.getType(), "Bosch"));
            arrayListComp.add(new Appliances<>(RandomGenerator.getType(), "Hitachi"));
            m.setArrComp(arrayListComp);

            ArrayList<Appliances<Double>> arrayListCost = new ArrayList<>();
            arrayListCost.add(new Appliances<>(RandomGenerator.getType(), 100.00));
            arrayListCost.add(new Appliances<>(RandomGenerator.getType(), 250.00));
            arrayListCost.add(new Appliances<>(RandomGenerator.getType(), 300.00));
            arrayListCost.add(new Appliances<>(RandomGenerator.getType(), 450.45));
            arrayListCost.add(new Appliances<>(RandomGenerator.getType(), 123.67));
            m.setArrCost(arrayListCost);

            Method method = Main.class.getDeclaredMethod("printList");
            method.setAccessible(true);
            method.invoke(m);

            CompThread compThread = new CompThread(m, "Haier");
            compThread.start();
            compThread.join();
            assertEquals(0, compThread.getResult());
            System.out.printf("Количество товаров компании Haier равно %d\n", compThread.getResult());

            compThread = new CompThread(m, "LG");
            compThread.start();
            compThread.join();
            assertEquals(1, compThread.getResult());
            System.out.printf("Количество товаров компании LG равно %d\n", compThread.getResult());

            compThread = new CompThread(m, "Bosch");
            compThread.start();
            compThread.join();
            assertEquals(2, compThread.getResult());
            System.out.printf("Количество товаров компании Bosch равно %d\n", compThread.getResult());

            compThread = new CompThread(m, "Samsung");
            compThread.start();
            compThread.join();
            assertEquals(0, compThread.getResult());
            System.out.printf("Количество товаров компании Samsung равно %d\n", compThread.getResult());

            compThread = new CompThread(m, "Hitachi");
            compThread.start();
            compThread.join();
            assertEquals(1, compThread.getResult());
            System.out.printf("Количество товаров компании Hitachi равно %d\n", compThread.getResult());

            compThread = new CompThread(m, "Siemens");
            compThread.start();
            compThread.join();
            assertEquals(1, compThread.getResult());
            System.out.printf("Количество товаров компании Siemens равно %d\n", compThread.getResult());

            compThread = new CompThread(m, "Sharp");
            compThread.start();
            compThread.join();
            assertEquals(0, compThread.getResult());
            System.out.printf("Количество товаров компании Sharp равно %d\n\n", compThread.getResult());

            double costSum = 100.00 + 250.00 + 300.00 + 450.45 + 123.67;

            CostThread costThread = new CostThread(m);
            costThread.start();
            try {
                costThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            assertEquals(costCheck(), costThread.getResult());
            System.out.printf("Общая сумма равна %.2f\n\n", costThread.getResult());
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void countTest() throws RuntimeException {//вычисление показателей для случайных значений
        System.out.println("Случайные значения!\n");
        try {
            Method method = Main.class.getDeclaredMethod("printList");
            method.setAccessible(true);
            method.invoke(m);

            HashMap<String, Integer> countFirm = countCheck();

            for (String st: RandomGenerator.getCompanyAll()){
                CompThread compThread = new CompThread(m, st);
                compThread.start();
                try {
                    compThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                assertEquals(countFirm.get(st), compThread.getResult());
                System.out.printf("Количество товаров компании %s равно %d\n", st, compThread.getResult());
            }

            CostThread costThread = new CostThread(m);
            costThread.start();
            try {
                costThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            assertEquals(costCheck(), costThread.getResult());
            System.out.printf("Общая сумма равна %.2f\n\n", costThread.getResult());
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

