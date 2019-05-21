package com.acme.banking.dbo.ooad;

import com.acme.banking.dbo.ooad.dal.StubAccountRepository;
import com.acme.banking.dbo.ooad.domain.Account;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class StubAccountRepositoryTest {

    @Test
    public void shouldReturnStubSavingAccountWhenFindById() {
        //region Given
        StubAccountRepository stubAccRepo = new StubAccountRepository(123);
        //endregion

        //region When
        Account account1 = stubAccRepo.findById(100L);
        Account account2 = stubAccRepo.findById(123L);
        //end region

        //region Then
        assertThat(account1.getAmount()).isEqualTo(account2.getAmount()).isEqualTo(123.0);
        assertThat(account1.getId()).isEqualTo(account2.getId()).isEqualTo(0);
        //endregion
    }
}
