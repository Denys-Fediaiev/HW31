import java.util.*;

public class Program {

    public static void main(String[] args) {

        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
        Product product1 = new Product("prod");
        Queue<Product> products = new LinkedList<>() {
            @Override
            public boolean add(Product product) {
                return false;
            }

            @Override
            public boolean offer(Product product) {
                return false;
            }

            @Override
            public Product remove() {
                return null;
            }

            @Override
            public Product poll() {
                return null;
            }

            @Override
            public Product element() {
                return null;
            }

            @Override
            public Product peek() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Product> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Product> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };
        products.add(product1);
        store.setProducts(products);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
// Класс Магазин, хранящий произведенные товары
class Store{
    public Queue<Product> products = new LinkedList<>();
    public void setProducts(Queue<Product> products){
        this.products = products;
    }
    public Queue<Product> getProducts(){
        return products;
    }
    public synchronized void get() {
        while (products.size() < 1) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        products.remove();
        System.out.println("Покупатель купил 1 товар");
        System.out.println("Товаров на складе: " + products.size());
        notify();
    }
    public synchronized void put() {
        while (products.size() >=3) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        products.add(new Product("product"));
        System.out.println("Производитель добавил 1 товар");
        System.out.println("Товаров на складе: " + products.size());
        notify();
    }
}
// класс Производитель
class Producer implements Runnable{

    Store store;
    Producer(Store store){
        this.store=store;
    }
    public void run(){
        for (int i = 1; i < 6; i++) {
            store.put();
        }
    }
}
// Класс Потребитель
class Consumer implements Runnable{

    Store store;
    Consumer(Store store){
        this.store=store;
    }
    public void run(){
        for (int i = 1; i < 6; i++) {
            store.get();
        }
    }
}
class Product{
    private String name;
    public Product(String value){

        name=value;
    }
    String getName(){return name;}

}