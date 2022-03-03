package za.ioc.robotapocalypse.dtos.requests;

import java.util.Set;
import lombok.Data;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Data
public class CreateSurvivorRequest {

  private String name;
  private int age;
  private String gender;
  private LocationRequest location;
  private Set<String> resources;

}
