package ru.deni1000.bookservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.deni1000.bookservice.dto.AccountDto;

@FeignClient(name = "user-service", url = "${user.service.url:http://localhost:8081}")
public interface UserServiceClient {

    @GetMapping("/api/accounts/{id}")
    AccountDto getUserById(@PathVariable("id") Long id);
} 