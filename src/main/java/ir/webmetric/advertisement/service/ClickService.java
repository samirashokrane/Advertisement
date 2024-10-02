package ir.webmetric.advertisement.service;

import ir.webmetric.advertisement.model.Click;
import ir.webmetric.advertisement.repository.ClickRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(rollbackFor = Exception.class)
public class ClickService {

    private final ClickRepository clickRepository;


    /**
     * Insert a click event
     * @param clicks
     * @return
     */
    public List<Click> insertClicks(List<Click> clicks) {
        return clickRepository.saveAllAndFlush(clicks);
    }
}

