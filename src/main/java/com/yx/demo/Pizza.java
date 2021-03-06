package com.yx.demo;

import lombok.Data;

/**
 * @Author yangxin
 * @Date 2022.2.11 14:35
 * @Version 1.0
 */
@Data
public class Pizza {
    private String dough = "";
    private String sauce = "";
    private String topping = "";}

@Data
abstract class PizzaBuilder {
    protected Pizza pizza;

    public void createNewPizzaProduct() { pizza = new Pizza(); }

    public abstract void buildDough();
    public abstract void buildSauce();
    public abstract void buildTopping();}


/** "ConcreteBuilder" */
@Data
class HawaiianPizzaBuilder extends PizzaBuilder {
    @Override
    public void buildDough()   { pizza.setDough("cross"); }
    @Override
    public void buildSauce()   { pizza.setSauce("mild"); }
    @Override
    public void buildTopping() { pizza.setTopping("ham+pineapple"); }}

/** "ConcreteBuilder" */
@Data
class SpicyPizzaBuilder extends PizzaBuilder {
    @Override
    public void buildDough()   { pizza.setDough("pan baked"); }
    @Override
    public void buildSauce()   { pizza.setSauce("hot"); }
    @Override
    public void buildTopping() { pizza.setTopping("pepperoni+salami"); }}

class Waiter {
    private PizzaBuilder pizzaBuilder;

    public void setPizzaBuilder (PizzaBuilder pb) { pizzaBuilder = pb; }
    public Pizza getPizza() { return pizzaBuilder.getPizza(); }

    public void constructPizza() {
        pizzaBuilder.createNewPizzaProduct();
        pizzaBuilder.buildDough();
        pizzaBuilder.buildSauce();
        pizzaBuilder.buildTopping(); }}

/** A customer ordering a pizza. */
class BuilderExample {
    public static void main(String[] args) {
        Waiter waiter = new Waiter();
        PizzaBuilder hawaiian_pizzabuilder = new HawaiianPizzaBuilder();
        PizzaBuilder spicy_pizzabuilder = new SpicyPizzaBuilder();

        waiter.setPizzaBuilder ( hawaiian_pizzabuilder );
        waiter.constructPizza();

        Pizza pizza = waiter.getPizza();
        System.out.println(pizza); }}