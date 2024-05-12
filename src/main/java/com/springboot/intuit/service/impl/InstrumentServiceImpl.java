package com.springboot.intuit.service.impl;

import com.springboot.intuit.entity.Instrument;
import com.springboot.intuit.exception.ResourceAlreadyException;
import com.springboot.intuit.exception.ResourceNotFoundException;
import com.springboot.intuit.payload.InstrumentDto;
import com.springboot.intuit.payload.InstrumentDtoResponse;
import com.springboot.intuit.repository.InstrumentRepository;
import com.springboot.intuit.service.InstrumentService;
import com.springboot.intuit.utils.Utility;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstrumentServiceImpl implements InstrumentService {

    private InstrumentRepository instrumentRepository;
    private ModelMapper modelMapper;
    private Utility utility;

    public InstrumentServiceImpl(InstrumentRepository instrumentRepository, ModelMapper modelMapper, Utility utility) {
        this.instrumentRepository = instrumentRepository;
        this.modelMapper = modelMapper;
        this.utility = utility;
    }

    @Override
    public InstrumentDto addInstrument(InstrumentDto instrumentDto) {

        Optional<Instrument> existingCategoryOptional = instrumentRepository
                .findByNameAndUserId(instrumentDto.getName(), instrumentDto.getUserId());
        if (existingCategoryOptional.isPresent()) {
            throw new ResourceAlreadyException("Instrument", "name", instrumentDto.getName());
        }

        Instrument instrument = modelMapper.map(instrumentDto, Instrument.class);
        Instrument savedInstrument = instrumentRepository.save(instrument);
        return modelMapper.map(savedInstrument, InstrumentDto.class);
    }

    @Override
    public InstrumentDto getInstrument(Long instrumentId) {

        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new ResourceNotFoundException("Instrument", "id", instrumentId));

        return modelMapper.map(instrument, InstrumentDto.class);
    }

    @Override
    public InstrumentDtoResponse getAllInstruments(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Instrument> posts = instrumentRepository.findAll(pageable);

        // get content for page object
        List<Instrument> listOfInstruments = posts.getContent();

        List<InstrumentDto> content = listOfInstruments.stream()
                .map((instrument) -> modelMapper.map(instrument, InstrumentDto.class))
                .collect(Collectors.toList());

        InstrumentDtoResponse postResponse = new InstrumentDtoResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public InstrumentDto updateInstrument(InstrumentDto instrumentDto, Long instrumentId) {

        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new ResourceNotFoundException("Instrument", "id", instrumentId));

        utility.updateInstrumentInfo(instrumentDto, instrumentId, instrument);

        Instrument updatedInstrument = instrumentRepository.save(instrument);

        return modelMapper.map(updatedInstrument, InstrumentDto.class);
    }

    @Override
    public void deleteInstrument(Long instrumentId) {

        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new ResourceNotFoundException("Instrument", "id", instrumentId));

        instrumentRepository.delete(instrument);
    }
}
