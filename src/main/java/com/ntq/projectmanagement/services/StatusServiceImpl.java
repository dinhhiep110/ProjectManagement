package com.ntq.projectmanagement.services;

import com.ntq.projectmanagement.entities.Status;
import com.ntq.projectmanagement.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService{
    @Autowired
    StatusRepository statusRepository;

    @Override
    public Status getStatusById(long id) {
        Optional<Status> statusOptional = statusRepository.findById(id);
        return statusOptional.orElseGet(Status::new);
    }
}
