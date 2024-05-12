package com.springboot.intuit.service;

import com.springboot.intuit.payload.InstrumentDto;
import com.springboot.intuit.payload.InstrumentDtoResponse;

public interface InstrumentService {
    InstrumentDto addInstrument(InstrumentDto instrumentDto);

    InstrumentDto getInstrument(Long instrumentId);

    InstrumentDtoResponse getAllInstruments(int pageNo, int pageSize, String sortBy, String sortDir);

    InstrumentDto updateInstrument(InstrumentDto instrumentDto, Long instrumentId);

    void deleteInstrument(Long instrumentId);
}
