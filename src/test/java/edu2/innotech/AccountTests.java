package edu2.innotech;

import org.junit.jupiter.api.*;

import java.math.BigInteger;

public class AccountTests {
    @Test
    @DisplayName("Создание объекта Account")
    public void accountCreate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Account(""));
    }

    @Test
    @DisplayName("Проверка отрицательных сумм")
    public void checkMinusSumma() {
        Account account1 = new Account("Client");
        Assertions.assertThrows(IllegalArgumentException.class, () -> account1.putMoneyToMap(Currency.RUR, BigInteger.valueOf(-500)));
    }

    @Test
    @DisplayName("Наполнение Account деньгами")
    public void fillMoneyMap() {
        Account account1 = new Account("Client");
        for (Currency cur : Currency.values()) {
            account1.putMoneyToMap(cur, BigInteger.valueOf(100));
        }
    }

    @Test
    @DisplayName("Копирующий конструктор Account")
    public void copyAccountCreate() {
        Account account1 = new Account("Client");
        account1.putMoneyToMap(Currency.RUR, BigInteger.valueOf(100));
        account1.putMoneyToMap(Currency.BYN, BigInteger.valueOf(200));
        Account account2 = new Account(account1);
        Assertions.assertEquals(account1, account2);
    }

    @Test
    @DisplayName("Замена суммы денег")
    public void changeMoneyMap() {
        Account account1 = new Account("Client");
        for (Currency cur : Currency.values()) {
            account1.putMoneyToMap(cur, BigInteger.valueOf(100));
        }
        account1.putMoneyToMap(Currency.RUR, BigInteger.valueOf(500));
        Assertions.assertEquals(account1.getMoneyCountFromMap(Currency.RUR), BigInteger.valueOf(500));
    }

    @Test
    @DisplayName("Проверка отмены действий")
    public void checkUndo() {
        Account account1 = new Account("Client");
        account1.putMoneyToMap(Currency.RUR, BigInteger.valueOf(100));
        account1.setClientName("Василий Иванов");
        account1.putMoneyToMap(Currency.RUR, BigInteger.valueOf(300));

        account1.undo();
        Assertions.assertEquals(account1.getMoneyCountFromMap(Currency.RUR), BigInteger.valueOf(100));

        account1.undo();
        Assertions.assertEquals(account1.getClientName(), "Client");

        account1.undo();
        Account account2 = new Account("Client");
        Assertions.assertEquals(account1, account2);

        Assertions.assertThrows(IllegalStateException.class, account1::undo);
    }

    @Test
    @DisplayName("Проверка создания раздельных сохранений")
    public void checkCreateSaves() {
        Account account1 = new Account("Client");
        account1.putMoneyToMap(Currency.RUR, BigInteger.valueOf(100));
        account1.putMoneyToMap(Currency.USD, BigInteger.valueOf(300));
        AccountEntry accountSave10 = account1.createSave();

        Account account2 = new Account(account1);
        AccountEntry accountSave20 = account2.createSave();

        Assertions.assertEquals(account1, account2);
        Assertions.assertEquals(account1.hashCode(), account2.hashCode());
        Assertions.assertEquals(accountSave10, accountSave20);

        account2.putMoneyToMap(Currency.TRY, BigInteger.valueOf(250));
        AccountEntry accountSave21 = account2.createSave();
        Assertions.assertNotEquals(account1, account2);
        Assertions.assertNotEquals(accountSave20, accountSave21);
    }

    @Test
    @DisplayName("Проверка загрузки сохранений")
    public void checkLoadSaves() {
        Account account1 = new Account("Client");
        account1.putMoneyToMap(Currency.RUR, BigInteger.valueOf(100));
        account1.putMoneyToMap(Currency.USD, BigInteger.valueOf(300));
        account1.createSave();

        Account account2 = new Account(account1);
        account2.createSave();

        account2.putMoneyToMap(Currency.TRY, BigInteger.valueOf(250));
        account2.createSave();
        Assertions.assertNotEquals(account1, account2);

        account2.loadAccountSave(0);
        Assertions.assertEquals(account1, account2);

        account2.loadAccountSave(1);
        Assertions.assertNotEquals(account1, account2);
    }

    @Test
    @DisplayName("Проверка Account.toString()")
    public void accountToString() {
        Account account1 = new Account("Client");
        account1.putMoneyToMap(Currency.RUR, BigInteger.valueOf(100));
        account1.putMoneyToMap(Currency.USD, BigInteger.valueOf(300));

        Account account2 = new Account("Client");
        account2.putMoneyToMap(Currency.RUR, BigInteger.valueOf(100));
        account2.putMoneyToMap(Currency.USD, BigInteger.valueOf(300));

        Assertions.assertEquals(account1.toString(), account2.toString());
    }
}
