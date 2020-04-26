package br.com.vinirib.provider.pact.account.pact;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.VerificationReports;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import br.com.vinirib.provider.pact.account.dto.BalanceDTO;
import br.com.vinirib.provider.pact.account.service.AccountService;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@Provider("AccountBalanceProvider")
@PactBroker(host = "pact_broker", port = "80")
@VerificationReports
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountProviderPactTest {

    @LocalServerPort
    private int localServerPort;

    @MockBean
    private AccountService accountService;

    private double MAX_BALANCE = 29999.00;
    private double MIN_BALANCE = -100.00;

    @BeforeEach
    void setUp(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", localServerPort, "/"));
    }

    @BeforeAll
    static void enablePublishingPact() {
        System.setProperty("pact.verifier.publishResults", "true");
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("get balance of accountId 1")
    public void getBalanceDTO() {
        final BalanceDTO balanceDTO = BalanceDTO
                .builder()
                .clientId(1)
                .accountId(1)
                .balance(Money.of(getRandomAmount(),
                        Monetary.getCurrency("BRL")))
                .build();
        given(accountService.getBalanceByAccountId(eq(1))).willReturn(Optional.of(balanceDTO));

    }

    @State("No accounts exist from accountId 1000")
    public void getBalanceDTONotWorking() {
        given(accountService.getBalanceByAccountId(eq(1000))).willReturn(Optional.empty());
    }

    private double getRandomAmount() {
        return Math.random() * (MAX_BALANCE - MIN_BALANCE) + MIN_BALANCE;
    }
}
