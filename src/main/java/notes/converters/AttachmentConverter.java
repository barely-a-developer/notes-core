package notes.converters;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import notes.dto.AttachmentGetDto;
import notes.dto.AttachmentSaveDto;
import notes.enums.AttachmentType;
import notes.models.Attachment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AttachmentConverter {

    public List<Attachment> convertDtoToEntity(Map<AttachmentType, List<AttachmentSaveDto>> attachmentMap) {
        List<Attachment> attachmentList = new ArrayList<>();

        for (val attachmentListByType : attachmentMap.entrySet()) {
            @NonNull AttachmentType attachmentType = attachmentListByType.getKey();
            @NonNull List<AttachmentSaveDto> attachmentSaveDtoList = attachmentListByType.getValue();

            for (int i = 0; i < attachmentSaveDtoList.size(); i++) {
                @NonNull AttachmentSaveDto attachmentSaveDto = attachmentSaveDtoList.get(i);
                Attachment attachment = new Attachment();
                attachment.setFileId(attachmentSaveDto.getFileId());
                attachment.setType(attachmentType);
                attachment.setTypeIndex(i);
                attachmentList.add(attachment);
            }

        }
        return attachmentList;
    }

    public Map<AttachmentType, List<AttachmentGetDto>> convertEntityToDto(List<Attachment> attachmentList) {

        Map<AttachmentType, List<AttachmentGetDto>> attachmentTypeSetMap = new HashMap<>();
        Map<Long, AttachmentGetDto> attachmentDtoForFileIdMap = new HashMap<>();

        for (Attachment attachment : attachmentList) {
            AttachmentType attachmentType = attachment.getType();
            AttachmentGetDto attachmentGetDto = convertEntityToDto(attachment);
            List<AttachmentGetDto> attachmentGetDtoList;

            if ((attachmentGetDtoList = attachmentTypeSetMap.get(attachmentType)) != null) {
                attachmentGetDtoList.add(attachmentGetDto);
            } else {
                attachmentGetDtoList = new ArrayList<>();
                attachmentGetDtoList.add(attachmentGetDto);
                attachmentTypeSetMap.put(attachmentType, attachmentGetDtoList);
            }

            attachmentDtoForFileIdMap.put(attachment.getFileId(), attachmentGetDto);
        }
        // TODO Get FileDto from file service for each attachment, in batch

        return attachmentTypeSetMap;
    }

    public AttachmentGetDto convertEntityToDto(Attachment attachment) {
        return AttachmentGetDto.builder()
                        .id(attachment.getId())
                        .typeIndex(attachment.getTypeIndex())
                        .fileId(attachment.getFileId())
                        .build();
    }

}
