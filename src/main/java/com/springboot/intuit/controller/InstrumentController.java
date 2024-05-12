package com.springboot.intuit.controller;

import com.springboot.intuit.payload.InstrumentDto;
import com.springboot.intuit.payload.InstrumentDtoResponse;
import com.springboot.intuit.service.InstrumentService;
import com.springboot.intuit.utils.AppConstants;
import com.springboot.intuit.utils.Validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/instruments")
public class InstrumentController {

    private InstrumentService instrumentService;
    private Validation validation;

    public InstrumentController(InstrumentService instrumentService, Validation validation) {
        this.instrumentService = instrumentService;
        this.validation = validation;
    }

    // Build Add Instrument REST API
    @PostMapping
    public ResponseEntity<InstrumentDto> addInstrument(@RequestBody InstrumentDto instrumentDto) {
        validation.validateInstrumentDto(instrumentDto);
        InstrumentDto savedInstrument = instrumentService.addInstrument(instrumentDto);
        return new ResponseEntity<>(savedInstrument, HttpStatus.CREATED);
    }

    // Build Get Instrument REST API
    @GetMapping("{id}")
    public ResponseEntity<InstrumentDto> getInstrument(@PathVariable("id") Long instrumentId) {
        InstrumentDto instrumentDto = instrumentService.getInstrument(instrumentId);
        return ResponseEntity.ok(instrumentDto);
    }

    // Build Get All Instruments REST API
    @GetMapping
    public ResponseEntity<InstrumentDtoResponse> getInstruments(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(instrumentService.getAllInstruments(pageNo, pageSize, sortBy, sortDir));
    }

    // Build Update Instrument REST API
    @PutMapping("{id}")
    public ResponseEntity<InstrumentDto> updateInstrument(@RequestBody InstrumentDto instrumentDto,
            @PathVariable("id") Long instrumentId) {
        return ResponseEntity.ok(instrumentService.updateInstrument(instrumentDto, instrumentId));
    }

    // Build Delete Instrument REST API

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteInstrument(@PathVariable("id") Long instrumentId) {
        instrumentService.deleteInstrument(instrumentId);
        return ResponseEntity.ok("Instrument deleted successfully!.");
    }
}
