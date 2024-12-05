package org.example.util;

import org.example.model.dto.CurrencyCourseDto;
import reactor.core.publisher.Mono;

public class CurrencyCourseMapper {

    private CurrencyCourseMapper() {
    }

    public static CurrencyCourseDto map(Mono<CurrencyCourseDto> courseDtoMono) {
        return courseDtoMono.map(dto -> {
                    if (!dto.rates().isEmpty()) {
                        return new CurrencyCourseDto(dto.currency(), dto.rates());
                    } else {
                        throw new IllegalArgumentException("Rates for currency:" + dto.currency() + " are unavailable.");
                    }
                }
        ).block();
    }
}
