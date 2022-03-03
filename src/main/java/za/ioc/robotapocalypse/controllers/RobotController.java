package za.ioc.robotapocalypse.controllers;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.ioc.robotapocalypse.dtos.response.RobotResponse;
import za.ioc.robotapocalypse.service.api.SurvivorService;


/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@RestController
@RequestMapping(value = "/robots")
@RequiredArgsConstructor
public class RobotController extends BaseController {

  private final SurvivorService survivorService;


  @GetMapping
  public ResponseEntity<RobotResponse[]> getInfectedReports() throws IOException {

    RobotResponse[] robotResponse  = survivorService.listAllRobots();

    return ResponseEntity.ok(robotResponse);

  }

}
