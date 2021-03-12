package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.payload.CategoryDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Result addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        if (categoryDto.getParentCategoryId() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optionalCategory.isPresent()) return new Result("Bunday ota kategoriya mavjud emas", false);
            category.setParentCategory(optionalCategory.get());
            category.setActive(categoryDto.getActive());
        }
        categoryRepository.save(category);
        return new Result("Kategoriya muvaffaqqiyatli saqlandi !", true);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public List<Category> getByParent(Integer id) {
        return categoryRepository.findAllByParentCategoryId(id);
    }

    public Result edit(Integer id, CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) return new Result("Bunday kategoriya topilmadi !", false, id);
        Category category = optionalCategory.get();

        Optional<Category> optionalCategory1 = categoryRepository.findById(categoryDto.getParentCategoryId());
        if (!optionalCategory1.isPresent()) return new Result("Bunday ota kategoriya topilmadi !", false, categoryDto.getParentCategoryId());
        Category parCategory = optionalCategory1.get();

        category.setName(category.getName());
        category.setParentCategory(parCategory);
        category.setActive(categoryDto.getActive());

        categoryRepository.save(category);
        return new Result("Kategoriya muvaffaqqiyatli tahrirlandi !", true, id);
    }

    public Result delete(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) return new Result("Bunday kategoriya topilmadi !", false, id);
        categoryRepository.deleteById(id);
        return new Result("Kategoriya muvaffaqqiyatli o'chirildi !", true);
    }
}
