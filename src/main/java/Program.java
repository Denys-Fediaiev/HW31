import java.util.*;

public class Program {

    public static void main(String[] args) {

        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
        Product product1 = new Product("prod");
        Queue<Product> products = new LinkedList<>();

        products.add(product1);
        store.setProducts(products);
        new Thread(producer).start();
        new Thread(consumer).start();
    }

    // Класс Магазин, хранящий произведенные товары
    static class Store {
        public Queue<Product> products = new LinkedList<>();

        public void setProducts(Queue<Product> products) {
            this.products = products;
        }

        public Queue<Product> getProducts() {
            return products;
        }

        public synchronized void get() {
            while (products.size() < 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            products.remove();
            System.out.println("Покупатель купил 1 товар");
            System.out.println("Товаров на складе:  " + products.size());
            notify();
        }

        public synchronized void put() {
            while (products.size() >= 3) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            products.add(new Product("product"));
            System.out.println("Производитель добавил 1 товар");
            System.out.println("Товаров на складе: " + products.size());
            notify();
        }
    }

    // класс Производитель
    static class Producer implements Runnable {

        Store store;

        Producer(Store store) {
            this.store = store;
        }

        public void run() {
            for (int i = 1; i < 6; i++) {
                store.put();
            }
        }
    }

    // Класс Потребитель
    static class Consumer implements Runnable {

        Store store;

        Consumer(Store store) {
            this.store = store;
        }

        public void run() {
            for (int i = 1; i < 6; i++) {
                store.get();
            }
        }
    }

    static class Product {
        private String name;

        public Product(String value) {

            name = value;
        }

        String getName() {
            return name;
        }

    }
}