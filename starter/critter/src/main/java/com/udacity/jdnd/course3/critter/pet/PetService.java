package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet savePet(Pet pet, long ownerId) {
        Customer owner = customerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + ownerId));
        pet.setOwner(owner);
        Pet savedPet = petRepository.save(pet);

        List<Pet> ownerPets = owner.getPets();
        if (ownerPets == null) {
            ownerPets = new java.util.ArrayList<>();
        }
        ownerPets.add(savedPet);
        owner.setPets(ownerPets);
        customerRepository.save(owner);

        return savedPet;
    }

    public Pet getPetById(long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found: " + id));
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
}
