package edu2.innotech;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {

        Account account1 = new Account("Client1");
        account1.putMoneyToMap(Currency.RUR, BigInteger.valueOf(1000));
        account1.putMoneyToMap(Currency.USD, BigInteger.valueOf(100));
        account1.setClientName("Client1+");
        account1.putMoneyToMap(Currency.BYN, BigInteger.valueOf(500));
        account1.putMoneyToMap(Currency.BYN, BigInteger.valueOf(700));
        account1.setClientName("Client1++");
        System.out.println(account1);

        account1.createSave();

        account1.undo().undo();
        System.out.println(account1);

        account1.undo().undo().undo();
        System.out.println(account1);

        account1.loadAccountSave(0);
        System.out.println(account1);

        account1.putMoneyToMap(Currency.TRY, BigInteger.valueOf(150));
        account1.setClientName("Client1+++");
        System.out.println(account1);

        account1.undo().undo();
        System.out.println(account1);
    }
}