package tech.shunzi.testdev.service;

public interface TestMultiInject {
    default void test() {
        System.out.println("Test multi inject");
    }
}
