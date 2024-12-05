package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.model.dto.CurrencyCourseDto;
import org.example.util.CurrencyCourseMapper;
import org.example.valueObject.CurrencyCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CurrencyCourseService {

    private static String ratesTable = "https://api.nbp.pl/api/exchangerates/rates/c/";

    public CurrencyCourseDto getCurrencyCourse(CurrencyCode code) {
        WebClient webClient = WebClient.create();
        String fullRequest = ratesTable + code.getCode() + "/today/";

        Mono<CurrencyCourseDto> mono = webClient.get()
                .uri(fullRequest)
                .retrieve()
                .bodyToMono(CurrencyCourseDto.class);
        return CurrencyCourseMapper.map(mono);
    }
}
