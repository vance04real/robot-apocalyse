package za.ioc.robotapocalypse.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Created  02/2022 Project  robot-apocalypse Author   echikuni
 **/

@Data
@Entity
@Table(name="location")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String longitude;
  private String latitude;

}
