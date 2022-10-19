package org.example;

/*
* Результаты теста счетчика модернизируемого из двух потоков
_______________________________________________________________________________________________________________
количество итераций в тесте|    10_000  |   50_000  |   примечания                                            |
--------------------------------------------------------------------------------------------------------------|
simple   |    times == 3  |   times == 25 |   все результаты парные                                           |
volatile |    times == 15 |   times == 37 |   j == 43997 после второй паузы данные обновились                 |
sync     |    times == 0  |   times == 0  |   было два результата которые не подтвердились после второй паузы |
both     |    times == 0  |   times == 0  |   чисто                                                           |
---------------------------------------------------------------------------------------------------------------
* */

public class ConcurrentCounterTest {
    public static void main(String[] args) throws InterruptedException {

        for (CONTAINERS c: CONTAINERS.values()) {
            System.out.println(c.getName());
            runTest(c.getContainer(), 50_000, 50);
        }
    }

    private static void runTest(Countable c, int iterations, int sleepTime) throws InterruptedException {
        int times = 0;
        for (int i = 0; i < iterations; i++) {
            Thread[] threads = {new Thread(c::incrementCounter), new Thread(c::incrementCounter),
                    new Thread(c::incrementCounter)};
            for (Thread t : threads) t.start();
            Thread.sleep(sleepTime);
            if (c.getCounter() != threads.length) {
                System.out.print("first counter == " + c.getCounter() + ", i == " + i + ", ");
            }
            Thread.sleep(sleepTime);
            if (c.getCounter() != threads.length) {
                System.out.print("second counter == " + c.getCounter() + ". ");
                times++;
            }

            c.setCounter(0);
        }
        System.out.println();
        System.out.println("times == " + times);
    }
}

interface Countable {

    int getCounter();
    void setCounter(int counter);
    void incrementCounter();
}
enum CONTAINERS {
    CONTAINER(new Container(), "simple"),
    CONTAINER_VOLATILE(new ContainerVolatile(), "volatile"),
    CONTAINER_SYNC(new ContainerSync(), "sync"),
    CONTAINER_VOLATILE_SYNC(new ContainerVolatileSync(), "both");

    private final Countable container;

    private final String name;
    CONTAINERS(Countable container, String name) {
        this.container = container;
        this.name = name;
    }

    public Countable getContainer() {
        return container;
    }

    public String getName() {
        return name;
    }

}
class Container implements Countable {

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    @Override
    public void incrementCounter() {
        this.counter++;
    }
}
class ContainerVolatile implements Countable {

    volatile private int i = 0;

    public int getCounter() {
        return i;
    }

    public void setCounter(int counter) {
        this.i = counter;
    }
    @Override
    public void incrementCounter() {
        this.i++;
    }
}
class ContainerSync implements Countable {

    private final Object o = new Object();
    private int i = 0;

    public int getCounter() {
        synchronized (o) {
            return i;
        }
    }

    public void setCounter(int counter) {
        synchronized (o) {
            this.i = counter;
        }
    }
    @Override
    public void incrementCounter() {
        synchronized (o) {
            this.i++;
        }
    }
}

class ContainerVolatileSync implements Countable {

    private final Object o = new Object();
    volatile private int i = 0;

    public int getCounter() {
        synchronized (o) {
            return i;
        }
    }

    public void setCounter(int counter) {
        synchronized (o) {
            this.i = counter;
        }
    }

    @Override
    public void incrementCounter() {
        synchronized (o) {
            this.i++;
        }
    }
}
