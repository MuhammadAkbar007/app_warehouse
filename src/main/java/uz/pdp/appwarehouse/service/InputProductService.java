package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.InputProductRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.Optional;

@Service
public class InputProductService {
    @Autowired
    InputProductRepository inputProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    InputRepository inputRepository;

    public Result add(InputProductDto inputProductDto) {
        InputProduct inputProduct = new InputProduct();
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setExpireDate(inputProductDto.getExpireDate());
        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Bunday mahsulot topilmadi !", false, inputProductDto.getProductId());
        inputProduct.setProduct(optionalProduct.get());
        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return new Result("Bunday kirim topilmadi !", false, inputProductDto.getInputId());
        inputProduct.setInput(optionalInput.get());
        InputProduct save = inputProductRepository.save(inputProduct);
        return new Result("Kirim mahsuloti muvaffaqqiyatli saqlandi !", true, save);
    }

    public Result edit(Integer id, InputProductDto inputProductDto) {
        Optional<InputProduct> optional = inputProductRepository.findById(id);
        if (!optional.isPresent()) return new Result("Bunday kirim mahsuloti topilmadi !", false, id);
        InputProduct inputProduct = optional.get();
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setExpireDate(inputProductDto.getExpireDate());
        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Bunday mahsulot topilmadi !", false, inputProductDto.getProductId());
        inputProduct.setProduct(optionalProduct.get());
        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return new Result("Bunday kirim topilmadi !", false, inputProductDto.getInputId());
        inputProduct.setInput(optionalInput.get());
        InputProduct save = inputProductRepository.save(inputProduct);
        return new Result("Kirim mahsuloti muvaffaqqiyatli tahrirlandi !", true, save);
    }

    public Result delete(Integer id) {
        Optional<InputProduct> optional = inputProductRepository.findById(id);
        if (!optional.isPresent()) return new Result("Bunday kirim mahsuloti topilmadi !", false, id);
        inputProductRepository.deleteById(id);
        return new Result("Kirim mahsuloti muvaffaqqiyatli o'chirildi !", true);
    }
}
