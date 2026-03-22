package ru.Daniil.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.Daniil.NauJava.model.BankCard;
import ru.Daniil.NauJava.repo.BankCardRepository;

@Controller
@RequestMapping("/view/bankCards")
public class BankCardViewController {

    @Autowired
    private BankCardRepository bankCardRepository;

    @GetMapping("/list")
    public String getBankCardList(Model model) {
        Iterable<BankCard> bankCards = bankCardRepository.findAll();
        model.addAttribute("bankCards", bankCards);
        return "bankCards";
    }
}