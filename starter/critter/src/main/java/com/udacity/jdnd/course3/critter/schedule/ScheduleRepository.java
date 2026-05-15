package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByEmployeesContaining(Employee employee);
    List<Schedule> findByPetsContaining(Pet pet);

    @Query("SELECT DISTINCT s FROM Schedule s JOIN s.pets p WHERE p IN :pets")
    List<Schedule> findByPetsIn(@Param("pets") List<Pet> pets);
}
