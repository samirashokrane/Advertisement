package ir.webmetric.advertisement.repository;

import ir.webmetric.advertisement.model.Impression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@Repository
public interface ImpressionRepository extends JpaRepository<Impression, String>{
}
