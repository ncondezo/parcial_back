package com.dh.catalog.model.serie;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Series")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Serie {

    @Id
    private String id;
    private String name;
    private String genre;
    private List<Season> seasons = new ArrayList<>();
}
