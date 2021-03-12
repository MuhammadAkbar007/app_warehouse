package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.payload.WarehouseDto;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.Optional;

@Service
public class WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;

    public Result add(WarehouseDto warehouseDto) {
        if (warehouseRepository.existsByName(warehouseDto.getName()))
            return new Result("Bunday ombor mavjud !", false, warehouseDto.getName());
        Warehouse warehouse = new Warehouse();
        warehouse.setName(warehouseDto.getName());
        warehouse.setActive(warehouseDto.getActive());
        Warehouse saved = warehouseRepository.save(warehouse);
        return new Result("Ombor muvaffaqqiyatli qo'shildi !", true, saved);
    }

    public Result edit(Integer id, WarehouseDto warehouseDto) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (!optionalWarehouse.isPresent()) return new Result("Bunday ombor topilmadi !", false, id);
        Warehouse warehouse = optionalWarehouse.get();
        warehouse.setName(warehouseDto.getName());
        warehouse.setActive(warehouseDto.getActive());
        Warehouse save = warehouseRepository.save(warehouse);
        return new Result("Ombor muvaffaqqiyatli tahrirlandi !", true, save);
    }

    public Result delete(Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (!optionalWarehouse.isPresent()) return new Result("Bunday ombor topilmadi !", false, id);
        warehouseRepository.deleteById(id);
        return new Result("Ombor muvaffaqqiyatli o'chirildi !", true);
    }
}
