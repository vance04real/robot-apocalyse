package za.ioc.robotapocalypse.integration.api;

import java.io.IOException;
import za.ioc.robotapocalypse.dtos.response.RobotResponse;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/
public interface RobotService {

RobotResponse[] fetchRobots() throws IOException;

}
