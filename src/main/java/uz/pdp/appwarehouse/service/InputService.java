package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.SupplierRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InputService {
    @Autowired
    InputRepository inputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    public String codeGeneration() {
        int gen = 1;
        List<Input> inputList = inputRepository.findAll();
        for (Input input : inputList) {
            if (Integer.parseInt(input.getCode()) > gen) {
                gen = Integer.parseInt(input.getCode());
            }
        }
        return String.valueOf(gen + 1);
    }

    public Result add(InputDto inputDto) {
        if (inputRepository.existsByFactureNumberAndCode(inputDto.getFactureNumber(), inputDto.getCode()))
            return new Result("Bunday kirim mavjud !", false);
        Input input = new Input();
        input.setDate(inputDto.getDate());
        input.setFactureNumber(inputDto.getFactureNumber());
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor topilmadi !", false, inputDto.getWarehouseId());
        input.setWarehouse(optionalWarehouse.get());
        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent())
            return new Result("Bunday ta'minotchi topilmadi !", false, inputDto.getSupplierId());
        input.setSupplier(optionalSupplier.get());
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Bunday pul birligi topilmadi !", false, inputDto.getCurrencyId());
        input.setCurrency(optionalCurrency.get());
        input.setCode(codeGeneration());
        Input save = inputRepository.save(input);
        return new Result("Kirim muvaffaqqiyatli saqlandi !", true, save);
    }

    public Result edit(Integer id, InputDto inputDto) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent()) return new Result("Bunday kirim topilmadi !", false, id);
        Input input = optionalInput.get();
        input.setCode(codeGeneration());
        input.setDate(inputDto.getDate());
        input.setFactureNumber(inputDto.getFactureNumber());
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Bunday pul birligi topilmadi !", false, inputDto.getCurrencyId());
        input.setCurrency(optionalCurrency.get());
        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent())
            return new Result("Bunday ta'minotchi topilmadi !", false, inputDto.getSupplierId());
        input.setSupplier(optionalSupplier.get());
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor topilmadi !", false, inputDto.getWarehouseId());
        input.setWarehouse(optionalWarehouse.get());
        Input save = inputRepository.save(input);
        return new Result("Kirim muvaffaqqiyatli tahrirlandi !", true, save);
    }

    public Result delete(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent()) return new Result("Bunday kirim topilmadi !", false, id);
        inputRepository.deleteById(id);
        return new Result("Kirim muvaffaqqiyatli o'chirildi !", true);
    }
}
