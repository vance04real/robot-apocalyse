package za.ioc.robotapocalypse.dtos.response;

import lombok.Data;
import za.ioc.robotapocalypse.domain.Survivor;
import za.ioc.robotapocalypse.dtos.requests.CreateSurvivorRequest;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Data
public class CreateSurvivorResponse extends ApiResponse{

  private Survivor body;

}
