package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.payload.WarehouseDto;
import uz.pdp.appwarehouse.repository.WarehouseRepository;
import uz.pdp.appwarehouse.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    WarehouseRepository warehouseRepository;

    @PostMapping
    public Result add(@RequestBody WarehouseDto warehouseDto) {
        return warehouseService.add(warehouseDto);
    }

    @GetMapping
    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }

    @GetMapping("/{name}")
    public Warehouse getByName(@PathVariable String name) {
        return warehouseRepository.findByName(name);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody WarehouseDto warehouseDto) {
        return warehouseService.edit(id, warehouseDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return warehouseService.delete(id);
    }
}
