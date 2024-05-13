package com.alextayron.schedule_finance.client.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alextayron.schedule_finance.client.entity.Client;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import com.alextayron.schedule_finance.client.service.ClientService;

 


@RestController
@RequestMapping("/v1/clients")
public class ClientController {

    private ClientService clientService;

    private ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody CreateClientDto createClientDto) {
        var clientId = clientService.createClient(createClientDto);
        return ResponseEntity.created(URI.create("/v1/clients/" + clientId.toString())).build();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable("clientId") String clientId) {
        var client = clientService.getClientById(clientId);
       
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.notFound().build();            
        }

    }

    @GetMapping
    public ResponseEntity<List<Client>> listClients(){
        var clients = clientService.listClients();
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Void> updateClientById(@PathVariable("clientId") String clientId, @RequestBody UpdateClientDto updateClientsDto){
        clientService.updateClientById(clientId, updateClientsDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteById(@PathVariable("clientId") String clientId) {
        clientService.deleteById(clientId);
        return ResponseEntity.noContent().build();
        
    }
}
