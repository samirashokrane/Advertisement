package ir.webmetric.advertisement.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@Data
@ToString
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class DimensionKeyDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

     Integer appId;
     String countryCode;

}
