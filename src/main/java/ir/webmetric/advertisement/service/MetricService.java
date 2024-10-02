package ir.webmetric.advertisement.service;

import ir.webmetric.advertisement.dto.EventRequest;
import ir.webmetric.advertisement.dto.MetricResponse;
import ir.webmetric.advertisement.dto.RecommendationResponse;
import ir.webmetric.advertisement.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(rollbackFor = Exception.class)
public class MetricService {

    private final EventMapper eventMapper;
    private final CalculateService calculateService;
    private final RecommendationService recommendationService;


    @SneakyThrows
    public List<MetricResponse> processMetrics(MultipartFile impressionsFile, MultipartFile clicksFile) {
        EventRequest eventRequest = eventMapper.convert(impressionsFile, clicksFile);
        eventRequest =  eventMapper.insertAll(eventRequest);

        return calculateService.calculateMetrics(eventRequest.getImpressions(),eventRequest.getClicks());

    }


    public List<RecommendationResponse> recommendation(MultipartFile impressionsFile, MultipartFile clicksFile) {
        EventRequest eventRequest = eventMapper.convert(impressionsFile, clicksFile);
        eventRequest =  eventMapper.insertAll(eventRequest);
        return recommendationService.recommendTopAdvertisers(eventRequest.getImpressions(),eventRequest.getClicks());
    }
}

