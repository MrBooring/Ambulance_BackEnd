package com.rapidrescue.ambulancewale;

import com.rapidrescue.ambulancewale.models.entity.AmbulanceFeatures;
import com.rapidrescue.ambulancewale.repository.AmbulanceFeatureRepository;
import com.rapidrescue.ambulancewale.security.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class AmbulancewaleApplication {

	@Autowired
	private TwilioConfig twilioConfig;

	public static void main(String[] args) {
		SpringApplication.run(AmbulancewaleApplication.class, args);

	}

	@Bean
		public CommandLineRunner loadData(AmbulanceFeatureRepository ambulanceFeaturesRepository) {
			return args -> {
				// Check if features are already present in the database
				if (ambulanceFeaturesRepository.count() == 0) {
					// Create 3 initial features with descriptions
					AmbulanceFeatures feature1 = new AmbulanceFeatures();
					feature1.setFeatureName("Oxygen Cylinder");
					feature1.setDescription("Portable oxygen supply for emergency use.");

					AmbulanceFeatures feature2 = new AmbulanceFeatures();
					feature2.setFeatureName("Ventilator");
					feature2.setDescription("Assists patients with breathing issues.");

					AmbulanceFeatures feature3 = new AmbulanceFeatures();
					feature3.setFeatureName("Paramedic Onboard");
					feature3.setDescription("Paramedics for transport care");

					// Save features to the database
					ambulanceFeaturesRepository.save(feature1);
					ambulanceFeaturesRepository.save(feature2);
					ambulanceFeaturesRepository.save(feature3);

					System.out.println("Initial ambulance features with descriptions added.");
				}
			};
		}

	@PostConstruct
	public void initTwilio(){
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
		log.info("Twilio Account initialized. " );
	}
}
