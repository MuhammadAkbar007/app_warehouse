package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.AttachmentContent;
import uz.pdp.appwarehouse.payload.AttachmentContentDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentContentRepository;
import uz.pdp.appwarehouse.service.AttachmentContentService;

import java.util.List;

@RestController
@RequestMapping("/attachmentContent")
public class AttachmentContentController {
    @Autowired
    AttachmentContentService attachmentContentService;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @PostMapping
    public Result add(@RequestBody AttachmentContentDto attachmentContentDto) {
        return attachmentContentService.add(attachmentContentDto);
    }

    @GetMapping
    public List<AttachmentContent> getAll() {
        return attachmentContentRepository.findAll();
    }

    @GetMapping("/{attachId}")
    public AttachmentContent getByAttachId(@PathVariable Integer attachId) {
        return attachmentContentRepository.findByAttachmentId(attachId);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody AttachmentContentDto attachmentContentDto) {
        return attachmentContentService.edit(id, attachmentContentDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return attachmentContentService.delete(id);
    }
}
