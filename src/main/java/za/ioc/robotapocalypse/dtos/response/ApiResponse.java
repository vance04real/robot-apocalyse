package za.ioc.robotapocalypse.dtos.response;

import lombok.Data;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Data
public class ApiResponse {

  private int code;
  private String description;
  private String message;

}
