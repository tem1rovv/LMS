package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lmsad.entity.AssignmentSubmission;

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, String> {
}