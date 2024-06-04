package edu2.innotech;

import lombok.Setter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;

public class Account extends AccountEntry {
    private EntityHistory entityHistory = new EntityHistory();
    @Setter
    private Boolean saveEntityHistoryFlag = false;

    public Account(String clientName) {
        setClientName(clientName);
        moneyMap = new HashMap<>();
        setSaveEntityHistoryFlag(true);
    }

    public Account(Account account) {
        setClientName(account.getClientName());
        moneyMap = account.getMoneyMap();
        setSaveEntityHistoryFlag(true);
    }

    public void setClientName(String clientName) {
        if ((clientName == null) || (clientName.isEmpty())) {
            throw new IllegalArgumentException("Имя не может быть null или пустым!");
        }
        String prevClientName = this.clientName;
        if (saveEntityHistoryFlag) entityHistory.addAccountChange(x -> x.setClientName(prevClientName));
        this.clientName = clientName;
    }

    public boolean putMoneyToMap(Currency currency, BigInteger moneyCount) {
       if (!(moneyCount.longValue() > 0)) {
           throw new IllegalArgumentException("Количество валюты " + moneyCount + " не может быть отрицательным!");
       }
       if (!moneyMap.containsKey(currency)) {
           moneyMap.put(currency, moneyCount);
           if (saveEntityHistoryFlag) entityHistory.addAccountChange(x -> x.removeMoneyInMap(currency));

           return false;
       }
       else {
           BigInteger prevMoneyCount = moneyMap.get(currency);
           if (saveEntityHistoryFlag) entityHistory.addAccountChange(x -> x.putMoneyToMap(currency, prevMoneyCount));
           replaceMoneyInMap(currency, moneyCount);

           return true;
       }
    }

    public void removeMoneyInMap(Currency currency) {
        moneyMap.remove(currency);
    }

    private void replaceMoneyInMap(Currency currency, BigInteger moneyCount) {
        moneyMap.replace(currency, moneyCount);
    }

    public Account undo() {
        try {
            setSaveEntityHistoryFlag(false);
            entityHistory.doOneAccountChange(this);
            entityHistory.removeLast();
        }
        finally {
            setSaveEntityHistoryFlag(true);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Счет : {" +
                "Клиент = '" + clientName + '\'' +
                ", Деньги = " + moneyMap +
                ", v." + entityHistory.getListChangesSize() +
                '}';
    }

    public AccountEntry createSave() {
        AccountEntry accountSave = new AccountEntry(this.getClientName(), this.getMoneyMap());
        entityHistory.addAccountSave(accountSave);
        return new AccountEntry(accountSave.getClientName(), accountSave.getMoneyMap());
    }

    public void loadAccountSave(int saveIndex) {
        AccountEntry accountSave = entityHistory.getAccountSaveByIndex(saveIndex);
        setClientName(accountSave.getClientName());
        moneyMap = accountSave.getMoneyMap();
        entityHistory.clearAcountChanges();
    }
}
