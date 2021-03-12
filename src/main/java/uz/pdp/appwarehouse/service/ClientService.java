package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.payload.ClientDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public Result add(ClientDto clientDto) {
        if (clientRepository.existsByPhoneNumber(clientDto.getPhoneNumber()))
            return new Result("Bunday mijoz mavjud !", false, clientDto.getPhoneNumber());
        Client client = new Client();
        client.setActive(clientDto.getActive());
        client.setName(clientDto.getName());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        clientRepository.save(client);
        return new Result("Mijoz muvaffaqqiyatli qo'shildi !", true, clientDto.getName());
    }

    public Result edit(Integer id, ClientDto clientDto) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) return new Result("Bunday mijoz topilmadi !", false, id);
        Client client = optionalClient.get();
        client.setName(client.getName());
        client.setActive(clientDto.getActive());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        clientRepository.save(client);
        return new Result("Mijoz muvaffaqqiyatli tahrirlandi !", true, clientDto.getName());
    }

    public Result delete(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) return new Result("Bunday mijoz topilmadi !", false, id);
        clientRepository.deleteById(id);
        return new Result("Mijoz muvaffaqqiyatli o'chirildi !", true);
    }
}
