package za.ioc.robotapocalypse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ioc.robotapocalypse.domain.Location;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/
public interface LocationRepository extends JpaRepository<Location, Long> {

}
