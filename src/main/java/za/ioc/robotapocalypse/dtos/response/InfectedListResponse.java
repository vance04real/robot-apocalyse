package za.ioc.robotapocalypse.dtos.response;

import java.util.List;
import lombok.Data;
import za.ioc.robotapocalypse.domain.Survivor;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Data
public class InfectedListResponse extends ApiResponse{

  private List<Survivor> body;

}
