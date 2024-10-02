package ir.webmetric.advertisement.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.webmetric.advertisement.dto.EventRequest;
import ir.webmetric.advertisement.model.Click;
import ir.webmetric.advertisement.model.Impression;
import ir.webmetric.advertisement.service.ClickService;
import ir.webmetric.advertisement.service.ImpressionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@RequiredArgsConstructor
@Service
public class EventMapper {

private final ImpressionService impressionService;
private final ClickService clickService;

    public EventRequest getRequest(final List<Impression> impressions, List<Click> clicks) {
        return EventRequest.builder()
                .impressions(impressions)
                .clicks(clicks)
                .build();

    }

    @SneakyThrows
    public EventRequest convert(final MultipartFile impressionsFile, MultipartFile clicksFile) {
        ObjectMapper mapper = new ObjectMapper();

        // Parse the JSON files into lists of Impression and Click
        List<Impression> impressions = mapper.readValue(impressionsFile.getInputStream(),
                mapper.getTypeFactory().constructCollectionType(List.class, Impression.class));


        List<Click> clicks = mapper.readValue(clicksFile.getInputStream(),
                mapper.getTypeFactory().constructCollectionType(List.class, Click.class));

        return EventRequest.builder()
                .impressions(impressions)
                .clicks(clicks)
                .build();

    }

    public EventRequest insertAll(EventRequest eventRequest) {
        List<Impression>  impressions = impressionService.insertImpressions(eventRequest.getImpressions());
        List<Click>  clicks =  clickService.insertClicks(eventRequest.getClicks());
       return EventRequest.builder().clicks(clicks).impressions(impressions).build();

    }
}