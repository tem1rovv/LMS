package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lmsad.entity.Assignment;
import uz.pdp.lmsad.entity.AssignmentResponse;


public interface AssignmentResponseRepository extends JpaRepository<AssignmentResponse, String> {



}
