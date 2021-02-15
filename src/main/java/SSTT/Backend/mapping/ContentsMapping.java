package SSTT.Backend.mapping;

import SSTT.Backend.domain.Category;
import SSTT.Backend.domain.Summary;
import SSTT.Backend.domain.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface ContentsMapping {
    Long getId();
    String getTitle();
    String getDesc();
    LocalDateTime getDate();
    String getOrigin();
    Category getCategory();
    List<Tag> getTagList();
    List<Summary> getSummaryList();

}
