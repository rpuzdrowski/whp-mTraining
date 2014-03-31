package org.motechproject.whp.mtraining.domain;

import org.joda.time.DateTime;
import org.motechproject.mtraining.dto.ChapterDto;
import org.motechproject.mtraining.dto.ModuleDto;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PersistenceCapable(table = "module", identityType = IdentityType.APPLICATION)
public class Module extends CourseContent {


    @Element(column = "module_id")
    @Order(column = "chapter_order")
    @Persistent(dependentElement = "true")
    private List<Chapter> chapters = new ArrayList<>();

    @Persistent
    private String description;


    public Module(String name, UUID contentId, Integer version, String description, String createdBy, DateTime createdOn, List<Chapter> chapters, boolean isActive) {
        super(name, contentId, version, createdBy, createdOn, isActive);
        this.chapters = chapters;
        this.description = description;
    }

    public Module(ModuleDto moduleDto) {
        this(moduleDto.getName(), moduleDto.getContentId(), moduleDto.getVersion(), moduleDto.getDescription(), moduleDto.getCreatedBy(), moduleDto.getCreatedOn(),
                mapToChapters(moduleDto.getChapters()), moduleDto.isActive());
    }

    private static List<Chapter> mapToChapters(List<ChapterDto> chapterDtoList) {
        ArrayList<Chapter> chapters = new ArrayList<>();
        for (ChapterDto chapterDto : chapterDtoList) {
            chapters.add(new Chapter(chapterDto));
        }
        return chapters;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }


}
