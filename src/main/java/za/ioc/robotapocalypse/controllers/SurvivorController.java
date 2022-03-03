package za.ioc.robotapocalypse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.ioc.robotapocalypse.dtos.requests.CreateSurvivorRequest;
import za.ioc.robotapocalypse.dtos.requests.UpdateSurvivorRequest;
import za.ioc.robotapocalypse.dtos.response.ApiResponse;
import za.ioc.robotapocalypse.dtos.response.CreateSurvivorResponse;
import za.ioc.robotapocalypse.dtos.response.InfectedListResponse;
import za.ioc.robotapocalypse.dtos.response.ReportsResponse;
import za.ioc.robotapocalypse.enums.ResponseCodesEnum;
import za.ioc.robotapocalypse.integration.api.RobotService;
import za.ioc.robotapocalypse.service.api.SurvivorService;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@RestController
@RequestMapping(value = "/survivors")
@RequiredArgsConstructor
public class SurvivorController extends BaseController {


  private final SurvivorService survivorService;

  @PostMapping
  public ResponseEntity<ApiResponse> addSurvivor(@RequestBody CreateSurvivorRequest createSurvivorRequest){

    if (createSurvivorRequest == null){
        buildApiResponse(ResponseCodesEnum.MISSING_BODY);
    }

    CreateSurvivorResponse apiResponse = survivorService.addSurvivor(createSurvivorRequest);

    return buildApiResponse(apiResponse, ResponseCodesEnum.SUCCESS_CREATION);

  }


  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse> updateSurvivor(@PathVariable Long id , @RequestBody
      UpdateSurvivorRequest updateSurvivorRequest){

    if(updateSurvivorRequest == null){
        return buildApiResponse(ResponseCodesEnum.MISSING_BODY);
    }

    if(updateSurvivorRequest.getLocation().getLatitude() == "" || updateSurvivorRequest.getLocation().getLongitude() == " "){
      return buildApiResponse(ResponseCodesEnum.MISSING_FIELDS);
    }

    ApiResponse apiResponse = survivorService.updateSurvivor(id,updateSurvivorRequest);

    return buildApiResponse(apiResponse, ResponseCodesEnum.SUCCESS_UPDATE);

  }


  @PutMapping("/{id}/reportinfection/{infectionId}")
  public ApiResponse reportInfection(@PathVariable Long id, @PathVariable Long infectionId){

    ApiResponse apiResponse = survivorService.reportInfection(id , infectionId);

    return apiResponse;

  }

  @GetMapping("reports/noninfected")
  public ResponseEntity<ApiResponse> getNonInfectedReports(){

    ApiResponse apiResponse = survivorService.getNonInfectedreports();

    return buildApiResponse(apiResponse, ResponseCodesEnum.SUCCESS);

  }

  @GetMapping("reports/infected")
  public ReportsResponse getInfectedreports(){

    ReportsResponse apiResponse = survivorService.getInfectedreports();

    return apiResponse;

  }


  @GetMapping("/reports/listofinfectedSurvivors")
  public InfectedListResponse getListofInfectedSurvivors(){

    InfectedListResponse apiResponse = survivorService.getListofInfectedSurvivors();

    return  apiResponse;

  }

  @GetMapping("/reports/listofnoneinfectedSurvivors")
  public ResponseEntity<ApiResponse> getListofNoneInfectedSurvivors(){

    ApiResponse apiResponse = survivorService.getListofNoneInfectedSurvivors();

    return buildApiResponse(apiResponse, ResponseCodesEnum.SUCCESS);

  }


}
