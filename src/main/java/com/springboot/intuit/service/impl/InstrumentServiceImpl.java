package com.springboot.intuit.service.impl;

import com.google.gson.Gson;
import com.springboot.intuit.entity.Instrument;
import com.springboot.intuit.exception.ResourceAlreadyException;
import com.springboot.intuit.exception.ResourceNotFoundException;
import com.springboot.intuit.payload.InstrumentDto;
import com.springboot.intuit.payload.InstrumentDtoResponse;
import com.springboot.intuit.repository.InstrumentRepository;
import com.springboot.intuit.service.InstrumentService;
import com.springboot.intuit.service.RedisService;
import com.springboot.intuit.utils.AppConstants;
import com.springboot.intuit.utils.Utility;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstrumentServiceImpl implements InstrumentService {

    private InstrumentRepository instrumentRepository;
    private ModelMapper modelMapper;
    private Utility utility;
    private RedisService redisService;

    @Value("${spring.redis.default-ttl}")
    private int redisKeyTtl;

    public InstrumentServiceImpl(InstrumentRepository instrumentRepository, ModelMapper modelMapper, Utility utility,
            RedisService redisService) {
        this.instrumentRepository = instrumentRepository;
        this.modelMapper = modelMapper;
        this.utility = utility;
        this.redisService = redisService;

    }

    @Override
    public InstrumentDto addInstrument(InstrumentDto instrumentDto) {

        Optional<Instrument> existingCategoryOptional = instrumentRepository
                .findByNameAndUserId(instrumentDto.getName(), instrumentDto.getUserId());
        if (existingCategoryOptional.isPresent()) {
            throw new ResourceAlreadyException("Instrument", "name", instrumentDto.getName());
        }
        // Set value in Redis
        String objectString = new Gson().toJson(instrumentDto);
        redisService.setValueWithTtl(AppConstants.INSTRUMENT + instrumentDto.getId(), objectString, redisKeyTtl); 

        Instrument instrument = modelMapper.map(instrumentDto, Instrument.class);
         try {
            instrument.setAvailableFromDate(utility.stringToDate(instrumentDto.getAvailableFromDate()));
            instrument.setAvailableToDate(utility.stringToDate(instrumentDto.getAvailableToDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Instrument savedInstrument = instrumentRepository.save(instrument);
        return modelMapper.map(savedInstrument, InstrumentDto.class);
    }

    @Override
    public InstrumentDto getInstrument(Long instrumentId) {
        // Check if the category exists in Redis
        String value = (String) redisService.getValue(AppConstants.INSTRUMENT + instrumentId);
        if (value != null) {
            // If found in Redis, return it directly
            return new Gson().fromJson(value, InstrumentDto.class);
        }

        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new ResourceNotFoundException("Instrument", "id", instrumentId));

        // Map the category to DTO
        InstrumentDto response = modelMapper.map(instrument, InstrumentDto.class);

        // Store the fetched value in Redis for future use
        String objectString = new Gson().toJson(response);
        redisService.setValueWithTtl(AppConstants.INSTRUMENT + instrumentId, objectString, redisKeyTtl); // Example TTL
                                                                                                         // of 1 hour

        return response;
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

        Instrument instrument = instrumentRepository.findByIdAndUserId(instrumentId, instrumentDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Instrument ", "userId : " +instrumentDto.getUserId()+" instrumentId", instrumentId));

        utility.updateInstrumentInfo(instrumentDto, instrumentId, instrument);

        // Set updated value in Redis
        String objectString = new Gson().toJson(instrumentDto);
        redisService.setValueWithTtl(AppConstants.INSTRUMENT + instrumentId, objectString, redisKeyTtl); 

        Instrument updatedInstrument = instrumentRepository.save(instrument);

        return modelMapper.map(updatedInstrument, InstrumentDto.class);
    }

    @Override
    public void deleteInstrument(Long instrumentId, String userId) {

        Instrument instrument = instrumentRepository.findByIdAndUserId(instrumentId, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Instrument ", "userId : " +userId+" instrumentId", instrumentId));
// Delete the category from Redis
        redisService.deleteKey(AppConstants.INSTRUMENT + instrumentId);

        instrumentRepository.delete(instrument);
    }
}
