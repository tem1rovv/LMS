package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lmsad.entity.Certificate;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, String> {


    @Query("""
    select c from Certificate c where c.user.id = :id
""")
    List<Certificate> findCertificateByUserId(String id);


    @Query("""
    select count(1) from Certificate c where c.user.id = :id
""")
    Integer getCountByUserId(String id);
}
