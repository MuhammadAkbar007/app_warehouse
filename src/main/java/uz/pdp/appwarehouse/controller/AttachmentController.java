package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentRepository;
import uz.pdp.appwarehouse.service.AttachmentService;

import java.util.List;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    AttachmentRepository attachmentRepository;

    @PostMapping("/upload")
    public Result upload(MultipartHttpServletRequest request) {
        return attachmentService.uploadFile(request);
    }

    @GetMapping
    public List<Attachment> get() {
        return attachmentRepository.findAll();
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, MultipartHttpServletRequest request) {
        return attachmentService.edit(id, request);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return attachmentService.delete(id);
    }
}
