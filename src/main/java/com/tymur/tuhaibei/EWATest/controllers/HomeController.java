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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author tymur.tuhaibei
 */

@Controller
public class HomeController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ContractRepository contractRepository;

    @GetMapping("/")
    public String home(Model model) {
        final var clients =
                StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                        .toList()
                        .stream()
                        .collect(Collectors.toMap(Client::getId, Function.identity()));
        final var contracts =
                StreamSupport.stream(contractRepository.findAll().spliterator(), false)
                        .toList()
                        .stream()
                        .collect(Collectors.toMap(Contract::getClientId, Function.identity()));
        final var createForms =
                Stream.concat(clients.keySet().stream(), contracts.keySet().stream())
                        .distinct()
                        .map(key -> CreateForm.builder()
                                .firstName(clients.getOrDefault(key, null).getFirstName())
                                .middleName(clients.getOrDefault(key, null).getMiddleName())
                                .lastName(clients.getOrDefault(key, null).getLastName())
                                .phoneNumber(clients.getOrDefault(key, null).getPhoneNumber())
                                .dayOfBirthday(String.valueOf(clients.getOrDefault(key, null).getDayOfBirthday()))
                                .clientId(contracts.getOrDefault(key, null).getClientId())
                                .contractId(contracts.getOrDefault(key, null).getId())
                                .contractNumber(contracts.getOrDefault(key, null).getContractNumber())
                                .contractStart(String.valueOf(contracts.getOrDefault(key, null).getContractStart()))
                                .contractEnd(String.valueOf(contracts.getOrDefault(key, null).getContractEnd()))
                                .sumInsured(Long.parseLong(contracts.getOrDefault(key, null).getSumInsured().toString()))
                                .contractPaymentAmount(Long.parseLong(contracts.getOrDefault(key, null).getContractPaymentAmount().toString()))
                                .build())
                        .toList();
        model.addAttribute("createForms", createForms);
        return "home";
    }

    @PostMapping("/{clientId}/{contractId}/remove")
    @Transactional
    private String remove(@PathVariable(value = "clientId") long clientId,
                          @PathVariable(value = "contractId") long contractId,
                          Model model) {
        contractRepository.delete(contractRepository.findById(contractId).orElseThrow());
        clientRepository.delete(clientRepository.findById(clientId).orElseThrow());
        return "redirect:/";
    }

}
