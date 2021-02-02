package SSTT.Backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class File {

    private String file_name;
    private String file_type;
    private Long file_size;
}
