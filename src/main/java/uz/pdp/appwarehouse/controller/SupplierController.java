package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.payload.SupplierDto;
import uz.pdp.appwarehouse.repository.SupplierRepository;
import uz.pdp.appwarehouse.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    SupplierService supplierService;
    @Autowired
    SupplierRepository supplierRepository;

    @PostMapping
    public Result add(@RequestBody SupplierDto supplierDto) {
        return supplierService.add(supplierDto);
    }

    @GetMapping
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @GetMapping("/{name}")
    public List<Supplier> getByName(@PathVariable String name) {
        return supplierRepository.findAllByName(name);
    }

    @GetMapping("/{phone}")
    public Supplier getByPhone(@PathVariable String phone) {
        return supplierRepository.findByPhoneNumber(phone);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody SupplierDto supplierDto) {
        return supplierService.edit(id, supplierDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return supplierService.delete(id);
    }
}
