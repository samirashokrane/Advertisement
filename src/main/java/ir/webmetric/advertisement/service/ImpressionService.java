package ir.webmetric.advertisement.service;

import ir.webmetric.advertisement.model.Impression;
import ir.webmetric.advertisement.repository.ImpressionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(rollbackFor = Exception.class)
public class ImpressionService {

    private final ImpressionRepository impressionRepository;

    /**
     * Insert an impression event
     * @param impressions
     * @return
     */
    public List<Impression> insertImpressions(List<Impression> impressions) {
        impressions.forEach(impression -> {
            // Assign a UUID if the id is null or empty
            if (StringUtils.isEmpty(impression.getId())) {
                impression.setId(UUID.randomUUID().toString());
            }
            // No action needed if countryCode is null or empty (allowed case)
        });

        // Log the batch size for debug purposes
        log.info("Inserting {} impressions into the database", impressions.size());

        // Use batch save to improve performance for large data sets
        return impressionRepository.saveAllAndFlush(impressions);
    }

}

