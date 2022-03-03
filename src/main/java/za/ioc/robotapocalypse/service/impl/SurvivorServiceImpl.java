package za.ioc.robotapocalypse.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.ioc.robotapocalypse.domain.Location;
import za.ioc.robotapocalypse.domain.Survivor;
import za.ioc.robotapocalypse.dtos.requests.CreateSurvivorRequest;
import za.ioc.robotapocalypse.dtos.requests.UpdateSurvivorRequest;
import za.ioc.robotapocalypse.dtos.response.ApiResponse;
import za.ioc.robotapocalypse.dtos.response.CreateSurvivorResponse;
import za.ioc.robotapocalypse.dtos.response.InfectedListResponse;
import za.ioc.robotapocalypse.dtos.response.ReportsResponse;
import za.ioc.robotapocalypse.dtos.response.RobotResponse;
import za.ioc.robotapocalypse.enums.Gender;
import za.ioc.robotapocalypse.enums.Resource;
import za.ioc.robotapocalypse.enums.ResponseCodesEnum;
import za.ioc.robotapocalypse.integration.api.RobotService;
import za.ioc.robotapocalypse.repository.SurvivorRepository;
import za.ioc.robotapocalypse.service.api.SurvivorService;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Service
@RequiredArgsConstructor
public class SurvivorServiceImpl implements SurvivorService {

  private final SurvivorRepository survivorRepository;
  private  final RobotService robotService;


  @Override
  public CreateSurvivorResponse addSurvivor(CreateSurvivorRequest createSurvivorRequest) {
    CreateSurvivorResponse createSurvivorResponse = new CreateSurvivorResponse();

    Survivor survivor = new Survivor();
    Location location = new Location();

    survivor.setName(createSurvivorRequest.getName());
    survivor.setAge(createSurvivorRequest.getAge());
    survivor.setGender(Gender.valueOf(createSurvivorRequest.getGender()));

    location.setLatitude(createSurvivorRequest.getLocation().getLatitude());
    location.setLongitude(createSurvivorRequest.getLocation().getLongitude());

    survivor.setLocation(location);
      Set<Resource> resourceSet = createSurvivorRequest.getResources()
        .stream()
        .map(Resource::getResource)
        .collect(Collectors.toSet());
    survivor.setResource(resourceSet);

    final Survivor savedSurvivor = survivorRepository.save(survivor);

    createSurvivorResponse.setBody(savedSurvivor);

    return createSurvivorResponse;
  }


  @Override
  public ApiResponse updateSurvivor(Long id, UpdateSurvivorRequest updateSurvivorRequest) {
    ApiResponse apiResponse = new ApiResponse();

    Optional<Survivor> optionalSurvivor = survivorRepository.findById(id);

    Location retrievedLocation = optionalSurvivor.get().getLocation();

    if(optionalSurvivor.isEmpty()){
      apiResponse.setCode(ResponseCodesEnum.NO_SURVIVOR_FOUND.getCode());
      apiResponse.setDescription(ResponseCodesEnum.NO_SURVIVOR_FOUND.getDescription());
    return apiResponse;
    }

    Survivor survivor = optionalSurvivor.get();

    retrievedLocation.setLatitude(updateSurvivorRequest.getLocation().getLatitude());
    retrievedLocation.setLongitude(updateSurvivorRequest.getLocation().getLongitude());
    survivor.setLocation(retrievedLocation);

    survivorRepository.save(survivor);

    apiResponse.setCode(ResponseCodesEnum.SUCCESS.getCode());
    apiResponse.setDescription(ResponseCodesEnum.SUCCESS_UPDATE.getDescription());
    return apiResponse;
  }

  @Override
  public ApiResponse reportInfection(Long id, Long reporterId) {

    ApiResponse apiResponse = new ApiResponse();

    if(id.equals(reporterId)){
      apiResponse.setCode(ResponseCodesEnum.SURVIVOR_CANT_REPORT.getCode());
      apiResponse.setDescription(ResponseCodesEnum.SURVIVOR_CANT_REPORT.getDescription());
      return apiResponse;
    }

    Optional<Survivor> optionalSurvivor = survivorRepository.findById(id);

    if(optionalSurvivor.isEmpty()){
      apiResponse.setCode(ResponseCodesEnum.NO_SURVIVOR_FOUND.getCode());
      apiResponse.setDescription(ResponseCodesEnum.NO_SURVIVOR_FOUND.getDescription());
      return apiResponse;
    }

    Survivor survivor = optionalSurvivor.get();
    int count = survivor.getReportCount();

    if(survivor.isInfected()){
      apiResponse.setCode(ResponseCodesEnum.SURVIVOR_ALREADY_INFECTED.getCode());
      apiResponse.setDescription(ResponseCodesEnum.SURVIVOR_ALREADY_INFECTED.getDescription());
      return apiResponse;
    }
    count += 1;

    survivor.setReportCount(count);
    Survivor savedSurvivor =  survivorRepository.save(survivor);

    if(savedSurvivor.getReportCount() == 3){
      savedSurvivor.setInfected(true);
      apiResponse.setCode(ResponseCodesEnum.SURVIVOR_FLAGGED.getCode());
      apiResponse.setDescription(ResponseCodesEnum.SURVIVOR_FLAGGED.getDescription());

    }
    else{
      apiResponse.setCode(ResponseCodesEnum.SURVIVOR_FLAGGED_PENDING_COUNT.getCode());
      apiResponse.setDescription(ResponseCodesEnum.SURVIVOR_FLAGGED_PENDING_COUNT.getDescription());
    }

    survivorRepository.save(survivor);
    return apiResponse;

  }

