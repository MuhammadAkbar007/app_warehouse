package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appwarehouse.entity.Supplier;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    boolean existsByPhoneNumberAndName(String phoneNumber, String name);
    List<Supplier> findAllByName(String name);
    Supplier findByPhoneNumber(String phoneNumber);
}
