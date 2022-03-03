package za.ioc.robotapocalypse.dtos.response;

import lombok.Data;

/**
 * Created  03/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Data
public class ReportsResponse<T> extends ApiResponse{

  private T percentageValue;

}
