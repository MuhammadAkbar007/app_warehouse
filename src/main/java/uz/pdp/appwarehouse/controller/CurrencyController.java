package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.CurrencyDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.service.CurrencyServise;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    CurrencyServise currencyServise;
    @Autowired
    CurrencyRepository currencyRepository;

    @PostMapping
    public Result add(@RequestBody CurrencyDto currencyDto) {
        return currencyServise.add(currencyDto);
    }

    @GetMapping
    public List<Currency> get() {
        return currencyRepository.findAll();
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody CurrencyDto currencyDto) {
        return currencyServise.edit(id, currencyDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return currencyServise.delete(id);
    }
}
