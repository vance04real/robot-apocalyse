package za.ioc.robotapocalypse.dtos.response;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Data
public class RetrieveRobotResponse extends  ApiResponse{

  public String model;
  public String serialNumber;
  public LocalDateTime manufacturedDate;
  public String category;

}
