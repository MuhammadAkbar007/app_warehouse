package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.OutputProductRepository;
import uz.pdp.appwarehouse.service.OutputProductService;

import java.util.List;

@RestController
@RequestMapping("/outputProduct")
public class OutputProductController {
    @Autowired
    OutputProductService outputProductService;
    @Autowired
    OutputProductRepository outputProductRepository;

    @PostMapping
    public Result add(@PathVariable OutputProductDto outputProductDto) {
        return outputProductService.add(outputProductDto);
    }

    @GetMapping
    public List<OutputProduct> getAll() {
        return outputProductRepository.findAll();
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody OutputProductDto outputProductDto) {
        return outputProductService.edit(id, outputProductDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return outputProductService.delete(id);
    }
}
