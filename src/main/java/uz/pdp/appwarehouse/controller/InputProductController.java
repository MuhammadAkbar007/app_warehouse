package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.InputProductRepository;
import uz.pdp.appwarehouse.service.InputProductService;

import java.util.List;

@RestController
@RequestMapping("/inputProduct")
public class InputProductController {
    @Autowired
    InputProductService inputProductService;
    @Autowired
    InputProductRepository inputProductRepository;

    @PostMapping
    public Result add(@RequestBody InputProductDto inputProductDto) {
        return inputProductService.add(inputProductDto);
    }

    @GetMapping
    public List<InputProduct> getAll() {
        return inputProductRepository.findAll();
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody InputProductDto inputProductDto) {
        return inputProductService.edit(id, inputProductDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return inputProductService.delete(id);
    }
}
