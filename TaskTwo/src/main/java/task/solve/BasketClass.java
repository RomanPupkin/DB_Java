package task.solve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class BasketClass implements Basket {

    private HashMap<String, Integer> ShoppingBasket;

    public BasketClass(String product, int quantity) {
        this.ShoppingBasket = new HashMap<>();
        this.ShoppingBasket.put(product, quantity);
    }

    @Override
    public void addProduct(String product, int quantity) {
//        if (this.ShoppingBasket.containsKey(product)) {
//            this.ShoppingBasket.remove(product);
//        }
        this.ShoppingBasket.put(product, quantity);
    }

    @Override
    public void removeProduct(String product) {
        this.ShoppingBasket.remove(product);
    }

    @Override
    public void updateProductQuantity(String product, int quantity) {
        if (this.ShoppingBasket.containsKey(product)) {
            this.ShoppingBasket.put(product, quantity);
        }
        throw new NoSuchElementException("basket is not contains product");
//        this.ShoppingBasket.remove(product);
//        this.ShoppingBasket.put(product, quantity);
    }

    @Override
    public List<String> getProducts() {
        if (this.ShoppingBasket.isEmpty()) {
            return null;
        } else {
            List<String> tempArray = new ArrayList<>(this.ShoppingBasket.size());
            tempArray.addAll(this.ShoppingBasket.keySet());
            return tempArray;
            //return new ArrayList<>(this.ShoppingBasket.size()).addAll(this.ShoppingBasket.keySet());
        }
    }

    public void viewBasket() {
        System.out.println(this.ShoppingBasket);
    }

    public int getProductQuantity(String product) {
        if (this.ShoppingBasket.containsKey(product)) {
            return this.ShoppingBasket.get(product);
        }
        throw new NoSuchElementException("no such product");
    }
}
