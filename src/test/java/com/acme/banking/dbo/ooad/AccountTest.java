package com.acme.banking.dbo.ooad;

import com.acme.banking.dbo.ooad.domain.CheckingAccount;
import com.acme.banking.dbo.ooad.domain.SavingAccount;
import org.hibernate.annotations.Check;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AccountTest {

    @Test
    public void shouldReturnAmountWhenGetAmount() {
        //region Given
        SavingAccount account = new SavingAccount(123L, 2);
        //endregion

        //region Then
        assertThat(account.getAmount()).isEqualTo(2.0);
        //end region
    }

    @Test
    public void shouldReturnIdWhenGetId() {
        //region Given
        SavingAccount account = new SavingAccount(123L, 1);
        //endregion

        //region Then
        assertThat(account.getId()).isEqualTo(123L);
        //end region
    }

    @Test
    public void shouldReturnDecreaseAmountWhenWithDraw() {
        //region Given
        SavingAccount account = new SavingAccount(123L, 100);
        //endregion

        //region When
        account.withdraw(5.0);
        //endregion

        //region Then
        assertThat(account.getAmount()).isEqualTo(95.0);
        //end region
    }

    @Test
    public void shouldReturnIncreaseAmountWhenDeposit() {
        //region Given
        SavingAccount account = new SavingAccount(123L, 100);
        //endregion

        //region When
        account.deposit(5.0);
        //endregion

        //region Then
        assertThat(account.getAmount()).isEqualTo(105.0);
        //end region
    }

    @Test
    public void shouldReturnAccountWhenCreateAccount() {
        //region When
        CheckingAccount account = new CheckingAccount(123L, 100, 5);
        //endregion

        //region Then
        assertThat(account.getId()).isEqualTo(123L);
        assertThat(account.getAmount()).isEqualTo(100.0);
        //end region
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhenWithDrawWithGreaterAmount() {
        //region Given
        SavingAccount account = new SavingAccount(123L, 100);
        //endregion

        //region When
        account.withdraw(101);
        //endregion
    }

    @Test(expected = IllegalStateException.class)
    public void shouldReturnIllegalStateExceptionWhenWithDrawWithGreaterAmountForCheckingAccount() {
        //region When
        CheckingAccount account = new CheckingAccount(123L, 100, 5);
        //endregion

        //region When
        account.withdraw(110);
        //endregion
    }
}
