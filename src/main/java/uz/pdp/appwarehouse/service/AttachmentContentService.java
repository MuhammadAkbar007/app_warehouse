package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.AttachmentContent;
import uz.pdp.appwarehouse.payload.AttachmentContentDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentContentRepository;
import uz.pdp.appwarehouse.repository.AttachmentRepository;

import java.util.Optional;

@Service
public class AttachmentContentService {
    @Autowired
    AttachmentContentRepository attachmentContentRepository;
    @Autowired
    AttachmentRepository attachmentRepository;

    public Result add(AttachmentContentDto attachmentContentDto) {
        if (attachmentContentRepository.existsByAttachmentId(attachmentContentDto.getAttachmentId()))
            return new Result("Bunday yuklanma mavjud !", false);
        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setBytes(attachmentContentDto.getBytes());
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentContentDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new Result("Bunday yuklanma topilmadi !", false, attachmentContentDto.getAttachmentId());
        attachmentContent.setAttachment(optionalAttachment.get());
        AttachmentContent save = attachmentContentRepository.save(attachmentContent);
        return new Result("Yuklanma muvaffaqqiyatli saqlandi !", true, save);
    }

    public Result edit(Integer id, AttachmentContentDto attachmentContentDto) {
        Optional<AttachmentContent> contentOptional = attachmentContentRepository.findById(id);
        if (!contentOptional.isPresent()) return new Result("Bunday yuklanma topilmadi !", false, id);
        AttachmentContent attachmentContent = contentOptional.get();
        attachmentContent.setBytes(attachmentContentDto.getBytes());
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentContentDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new Result("Bunday yuklanma topilmadi !", false, attachmentContentDto.getAttachmentId());
        attachmentContent.setAttachment(optionalAttachment.get());
        AttachmentContent save = attachmentContentRepository.save(attachmentContent);
        return new Result("Yuklanma muvaffaqqiyatli tahrirlandi !", true, save);
    }

    public Result delete(Integer id) {
        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findById(id);
        if (!optionalAttachmentContent.isPresent()) return new Result("Bunday yuklanma topilmadi !", false, id);
        attachmentContentRepository.deleteById(id);
        return new Result("Yuklanma muvaffaqqiyatli o'chirildi !", true);
    }
}
