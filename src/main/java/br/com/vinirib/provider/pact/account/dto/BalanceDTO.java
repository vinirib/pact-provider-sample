package br.com.vinirib.provider.pact.account.dto;

import br.com.vinirib.provider.pact.account.entity.Account;
import lombok.Builder;
import lombok.Data;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
public class BalanceDTO implements Serializable {

    private Integer clientId;
    private Integer accountId;
    private MonetaryAmount balance;

    public static BalanceDTO fromAccountToDTO(Account accountFound) {
        Objects.requireNonNull(accountFound, "Account must not null to build BalanceDTO");
        return BalanceDTO.builder()
                .accountId(accountFound.getId())
                .clientId(accountFound.getClientId())
                .balance(accountFound.getBalance())
                .build();

    }
}