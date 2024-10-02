package ir.webmetric.advertisement.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;


/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@Entity
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
@ToString
@Table(name = "ad_impression", indexes = {
        @Index(unique = false, name = "idx_advertiser_id", columnList = "advertiser_id")
})
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Impression implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id ;

    @JsonProperty("app_id")
    @Column(name = "app_id")
    private Integer appId;

    @Column(name = "advertiser_id")
    @JsonProperty("advertiser_id")
    private Integer advertiserId;

    @JsonProperty("country_code")
    @Column(name = "country_code",  length = 10)
    private String countryCode;
}
