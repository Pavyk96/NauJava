package ru.Daniil.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.Daniil.NauJava.model.BankCard;
import ru.Daniil.NauJava.repo.BankCardRepository;

import java.util.List;

@RestController
@RequestMapping("/custom/bankCards")
public class BankCardController {

    @Autowired
    private BankCardRepository bankCardRepository;

    @GetMapping("/findByEmail")
    public List<BankCard> findByEmail(@RequestParam String email) {
        return bankCardRepository.findByUserEmail(email);
    }
}