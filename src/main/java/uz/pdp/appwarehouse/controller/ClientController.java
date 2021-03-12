package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.payload.ClientDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;
import uz.pdp.appwarehouse.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepository clientRepository;

    @PostMapping
    public Result add(@RequestBody ClientDto clientDto) {
        return clientService.add(clientDto);
    }

    @GetMapping
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @GetMapping("/{phone}")
    public Client getByPhone(@PathVariable String phone) {
        return clientRepository.findByPhoneNumber(phone);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody ClientDto clientDto) {
        return clientService.edit(id, clientDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return clientService.delete(id);
    }
}
