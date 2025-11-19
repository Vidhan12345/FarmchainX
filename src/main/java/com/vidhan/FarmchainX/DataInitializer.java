package com.vidhan.FarmchainX;

import com.vidhan.FarmchainX.entity.ERole;
import com.vidhan.FarmchainX.entity.Role;
import com.vidhan.FarmchainX.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        if (roleRepository.count() == 0) {
            // Use the custom constructor (roleName, description)
            roleRepository.save(new Role(ERole.ROLE_FARMER, "Agricultural producer"));
            roleRepository.save(new Role(ERole.ROLE_DISTRIBUTOR, "Wholesale distributor"));
            roleRepository.save(new Role(ERole.ROLE_RETAILER, "Retail seller"));
            roleRepository.save(new Role(ERole.ROLE_ADMIN, "System administrator"));
            roleRepository.save(new Role(ERole.ROLE_CONSUMER, "End consumer"));

            System.out.println("✅ Roles initialized successfully!");
        } else {
            System.out.println("✅ Roles already exist in database");
        }
    }
}
