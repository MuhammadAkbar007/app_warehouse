package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.MeasurementDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping
    public Result addMeasurementController(@RequestBody MeasurementDto measurement) {
        return measurementService.addMeasurementService(measurement);
    }

    @GetMapping
    public List<Measurement> getAll() {
        return measurementService.getAll();
    }

    @GetMapping("/{id}")
    public Measurement get(@PathVariable Integer id) {
        return measurementService.get(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody MeasurementDto measurement) {
        return measurementService.edit(id, measurement);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return measurementService.delete(id);
    }
}
