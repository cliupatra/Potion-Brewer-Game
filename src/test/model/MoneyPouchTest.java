package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyPouchTest {
    MoneyPouch money;

    @BeforeEach
    public void setUp(){
        money = new MoneyPouch();
    }

    @Test
    public void testConstructor(){
        assertEquals(0, money.getMoney());
    }

    @Test
    public void testAddMoney(){
        money.addMoney(20);

        assertEquals(20, money.getMoney());
    }

    @Test
    public void testSpendMoney(){
        money.addMoney(40);
        money.spendMoney(10);

        assertEquals(30, money.getMoney());
    }
}
