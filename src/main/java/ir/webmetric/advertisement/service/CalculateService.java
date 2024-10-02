package ir.webmetric.advertisement.service;

import ir.webmetric.advertisement.dto.DimensionKeyDto;
import ir.webmetric.advertisement.dto.MetricResponse;
import ir.webmetric.advertisement.model.Click;
import ir.webmetric.advertisement.model.Impression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(rollbackFor = Exception.class)
public class CalculateService {

    /**
     * Calculate metrics
     * Manually group clicks by impressionId for quick lookup
     * Manually group impressions by appId and countryCode
     * Calculate metrics for each appId and countryCode group
     * Calculate click count and total revenue for each impression in the group
     * Create and add the MetricResponse
     *
     * @param impressions
     * @param clicks
     * @return
     */

    public List<MetricResponse> calculateMetrics(List<Impression> impressions, List<Click> clicks) {
        log.info("start calculateMetrics....");
        Map<String, List<Click>> clicksGroupedByImpression = new HashMap<>();
        for (Click click : clicks) {
            clicksGroupedByImpression
                    .computeIfAbsent(click.getImpressionId(), k -> new ArrayList<>())
                    .add(click);
        }


        Map<DimensionKeyDto, List<Impression>> impressionsGrouped = new HashMap<>();
        for (Impression impression : impressions) {
            DimensionKeyDto key = new DimensionKeyDto(impression.getAppId(),
                    Optional.ofNullable(impression.getCountryCode()).orElse("UNKNOWN"));

            impressionsGrouped
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(impression);
        }


        List<MetricResponse> metricResponses = new ArrayList<>();
        for (Map.Entry<DimensionKeyDto, List<Impression>> entry : impressionsGrouped.entrySet()) {
            DimensionKeyDto key = entry.getKey();
            List<Impression> impressionGroup = entry.getValue();

            int clickCount = 0;
            double totalRevenue = 0;


            for (Impression impression : impressionGroup) {
                List<Click> relatedClicks = clicksGroupedByImpression.get(impression.getId());
                if (relatedClicks != null) {
                    clickCount += relatedClicks.size();
                    totalRevenue += relatedClicks.stream().mapToDouble(Click::getRevenue).sum();
                }
            }

            MetricResponse metricResponse = MetricResponse.builder()
                    .appId(key.getAppId())
                    .countryCode(key.getCountryCode())
                    .impressions(impressionGroup.size())
                    .clicks(clickCount)
                    .revenue(totalRevenue)
                    .build();

            metricResponses.add(metricResponse);
        }

        return metricResponses;
    }


}