package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.StatusDTO;
import com.zemoso.checkr.entities.Status;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.StatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StatusService(StatusRepository statusRepository, ModelMapper modelMapper) {
        this.statusRepository = statusRepository;
        this.modelMapper = modelMapper;
    }

    public StatusDTO getStatus(Long id){
        Optional<Status> statusOptional = statusRepository.findById(id);
        if (statusOptional.isPresent()) {
            Status status = statusOptional.get();
            return convertEntityToDTO(status);
        } else {
            throw new ServiceException("An error occurred while fetching the Report details");
        }
    }

    public StatusDTO convertEntityToDTO(Status status) {
        return modelMapper.map(status, StatusDTO.class);
    }

}
