package com.rapidrescue.ambulancewale.seeder;

import com.rapidrescue.ambulancewale.models.entity.AmbulanceFeatures;
import com.rapidrescue.ambulancewale.models.entity.AmbulanceType;
import com.rapidrescue.ambulancewale.repository.AmbulanceFeatureRepository;
import com.rapidrescue.ambulancewale.repository.AmbulanceTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class AmbulanceTypeSeeder implements CommandLineRunner {
    private final AmbulanceTypeRepository ambulanceTypeRepository;
    private final AmbulanceFeatureRepository ambulanceFeatureRepository;

    public AmbulanceTypeSeeder(AmbulanceTypeRepository ambulanceTypeRepository,
                               AmbulanceFeatureRepository ambulanceFeatureRepository) {
        this.ambulanceTypeRepository = ambulanceTypeRepository;
        this.ambulanceFeatureRepository = ambulanceFeatureRepository;
    }



    @Override
    public void run(String... args) {
        if (ambulanceFeatureRepository.count() == 0) {
            List<AmbulanceFeatures> defaultFeatures = List.of(
                    new AmbulanceFeatures("Oxygen Cylinder", "Used for providing oxygen to patients"),
                    new AmbulanceFeatures("Ventilator", "For patients requiring mechanical ventilation"),
                    new AmbulanceFeatures("Defibrillator", "To perform emergency resuscitation"),
                    new AmbulanceFeatures("Paramedic Support", "Medical personnel to assist with care")
            );

            ambulanceFeatureRepository.saveAll(defaultFeatures);
            System.out.println("✅ Default ambulance features added to the database!");
        } else {
            System.out.println("🚀 Default ambulance features already exist.");
        }
        
        if (ambulanceTypeRepository.count() == 0) {
            List<AmbulanceFeatures> basicFeatures = ambulanceFeatureRepository.findAll(); // Get all features from DB

            // Define Ambulance Types and associate them with features

            AmbulanceType basicLifeSupport = new AmbulanceType("Basic Life Support", "Basic emergency care", 2  );
            basicLifeSupport.setFeatures(basicFeatures); // Assign all features to this ambulance type

            AmbulanceType advancedLifeSupport = new AmbulanceType("Advanced Life Support",
                    "Equipped with ventilators & defibrillators", 4);
            advancedLifeSupport.setFeatures(basicFeatures); // Assign all features to this ambulance type

            AmbulanceType criticalCare = new AmbulanceType("Critical Care Ambulance",
                    "For ICU patients with specialized care", 3);
            criticalCare.setFeatures(basicFeatures); // Assign all features to this ambulance type

            // Save to DB
            ambulanceTypeRepository.saveAll(List.of(basicLifeSupport, advancedLifeSupport, criticalCare));
            System.out.println("✅ Default ambulance types with features added to the database!");
        } else {
            System.out.println("🚀 Default ambulance types already exist.");
        }
    }
}
