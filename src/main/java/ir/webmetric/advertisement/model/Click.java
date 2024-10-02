package ir.webmetric.advertisement.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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
@Table(name = "ad_click", indexes = {
        @Index(unique = false, name = "idx_impression_id", columnList = "impression_id")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Click implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty("impression_id")
    @Column(name = "impression_id", nullable = false, length = 36)
    private String impressionId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "impression_id", referencedColumnName = "id", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "FK_CLICK_IMPRESSION"))
    private Impression impression;

    @JsonProperty("revenue")
    @Column(name = "revenue", nullable = false)
    private Double revenue;

}
