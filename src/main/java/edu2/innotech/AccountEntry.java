package edu2.innotech;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class AccountEntry {
    @Getter
    protected String clientName;
    protected HashMap<Currency, BigInteger> moneyMap;

    public HashMap<Currency, BigInteger> getMoneyMap() {
        return new HashMap<Currency, BigInteger>(moneyMap);
    }

    public BigInteger getMoneyCountFromMap(Currency currency) {
        return moneyMap.get(currency);
    }

    // Account и AccountEntry equal только если совпадает значимое содержимое в полях AccountEntry
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntry accountEntry = (AccountEntry) o;
        return Objects.equals(clientName, accountEntry.clientName) && Objects.equals(moneyMap, accountEntry.moneyMap);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hash(clientName);
        hash = 23 * hash + Objects.hash(moneyMap);
        return hash;
    }
}
