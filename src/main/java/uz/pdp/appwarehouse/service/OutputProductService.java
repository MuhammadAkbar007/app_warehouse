package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.OutputProductRepository;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.Optional;

@Service
public class OutputProductService {
    @Autowired
    OutputProductRepository outputProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OutputRepository outputRepository;

    public Result add(OutputProductDto outputProductDto) {
        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Bunday mahsulot topilmadi !", false, outputProductDto.getProductId());
        outputProduct.setProduct(optionalProduct.get());
        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("Bunday chiqim topilmadi !", false, outputProductDto.getOutputId());
        outputProduct.setOutput(optionalOutput.get());
        OutputProduct save = outputProductRepository.save(outputProduct);
        return new Result("Chiqim mahsuloti muvaffaqqiyatli saqlandi !", true, save);
    }

    public Result edit(Integer id, OutputProductDto outputProductDto) {
        Optional<OutputProduct> optional = outputProductRepository.findById(id);
        if (!optional.isPresent()) return new Result("Bunday chiqim mahsuloti topilmadi !", false, id);
        OutputProduct outputProduct = optional.get();
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setAmount(outputProductDto.getAmount());
        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Bunday mahsulot topilmadi !", false, outputProductDto.getProductId());
        outputProduct.setProduct(optionalProduct.get());
        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("Bunday chiqim topilmadi !", false, outputProductDto.getOutputId());
        outputProduct.setOutput(optionalOutput.get());
        OutputProduct save = outputProductRepository.save(outputProduct);
        return new Result("Chiqim mahsuloti muvaffaqqiyatli tahrirlandi !", true, save);
    }

    public Result delete(Integer id) {
        Optional<OutputProduct> optional = outputProductRepository.findById(id);
        if (!optional.isPresent()) return new Result("Bunday chiqim mahsuloti topilmadi !", false, id);
        outputProductRepository.deleteById(id);
        return new Result("Chiqim mahsuloti muvaffaqqiyatli o'chirildi !", true);
    }
}
