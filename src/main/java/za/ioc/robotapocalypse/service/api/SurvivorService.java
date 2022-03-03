package za.ioc.robotapocalypse.service.api;

import java.io.IOException;
import za.ioc.robotapocalypse.dtos.requests.CreateSurvivorRequest;
import za.ioc.robotapocalypse.dtos.requests.UpdateSurvivorRequest;
import za.ioc.robotapocalypse.dtos.response.ApiResponse;
import za.ioc.robotapocalypse.dtos.response.CreateSurvivorResponse;
import za.ioc.robotapocalypse.dtos.response.InfectedListResponse;
import za.ioc.robotapocalypse.dtos.response.ReportsResponse;
import za.ioc.robotapocalypse.dtos.response.RobotResponse;

/**
 * Created  02/2022 Project  robot-apocalypse Author   echikuni
 **/
public interface SurvivorService {

  CreateSurvivorResponse addSurvivor(CreateSurvivorRequest createSurvivorRequest);

  ApiResponse updateSurvivor(Long id, UpdateSurvivorRequest updateSurvivorRequest);

  ApiResponse reportInfection(Long id, Long infectionId);

  ReportsResponse getInfectedreports();

  InfectedListResponse getListofInfectedSurvivors();

  InfectedListResponse getListofNoneInfectedSurvivors();

  ApiResponse getNonInfectedreports();

  RobotResponse [] listAllRobots() throws IOException;
}
