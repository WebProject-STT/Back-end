package SSTT.Backend.mapping;

import SSTT.Backend.domain.Category;

import java.time.LocalDateTime;

public interface ContentsListMapping {
    Long getId();
    String getTitle();
    LocalDateTime getDate();
    Category getCategory();
}
