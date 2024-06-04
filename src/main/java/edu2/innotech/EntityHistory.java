package edu2.innotech;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class EntityHistory {
    private List<AccountChange> accountChanges = new ArrayList<>();
    private List<AccountEntry> accountSaves = new ArrayList<>();
    public void addAccountChange(AccountChange accountChange) {accountChanges.add(accountChange);}
    public void doOneAccountChange(Account account) {
        if (isUndoPossible()) {
            accountChanges.get(accountChanges.size()-1).doOneAccountChange(account);
        }
        else throw new IllegalStateException("Нельзя выполнить отмену действия, объект перешел в исходное состояние!");
    }

    public Boolean isUndoPossible() {
        return (!accountChanges.isEmpty());
    }

    public int getListChangesSize() {
        return accountChanges.size();
    }

    public void removeLast() {accountChanges.remove(accountChanges.size()-1);}

    public void clearAcountChanges() {
        accountChanges.clear();
    }
    public void addAccountSave(AccountEntry accountEntry) {
        accountSaves.add(accountEntry);
    }

    public int getListSavesSize() {
        return accountSaves.size();
    }

    public AccountEntry getAccountSaveByIndex(int saveIndex) {
        if (saveIndex > getListSavesSize() - 1) {
            throw new IndexOutOfBoundsException("Недопустимый индекс сохранения для загрузки! " +
                    saveIndex + " > " + (getListSavesSize() - 1));
        }

        AccountEntry accountSave = accountSaves.get(saveIndex);
        return new AccountEntry(accountSave.getClientName(), accountSave.getMoneyMap());
    }

}
