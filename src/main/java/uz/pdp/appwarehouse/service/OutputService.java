package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OutputService {
    @Autowired
    OutputRepository outputRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ClientRepository clientRepository;

    public String codeGeneration() {
        int gen = 1;
        List<Output> outputList = outputRepository.findAll();
        for (Output output : outputList) {
            if (Integer.parseInt(output.getCode()) > gen) {
                gen = Integer.parseInt(output.getCode());
            }
        }
        return String.valueOf(gen + 1);
    }

    public Result add(OutputDto outputDto) {
        if (outputRepository.existsByFactureNumberAndCode(outputDto.getFactureNumber(), outputDto.getCode()))
            return new Result("Bunday chiqim mavjud !", false, outputDto.getFactureNumber());
        Output output = new Output();
        output.setCode(codeGeneration());
        output.setDate(outputDto.getDate());
        output.setFactureNumber(outputDto.getFactureNumber());
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Bunday pul birligi topilmadi !", false, outputDto.getCurrencyId());
        output.setCurrency(optionalCurrency.get());
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor topilmadi !", false, outputDto.getWarehouseId());
        output.setWarehouse(optionalWarehouse.get());
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent()) return new Result("Bunday mijoz topilmadi !", false, outputDto.getClientId());
        output.setClient(optionalClient.get());
        Output save = outputRepository.save(output);
        return new Result("Chiqim muvaffaqqiyatli saqlandi !", true, save);
    }

    public Result edit(Integer id, OutputDto outputDto) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent()) return new Result("Bunday chiqim topilmadi !", false, id);
        Output output = optionalOutput.get();
        output.setDate(outputDto.getDate());
        output.setFactureNumber(outputDto.getFactureNumber());
        output.setCode(codeGeneration());
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent()) return new Result("Bunday mijoz topilmadi !", false, outputDto.getClientId());
        output.setClient(optionalClient.get());
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Bunday pul birligi topilmadi !", false, outputDto.getCurrencyId());
        output.setCurrency(optionalCurrency.get());
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor topilmadi !", false, outputDto.getWarehouseId());
        output.setWarehouse(optionalWarehouse.get());
        Output save = outputRepository.save(output);
        return new Result("Chiqim muvaffaqqiyatli tahrirlandi !", true, save);
    }

    public Result delete(Integer id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent()) return new Result("Bunday chiqim topilmadi !", false, id);
        outputRepository.deleteById(id);
        return new Result("Chiqim muvaffaqqiyatli o'chirildi !", true);
    }
}
