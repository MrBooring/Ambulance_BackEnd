package com.rapidrescue.ambulancewale.service;

import com.rapidrescue.ambulancewale.controller.AuthController;
import com.rapidrescue.ambulancewale.models.entity.AmbulanceFeatures;
import com.rapidrescue.ambulancewale.models.entity.AmbulanceType;
import com.rapidrescue.ambulancewale.repository.AmbulanceFeatureRepository;
import com.rapidrescue.ambulancewale.repository.AmbulanceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AmbulanceService {
    // Inject the AmbulanceFeatureRepository at the class level
    @Autowired
    private AmbulanceFeatureRepository ambulanceFeatureRepository;

    @Autowired
    private AmbulanceTypeRepository ambulanceTypeRepository;

    Logger log = Logger.getLogger(AuthController.class.getName());


    public List<AmbulanceType> getAmbulanceType(){
        log.info("=============================getAmbulanceType========================");
        List<AmbulanceType> ambulanceTypes = ambulanceTypeRepository.findAll();
        log.info("============================= ambulanceTypes fetched ========================");
        return  ambulanceTypes;
    }

}
