package ru.deni1000.accountservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.deni1000.accountservice.dto.AccountDto;
import ru.deni1000.accountservice.dto.CreateAccountRequest;
import ru.deni1000.accountservice.model.Account;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountDto toDto(Account account);

    Account toEntity(CreateAccountRequest createAccountRequest);
}