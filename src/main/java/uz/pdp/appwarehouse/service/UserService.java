package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.entity.User;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.payload.UserDto;
import uz.pdp.appwarehouse.repository.UserRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public String codeGeneration() {
        int gen = 1;
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            if (Integer.parseInt(user.getCode()) > gen) {
                gen = Integer.parseInt(user.getCode());
            }
        }
        return String.valueOf(gen + 1);
    }

    public Result add(UserDto userDto) {
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber()))
            return new Result("Bunday ishchi mavjud !", false, userDto.getFirstName());
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setActive(userDto.getActive());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCode(codeGeneration());

        Set<Warehouse> warehouseSet = new HashSet<>();
        for (Integer id : userDto.getWarehouseIds()) {
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
            if (!optionalWarehouse.isPresent()) return new Result("Bunday ombor topilmadi !", false, id);
            warehouseSet.add(optionalWarehouse.get());
        }
        user.setWarehouses(warehouseSet);
        User save = userRepository.save(user);
        return new Result("Ishchi muvaffaqqiyatli qo'shildi !", true, save);
    }

    public Result edit(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) return new Result("Bunday ishchi topilmadi !", false, id);
        User user = optionalUser.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        user.setActive(userDto.getActive());
        user.setCode(codeGeneration());
        Set<Warehouse> warehouseSet = new HashSet<>();
        for (Integer warehouseId : userDto.getWarehouseIds()) {
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(warehouseId);
            if (!optionalWarehouse.isPresent()) return new Result("Bunday ombor topilmadi !", false, warehouseId);
            warehouseSet.add(optionalWarehouse.get());
        }
        user.setWarehouses(warehouseSet);
        User save = userRepository.save(user);
        return new Result("Ishchi muvaffaqqiyatli tahrirlandi !", true, save);
    }

    public Result delete(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) return new Result("Bunday ishchi topilmadi !", false, id);
        userRepository.deleteById(id);
        return new Result("Ishchi muvaffaqqiyatli o'chirildi !", true);
    }
}
