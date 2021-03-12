package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentRepository;
import uz.pdp.appwarehouse.repository.CategoryRepository;
import uz.pdp.appwarehouse.repository.MeasurementRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    MeasurementRepository measurementRepository;

    public String codeGeneration() {
        int gen = 1;
        List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            if (Integer.parseInt(product.getCode()) > gen) {
                gen = Integer.parseInt(product.getCode());
            }
        }
        return String.valueOf(gen + 1);
    }

    public Result addProduct(ProductDto productDto) {
        boolean exists = productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (exists) return new Result("Bunday mahsulot kategoriyada mavjud !", false);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) return new Result("Bunday kategoriya mavjud emas !", false);
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent()) return new Result("Bunday rasm mavjud emas !", false);
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent()) return new Result("Bunday o'lchov birligi mavjud emas !", false);
        Product product = new Product();
        product.setName(productDto.getName());
        product.setActive(productDto.getActive());
        product.setCode(codeGeneration());
        product.setCategory(optionalCategory.get());
        product.setPhoto(optionalAttachment.get());
        product.setMeasurement(optionalMeasurement.get());
        productRepository.save(product);
        return new Result("Mahsulot muvaffaqqiyatli saqlandi !", true);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getByCategory(Integer id) {
        return productRepository.findAllByCategoryId(id);
    }

    public Result edit(Integer id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) return new Result("Bunday mahsulot topilmadi !", false, id);
        Product product = optionalProduct.get();
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("Bunday kategoriya topilmadi !", false, productDto.getCategoryId());
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent()) return new Result("Bunday o'lchov birligi topilmadi !", false, productDto.getMeasurementId());
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent()) return new Result("Bunday attachment topilmadi !", false, productDto.getPhotoId());
        product.setPhoto(optionalAttachment.get());
        product.setMeasurement(optionalMeasurement.get());
        product.setCategory(optionalCategory.get());
        product.setName(productDto.getName());
        product.setActive(productDto.getActive());
        productRepository.save(product);
        return new Result("Mahsulot muvaffaqqiyatli tahrirlandi !", true, id);
    }

    public Result delete(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) return new Result("Bunday mahsulot topilmadi !", false, id);
        productRepository.deleteById(id);
        return new Result("Mahsulot muvaffaqqiyatli o'chirildi !", true);
    }
}
