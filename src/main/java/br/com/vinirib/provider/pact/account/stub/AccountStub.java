package br.com.vinirib.provider.pact.account.stub;

import br.com.vinirib.provider.pact.account.entity.Account;
import br.com.vinirib.provider.pact.account.enums.AccountType;
import br.com.vinirib.provider.pact.account.dto.AccountDetailsDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountStub {

    @Getter
    private Map<Integer, Account> accounts;
    private static final Integer NUMBER_OF_STUBS = 10;
    private static final List<AccountType> ACCOUNT_TYPES = Arrays.asList(AccountType.class.getEnumConstants());
    private double MAX_BALANCE = 29999.00;
    private double MIN_BALANCE = -100.00;


    public AccountStub() {
        log.info("\n\n\n\t\t\t\t\t\t ============================ Creating Account Stubs! ============================ \n");
        accounts = createStubs(NUMBER_OF_STUBS);
    }

    private Map<Integer, Account> createStubs(int numberOfStubs) {
        Map<Integer, Account> accounts = new HashMap<>(NUMBER_OF_STUBS);
        for (int i = 1; i <= numberOfStubs; i++) {
            Collections.shuffle(ACCOUNT_TYPES);
            final Account account = Account.builder()
                    .id(i)
                    .clientId(i)
                    .balance(Money.of(getRandomAmount(),
                            Monetary.getCurrency("BRL")))
                    .accountType(ACCOUNT_TYPES.get(0))
                    .build();
            accounts.put(i, account);
        }
        return accounts;
    }

    private double getRandomAmount() {
        return Math.random() * (MAX_BALANCE - MIN_BALANCE) + MIN_BALANCE;
    }

    public List<AccountDetailsDTO> getAllStubsDTOFormat(){
        List<AccountDetailsDTO> clientDetailsDTOS = new ArrayList<>();
        final List<Account> accounts = this.accounts.values().stream()
                .collect(Collectors.toList());
        for (Account account : accounts) {
            clientDetailsDTOS.add(Account.fromEntityToDto(account));
        }
        return clientDetailsDTOS;
    }
}
