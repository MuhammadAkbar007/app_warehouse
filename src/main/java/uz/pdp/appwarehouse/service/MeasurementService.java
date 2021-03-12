package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.MeasurementDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    public Result addMeasurementService(MeasurementDto measurement) {
        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName) return new Result("Bunday o'lchov birligi mavjud !", false);
        Measurement measurement1 = new Measurement();
        measurement1.setName(measurement.getName());
        measurement1.setActive(measurement.getActive());
        measurementRepository.save(measurement1);
        return new Result("Measurement muvaffaqqiyatli qo'shildi !", true);
    }

    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }

    public Measurement get(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        return optionalMeasurement.orElseGet(Measurement::new);
    }

    public Result edit(Integer id, MeasurementDto measurement) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (!optionalMeasurement.isPresent()) return new Result("Bunday o'lchov birligi topilmadi !", false, id);
        Measurement measurement1 = optionalMeasurement.get();
        measurement1.setName(measurement.getName());
        measurement1.setActive(measurement.getActive());
        measurementRepository.save(measurement1);
        return new Result("Measurement muvaffaqqiyatli tahrirlandi !", true, id);
    }

    public Result delete(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (!optionalMeasurement.isPresent()) return new Result("Bunday o'lchov birligi topilmadi !", false, id);
        measurementRepository.deleteById(id);
        return new Result("Measurement muvaffaqqiyatli o'chirildi !", true);
    }
}
