package com.udacity.critter.pet;

import com.udacity.critter.entity.Pet;
import com.udacity.critter.services.PetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetsService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet p = new Pet();
        p.setType(petDTO.getType());
        p.setName(petDTO.getName());
        p.setBirthDate(petDTO.getBirthDate());
        p.setNotes(petDTO.getNotes());
        return getPetDTO(petService.savePet(p, petDTO.getOwnerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return getPetDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(this::getPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pet = petService.getPetsByCustomerId(ownerId);
        return pet.stream().map(this::getPetDTO).collect(Collectors.toList());
    }

    private PetDTO getPetDTO(Pet p) {
        PetDTO pDTO = new PetDTO();
        pDTO.setId(p.getId());
        pDTO.setName(p.getName());
        pDTO.setType(p.getType());
        pDTO.setOwnerId(p.getCustomer().getId());
        pDTO.setBirthDate(p.getBirthDate());
        pDTO.setNotes(p.getNotes());
        return pDTO;
    }
}
