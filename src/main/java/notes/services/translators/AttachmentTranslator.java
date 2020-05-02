package notes.services.translators;

import notes.models.Attachment;
import notes.models.Photo;
import notes.models.dto.AttachmentDto;
import notes.models.dto.PhotoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AttachmentTranslator {

    private static String fileStorageUrl;

    public AttachmentTranslator(@Value("${services.notes.filestorage.url}") String fileStorageUrl) {
        AttachmentTranslator.fileStorageUrl = fileStorageUrl;
    }

    public static Attachment newAttachmentFromAttachmentDto(AttachmentDto attachmentDto) {
        Attachment attachment;
        if (attachmentDto instanceof PhotoDto) {
            attachment = new Photo();
            enrichPhotoWithPhotoDto((Photo) attachment, (PhotoDto) attachmentDto);
        } else {
            attachment = new Attachment();
            enrichAttachmentWithAttachmentDto(attachment, attachmentDto);
        }
        return attachment;
    }

    public static void enrichAttachmentWithAttachmentDto(Attachment attachment, AttachmentDto attachmentDto) {
        attachment.setOriginalUrl(attachmentDto.getOriginalUrl());
        attachment.setStorageId(attachmentDto.getStorageId());
    }

    public static void enrichPhotoWithPhotoDto(Photo photo, PhotoDto photoDto) {
        photo.setWidth(photoDto.getWidth());
        photo.setHeight(photoDto.getHeight());

        enrichAttachmentWithAttachmentDto(photo, photoDto);
    }

    public static AttachmentDto translateAttachmentToAttachmentDto(Attachment attachment) {
        if (attachment instanceof Photo) {
            Photo photo = (Photo) attachment;
            PhotoDto photoDto = new PhotoDto();
            translatePhotoToDto(photo, photoDto);
            return photoDto;
        }
        throw new RuntimeException("Unknown type of attachment");
    }

    private static void translatePhotoToDto(Photo photo, PhotoDto photoDto) {
        photoDto.setHeight(photo.getHeight());
        photoDto.setWidth(photo.getWidth());

        translateAttachmentToDto(photo, photoDto);
    }

    private static void translateAttachmentToDto(Attachment attachment, AttachmentDto attachmentDto) {
        attachmentDto.setOriginalUrl(attachment.getOriginalUrl());
        attachmentDto.setStorageId(attachment.getStorageId());

        String id = attachment.getStorageId();
        attachmentDto.setStorageUrl(fileStorageUrl + id);
    }

}
