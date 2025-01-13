package org.example.util;

import org.example.model.dto.CurrencyCourseDto;
import reactor.core.publisher.Mono;

public class CurrencyCourseMapper {

    private CurrencyCourseMapper() {
    }

    public static CurrencyCourseDto map(Mono<CurrencyCourseDto> courseDtoMono) {
       return courseDtoMono.map(dto -> {
                    if (!dto.rates().isEmpty()) {
                        return dto;
                    } else {
                        throw new IllegalArgumentException("Rates for currency:" + dto.currency() + " are unavailable.");
                    }
                }
        ).block();
    }
}
