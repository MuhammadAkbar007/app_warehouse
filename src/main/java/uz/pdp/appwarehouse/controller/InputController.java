package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.service.InputService;

import java.util.List;

@RestController
@RequestMapping("/input")
public class InputController {
    @Autowired
    InputService inputService;
    @Autowired
    InputRepository inputRepository;

    @PostMapping
    public Result add(@RequestBody InputDto inputDto) {
        return inputService.add(inputDto);
    }

    @GetMapping
    public List<Input> getAll() {
        return inputRepository.findAll();
    }

    @GetMapping("/{facture}")
    public Input getByFacture(@PathVariable String facture) {
        return inputRepository.findByFactureNumber(facture);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody InputDto inputDto) {
        return inputService.edit(id, inputDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return inputService.delete(id);
    }
}
