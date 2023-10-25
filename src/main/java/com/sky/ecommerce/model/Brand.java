package com.sky.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "brands")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Brand {
    @Id
    private String id;
    private String name;
    private String imageUrl;
    private String description;
}
