package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.service.OutputService;

import java.util.List;

@RestController
@RequestMapping("output")
public class OutputController {
    @Autowired
    OutputService outputService;
    @Autowired
    OutputRepository outputRepository;

    @PostMapping
    public Result add(@RequestBody OutputDto outputDto) {
        return outputService.add(outputDto);
    }

    @GetMapping
    public List<Output> getAll() {
        return outputRepository.findAll();
    }

    @GetMapping("/{facture}")
    public Output getByFacture(@PathVariable String facture) {
        return outputRepository.findByFactureNumber(facture);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody OutputDto outputDto) {
        return outputService.edit(id, outputDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return outputService.delete(id);
    }
}
