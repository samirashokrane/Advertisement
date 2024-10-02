package ir.webmetric.advertisement.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@Data
@ToString
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RecommendationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    Integer appId;
     String countryCode;
     List<Integer> recommendedAdvertiserIds;
}
