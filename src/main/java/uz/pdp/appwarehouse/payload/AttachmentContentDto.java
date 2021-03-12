package uz.pdp.appwarehouse.payload;

import lombok.Data;

@Data
public class AttachmentContentDto {
    private byte[] bytes;
    private Integer attachmentId;
}
