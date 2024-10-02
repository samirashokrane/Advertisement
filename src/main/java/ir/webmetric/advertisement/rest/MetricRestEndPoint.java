package ir.webmetric.advertisement.rest;


import ir.webmetric.advertisement.dto.EventRequest;
import ir.webmetric.advertisement.dto.MetricResponse;
import ir.webmetric.advertisement.dto.RecommendationResponse;
import ir.webmetric.advertisement.mapper.EventMapper;
import ir.webmetric.advertisement.service.MetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@RestController
@RequestMapping("/api/events/")
@RequiredArgsConstructor
public class MetricRestEndPoint implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    private  final MetricService metricService;
    private  final EventMapper eventMapper;


    /**
     * Endpoint to process events and calculate metrics
     * @param impressionsFile
     * @param clicksFile
     * @return
     */
    @PostMapping("/process/v1")
    public ResponseEntity<?> process(@RequestParam("impressions") MultipartFile impressionsFile,
                                     @RequestParam("clicks") MultipartFile clicksFile) {
        List<MetricResponse> metrics = metricService.processMetrics(impressionsFile ,clicksFile);
        return ResponseEntity.ok(metrics);
    }


    /**
     * Endpoint to get top 5 advertisers
     * @param impressionsFile
     * @param clicksFile
     * @return
     */
    @PostMapping("/recommendations/v1")
    public ResponseEntity<?> recommendation(@RequestParam("impressions") MultipartFile impressionsFile,
                                            @RequestParam("clicks") MultipartFile clicksFile) {

        List<RecommendationResponse> recommendations = metricService.recommendation(impressionsFile ,clicksFile);
        return ResponseEntity.ok(recommendations);
    }

}

