package com.acme.banking.dbo.ooad;

import com.acme.banking.dbo.spring.dao.AccountRepository;
import com.acme.banking.dbo.spring.domain.Account;
import com.acme.banking.dbo.spring.domain.CheckingAccount;
import com.acme.banking.dbo.spring.domain.SavingAccount;
import com.acme.banking.dbo.spring.service.ReportingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:test-spring-context.xml", "classpath:spring-context.xml"})
@ActiveProfiles("test")
public class ReportingServiceTest {

    @Autowired
    private ReportingService reportingService;
    @MockBean
    private AccountRepository accounts;


    @Test(expected = IllegalStateException.class)
    public void shouldGetExceptionWhenNoAccountFound() {
        reportingService.accountReport(0L);
    }

    @Test
    public void shouldGetReportForAccountWhenAccountExists() {
        //region Given
        Account stubAccount = mock(Account.class);
        when(stubAccount.toString()).thenReturn("123 150.0");
        when(accounts.findById(anyLong())).thenReturn(Optional.of(stubAccount));
        //endregion

        //region When
        String actualReport = reportingService.accountReport(123L);
        //endregion

        //region Then
        assertThat(actualReport)
                .contains(String.valueOf(123L))
                .contains("150.0");
        //endregion
    }

    @Test
    public void shouldReturnDefaultLayoutMarker() {
        //region Given
        Account stubAccount = mock(Account.class);
        when(stubAccount.toString()).thenReturn("123 150.0");
        when(accounts.findById(anyLong())).thenReturn(Optional.of(stubAccount));
        //endregion

        //region When
        String actualReport = reportingService.accountReport(123L);
        //endregion

        //region Then
        assertThat(actualReport)
                .contains("##");
        //endregion
    }

    @DirtiesContext
    @Test
    public void shouldReturnSetLayoutMarker() {
        //region Given
        Account stubAccount = mock(Account.class);
        when(stubAccount.toString()).thenReturn("123 150.0");
        when(accounts.findById(anyLong())).thenReturn(Optional.of(stubAccount));
        reportingService.setLayoutMarker(">");
        //endregion

        //region When
        String actualReport = reportingService.accountReport(123L);
        //endregion

        //region Then
        assertThat(actualReport)
                .contains(">>");
        //endregion
    }

    @Test
    public void shouldReturnCheckingAccountInfoWhenToString() {
        CheckingAccount account = new CheckingAccount(100, 5, "email@email.com");

        assertTrue(account.toString().equals("0 100.0 C 5.0"));
    }

    @Test
    public void shouldReturnSavingAccountInfoWhenToString() {
        SavingAccount account = new SavingAccount(150,"email@email.com");

        assertTrue(account.toString().equals("0 150.0 S"));
    }

    @Test
    public void shouldChangeAccountAmountWhenSetAmount() {
        CheckingAccount account = new CheckingAccount(100, 5, "email@email.com");

        assertTrue(account.toString().equals("0 100.0 C 5.0"));

        account.setAmount(150);

        assertTrue(account.toString().equals("0 150.0 C 5.0"));
    }
}
