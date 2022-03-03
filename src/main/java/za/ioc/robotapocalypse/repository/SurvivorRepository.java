package za.ioc.robotapocalypse.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import za.ioc.robotapocalypse.domain.Survivor;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/
public interface SurvivorRepository extends JpaRepository<Survivor , Long> {

 int countAllByInfectedTrue();

 int countAllByInfectedFalse();

 List<Survivor> findAllByInfectedTrue();

 List<Survivor> findAllByInfectedFalse();

}
