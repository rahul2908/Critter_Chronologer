package com.udacity.critter.services;

import com.udacity.critter.entity.Customer;
import com.udacity.critter.entity.Pet;
import com.udacity.critter.repository.CustomersRepository;
import com.udacity.critter.repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.*;


@Service
@Transactional
public class CustomersService {
    @Autowired
    private CustomersRepository custRepository;


    @Autowired
    private PetsRepository petRepository;

    public Customer getCustomerByPetId(long petId) {
        return petRepository.getOne(petId).getCustomer();
    }


    public Customer saveCustomer(Customer cust, List<Long> pIds) {

        List<Pet> petList = new ArrayList<>();

        if (pIds != null && !pIds.isEmpty()) {
            petList = pIds.stream().map((petId) -> petRepository.getOne(petId)).collect(Collectors.toList());
        }

        cust.setPets(petList);

        return custRepository.save(cust);
    }

    public List<Customer> getAllCustomers() {
        return custRepository.findAll();
    }
}
