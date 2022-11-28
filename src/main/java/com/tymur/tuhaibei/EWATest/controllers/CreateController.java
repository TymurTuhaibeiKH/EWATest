package com.tymur.tuhaibei.EWATest.controllers;

import com.tymur.tuhaibei.EWATest.models.Client;
import com.tymur.tuhaibei.EWATest.models.Contract;
import com.tymur.tuhaibei.EWATest.models.CreateForm;
import com.tymur.tuhaibei.EWATest.repo.ClientRepository;
import com.tymur.tuhaibei.EWATest.repo.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author tymur.tuhaibei
 */
@Controller
public class CreateController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ContractRepository contractRepository;

    @GetMapping("/createForm")
    public String createForm(Model model) {
        return "createForm";
    }

    @PostMapping("/createForm")
    @Transactional
    public String createForm(@ModelAttribute("createForm") CreateForm createForm,
                             Model model) {
        final var client = Client.builder()
                .firstName(createForm.getFirstName())
                .middleName(createForm.getMiddleName())
                .lastName(createForm.getLastName())
                .phoneNumber(createForm.getPhoneNumber())
                .dayOfBirthday(LocalDate.parse(createForm.getDayOfBirthday()))
                .build();
        clientRepository.save(client);
        final var contract = Contract.builder()
                .clientId(client.getId())
                .contractNumber(UUID.randomUUID().toString())
                .contractStart(LocalDate.parse(createForm.getContractStart()))
                .contractEnd(LocalDate.parse(createForm.getContractEnd()))
                .sumInsured(BigDecimal.valueOf(createForm.getSumInsured()))
                .contractPaymentAmount(BigDecimal.valueOf(createForm.getContractPaymentAmount()))
                .build();
        contractRepository.save(contract);
        return "redirect:/";
    }

}
