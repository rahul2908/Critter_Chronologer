package com.udacity.critter.services;

import com.udacity.critter.entity.Customer;
import com.udacity.critter.entity.Pet;
import com.udacity.critter.repository.CustomersRepository;
import com.udacity.critter.repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.util.List;

@Service
@Transactional
public class PetsService {

    @Autowired
    private PetsRepository petRepository;
    @Autowired
    private CustomersRepository custRepository;

    public Pet getPetById(long pId) {
        return petRepository.getOne(pId);
    }

    public List<Pet> getPetsByCustomerId(long custId) {
        return petRepository.getAllByCustomerId(custId);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }


    public Pet savePet(Pet p, long owner_id) {

        Customer customer = custRepository.getOne(owner_id);
        p.setCustomer(customer);
        p = petRepository.save(p);
        customer.insertPet(p);

        custRepository.save(customer);
        return p;
    }
}
