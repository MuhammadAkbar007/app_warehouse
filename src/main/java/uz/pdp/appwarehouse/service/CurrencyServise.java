package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.CurrencyDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;

import java.util.Optional;

@Service
public class CurrencyServise {
    @Autowired
    CurrencyRepository currencyRepository;

    public Result add(CurrencyDto currencyDto) {
        if (currencyRepository.existsByName(currencyDto.getName())) return new Result("Bunday pul birligi mavjud !", false);
        Currency currency1 = new Currency();
        currency1.setName(currencyDto.getName());
        currency1.setActive(currencyDto.getActive());
        currencyRepository.save(currency1);
        return new Result("Pul birligi muvaffaqqiyatli qo'shildi !", true);
    }

    public Result edit(Integer id, CurrencyDto currency) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent()) return new Result("Bunday pul birligi topilmadi !", false, id);
        Currency currencyNew = optionalCurrency.get();
        currencyNew.setName(currency.getName());
        currencyNew.setActive(currency.getActive());
        currencyRepository.save(currencyNew);
        return new Result("Pul birligi muvaffaqqiyatli tahrirlandi !", true, id);
    }

    public Result delete(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent()) return new Result("Bunday pul birligi topilmadi !", false, id);
        currencyRepository.deleteById(id);
        return new Result("Pul birligi muvaffaqqiyatli o'chirildi !", true);
    }
}
