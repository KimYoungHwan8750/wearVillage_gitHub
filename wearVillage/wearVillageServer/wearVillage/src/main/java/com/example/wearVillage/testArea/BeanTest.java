package com.example.wearVillage.testArea;


public class BeanTest {
    private final BeanTestInterface.innerInterface innerInterface;
    BeanTest(BeanTestInterface.innerInterface innerInterface){
        this.innerInterface = innerInterface;
    }

    void TestMethod(){
    innerInterface.printTest();
    }
    public static void main(String[] args) {
        BeanTestClass beanTestClass = new BeanTestClass();
        BeanTest beanTest = new BeanTest(beanTestClass);
        beanTest.TestMethod();
    }
}
