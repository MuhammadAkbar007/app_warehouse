package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.payload.SupplierDto;
import uz.pdp.appwarehouse.repository.SupplierRepository;

import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public Result add(SupplierDto supplierDto) {
        if (supplierRepository.existsByPhoneNumberAndName(supplierDto.getPhoneNumber(), supplierDto.getName()))
            return new Result("Bunday yetkazib beruvchi mavjud !", false, supplierDto.getName());
        Supplier supplier = new Supplier();
        supplier.setPhoneNumber(supplierDto.getPhoneNumber());
        supplier.setName(supplierDto.getName());
        supplier.setActive(supplierDto.getActive());
        supplierRepository.save(supplier);
        return new Result("Yetkazib beruvchi muvaffaqqiyatli qo'shildi !", true, supplierDto.getName());
    }

    public Result edit(Integer id, SupplierDto supplierDto) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (!optionalSupplier.isPresent()) return new Result("Bunday ta'minotchi topilmadi !", false, id);
        Supplier supplier = optionalSupplier.get();
        supplier.setActive(supplierDto.getActive());
        supplier.setName(supplierDto.getName());
        supplier.setPhoneNumber(supplierDto.getPhoneNumber());
        supplierRepository.save(supplier);
        return new Result("Ta'minotchi muvaffaqqiyatli tahrirlandi !", true, id);
    }

    public Result delete(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (!optionalSupplier.isPresent()) return new Result("Bunday ta'minotchi topilmadi !", false, id);
        supplierRepository.deleteById(id);
        return new Result("Ta'minotchi muvaffaqqiyatli o'chirildi !", true);
    }
}
