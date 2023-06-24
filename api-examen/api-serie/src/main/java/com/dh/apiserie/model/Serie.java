package com.dh.apiserie.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
