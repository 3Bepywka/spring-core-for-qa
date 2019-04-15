package com.acme.banking.dbo.ooad.service;

import com.acme.banking.dbo.ooad.domain.Account;

public class TransferService {
    private CurrencyService currencyService =
            Registry.getConcreteFactory("component-id").getCurrencySerice();

    public void withdraw(Account from, double rurAmount) { //PL/SQL
        //polymorphism
        from.withdraw(currencyService.exchange(rurAmount));
    }
}
