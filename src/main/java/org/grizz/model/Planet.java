package org.grizz.model;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "planets")
public class Planet {

    private String id;

}
