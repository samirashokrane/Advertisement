package ir.webmetric.advertisement.service;

import ir.webmetric.advertisement.dto.DimensionKeyDto;
import ir.webmetric.advertisement.dto.RecommendationResponse;
import ir.webmetric.advertisement.model.Click;
import ir.webmetric.advertisement.model.Impression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(rollbackFor = Exception.class)
public class RecommendationService {

    /**
     * Group clicks by impressionId for easy lookup
     * Group impressions by appId and countryCode and group by advertiserId for revenue calculations
     * Process each appId and countryCode group
     *  For each advertiser, calculate total impressions and total revenue
     *  Get the top 5 advertisers by revenue per impression
     *
     * @param impressions
     * @param clicks
     * @return
     */

    public List<RecommendationResponse> recommendTopAdvertisers(List<Impression> impressions, List<Click> clicks) {

        Map<String, List<Click>> clicksGroupedByImpression = clicks.stream()
                .collect(Collectors.groupingBy(Click::getImpressionId));


        Map<DimensionKeyDto, Map<Integer, List<Impression>>> impressionsGroupedByAdvertiser = impressions.stream()
                .collect(Collectors.groupingBy(
                        impression -> new DimensionKeyDto(impression.getAppId(),
                                Optional.ofNullable(impression.getCountryCode()).orElse("UNKNOWN")),
                        Collectors.groupingBy(Impression::getAdvertiserId)));


        return impressionsGroupedByAdvertiser.entrySet().stream()
                .map(entry -> {
                    DimensionKeyDto key = entry.getKey();
                    Map<Integer, List<Impression>> advertiserGroup = entry.getValue();

                    // For each advertiser, calculate total impressions and total revenue
                    Map<Integer, Double> revenuePerImpressionMap = advertiserGroup.entrySet().stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey, // advertiserId
                                    advertiserEntry -> {
                                        List<Impression> advertiserImpressions = advertiserEntry.getValue();
                                        int impressionCount = advertiserImpressions.size();
                                        double totalRevenue = advertiserImpressions.stream()
                                                .mapToDouble(impression -> clicksGroupedByImpression
                                                        .getOrDefault(impression.getId(), List.of()).stream()
                                                        .mapToDouble(Click::getRevenue).sum())
                                                .sum();
                                        return totalRevenue / impressionCount;
                                    }
                            ));


                    List<Integer> topAdvertisers = revenuePerImpressionMap.entrySet().stream()
                            .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                            .limit(5)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());

                    return RecommendationResponse.builder()
                            .appId(key.getAppId())
                            .countryCode(key.getCountryCode())
                            .recommendedAdvertiserIds(topAdvertisers)
                            .build();
                })
                .collect(Collectors.toList());
    }

}