  @Override
  public ReportsResponse getInfectedreports() {
    ReportsResponse infectedReportsResponse = new ReportsResponse();

    long infected = survivorRepository.countAllByInfectedTrue();

    long  allSurvivors = survivorRepository.count();

    if(allSurvivors == 0){
      infectedReportsResponse.setCode(ResponseCodesEnum.NO_RECORDS_FOR_NOW_TO_COMPUTE.getCode());
      infectedReportsResponse.setDescription(ResponseCodesEnum.NO_RECORDS_FOR_NOW_TO_COMPUTE.getDescription());
    }

    float result = ((float) infected) / allSurvivors;

    float percentage = result * 100;


    infectedReportsResponse.setCode(ResponseCodesEnum.SUCCESS.getCode());
    infectedReportsResponse.setDescription(ResponseCodesEnum.SUCCESS.getDescription());
    infectedReportsResponse.setPercentageValue(percentage);

    return infectedReportsResponse;
  }

  @Override
  public ApiResponse getNonInfectedreports() {
    ReportsResponse reportsResponse = new ReportsResponse();

    int noneInfected = survivorRepository.countAllByInfectedFalse();

    long allSurvivors = survivorRepository.count();

    if(allSurvivors == 0){
      reportsResponse.setCode(ResponseCodesEnum.NO_RECORDS_FOR_NOW_TO_COMPUTE.getCode());
      reportsResponse.setDescription(ResponseCodesEnum.NO_RECORDS_FOR_NOW_TO_COMPUTE.getDescription());
      return reportsResponse;
    }

    float result = ((float) noneInfected) / allSurvivors;

    float percentage = result * 100;

    reportsResponse.setCode(ResponseCodesEnum.SUCCESS.getCode());
    reportsResponse.setDescription(ResponseCodesEnum.SUCCESS.getDescription());
    reportsResponse.setPercentageValue(percentage);

    return reportsResponse;
  }

  @Override
  public RobotResponse[] listAllRobots() throws IOException {
    RobotResponse[] robotResponse = robotService.fetchRobots();

    Stream<RobotResponse> sortedRobotList = Arrays.stream(robotResponse)
        .sorted(Comparator.comparing(RobotResponse::getCategory));

    RobotResponse[] newArray = sortedRobotList.toArray(RobotResponse[]::new);
    return newArray;
  }


  @Override
  public InfectedListResponse getListofInfectedSurvivors() {

    InfectedListResponse infectedListResponse = new InfectedListResponse();

    List<Survivor> survivorInfectedList = survivorRepository.findAllByInfectedTrue();

    if(survivorInfectedList.isEmpty()){
      infectedListResponse.setCode(ResponseCodesEnum.NO_INFECTED_SURVIVORS_FOUND.getCode());
      infectedListResponse.setDescription(ResponseCodesEnum.NO_INFECTED_SURVIVORS_FOUND.getDescription());
      return infectedListResponse;
    }

    infectedListResponse.setBody(survivorInfectedList);
    infectedListResponse.setCode(ResponseCodesEnum.SUCCESS.getCode());
    infectedListResponse.setDescription(ResponseCodesEnum.SUCCESS.getDescription());
    return infectedListResponse;
  }

  @Override
  public InfectedListResponse getListofNoneInfectedSurvivors() {
    InfectedListResponse infectedListResponse = new InfectedListResponse();

    List<Survivor> survivorInfectedList = survivorRepository.findAllByInfectedFalse();

    if(survivorInfectedList.isEmpty()){
      infectedListResponse.setCode(ResponseCodesEnum.NO_NONEINFECTED_SURVIVORS_FOUND.getCode());
      infectedListResponse.setDescription(ResponseCodesEnum.NO_NONEINFECTED_SURVIVORS_FOUND.getDescription());
      return infectedListResponse;
    }

    infectedListResponse.setBody(survivorInfectedList);
    infectedListResponse.setCode(ResponseCodesEnum.SUCCESS.getCode());
    infectedListResponse.setDescription(ResponseCodesEnum.SUCCESS.getDescription());
    return infectedListResponse;
  }


}


