package ru.vsu.atm.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.atm.dto.TransferRequest;
import ru.vsu.atm.dto.TransferResponse;
import ru.vsu.atm.service.AccountService;

@RestController
@RequestMapping("/transfers")
public class TransferController {
    private final AccountService accountService;

    public TransferController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public TransferResponse transfer(@RequestBody TransferRequest request) {
        accountService.transfer(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
        return new TransferResponse(request.getFromAccountId(), request.getToAccountId(), request.getAmount(), "SUCCESS");
    }
}