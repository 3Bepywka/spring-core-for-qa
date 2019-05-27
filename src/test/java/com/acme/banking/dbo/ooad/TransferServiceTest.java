package com.acme.banking.dbo.ooad;

import com.acme.banking.dbo.spring.dao.AccountRepository;
import com.acme.banking.dbo.spring.domain.Account;
import com.acme.banking.dbo.spring.service.TransferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:test-spring-context.xml", "classpath:spring-context.xml"})
@ActiveProfiles("test")
public class TransferServiceTest {
    @Autowired
    private TransferService transferService;
    @MockBean
    private AccountRepository accounts;

    @Test
    public void shouldUpdateAccountsStateWhenTransfer() {
        //region Given
        Account fromAccountStub = mock(Account.class);
        when(fromAccountStub.getAmount()).thenReturn(100d);
        when(accounts.findById(0L)).thenReturn(Optional.of(fromAccountStub));
        Account toAccountStub = mock(Account.class);
        when(toAccountStub.getAmount()).thenReturn(0d);
        when(accounts.findById(1L)).thenReturn(Optional.of(toAccountStub));
        //endregion

        //region When
        transferService.transfer(0L, 1L, 100);
        //endregion

        //region Then
        verify(fromAccountStub, times(1)).setAmount(0d);
        verify(toAccountStub).setAmount(100d);
        //endregion
    }

    @Test
    public void shouldFindByIdForAccountsWhenTransfer() {
        //region Given
        Account fromAccountStub = mock(Account.class);
        when(fromAccountStub.getAmount()).thenReturn(100d);
        when(accounts.findById(0L)).thenReturn(Optional.of(fromAccountStub));
        Account toAccountStub = mock(Account.class);
        when(toAccountStub.getAmount()).thenReturn(0d);
        when(accounts.findById(1L)).thenReturn(Optional.of(toAccountStub));
        //endregion

        //region When
        transferService.transfer(0L, 1L, 100);
        //endregion

        //region Then
        verify(accounts, times(1)).findById(0L);
        verify(accounts, times(1)).findById(1L);
        //endregion
    }

    @Test(expected = IllegalStateException.class)
    public void shouldReturnExceptionWhenFirstAccountNotFoundWhenTransfer() {
        //region When
        transferService.transfer(0L, 1L, 100);
        //endregion
    }

    @Test(expected = IllegalStateException.class)
    public void shouldReturnExceptionWhenSecondAccountNotFoundWhenTransfer() {
        //region Given
        Account fromAccountStub = mock(Account.class);
        when(fromAccountStub.getAmount()).thenReturn(100d);
        when(accounts.findById(0L)).thenReturn(Optional.of(fromAccountStub));
        //endregion
        //region When
        transferService.transfer(0L, 1L, 100);
        //endregion
    }
}
