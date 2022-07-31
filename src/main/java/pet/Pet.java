package pet;

import lombok.Builder;
import lombok.Data;
import pet.Category;
import pet.Tag;

import java.util.ArrayList;

@Data
@Builder

public class Pet {
    private long id;
    private Category category;
    private String name;
    @Builder.Default
    private ArrayList<String> photoUrls = new ArrayList<>();
    @Builder.Default
    private ArrayList<Tag> tags = new ArrayList<>();
    private String petStatus;

}
