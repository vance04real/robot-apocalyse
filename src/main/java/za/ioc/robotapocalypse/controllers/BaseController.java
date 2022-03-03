package za.ioc.robotapocalypse.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ioc.robotapocalypse.dtos.response.ApiResponse;
import za.ioc.robotapocalypse.enums.ResponseCodesEnum;

/**
 * Created  02/2022 Project  robot-apocalypse Author   echikuni
 **/
public class BaseController {

  protected ResponseEntity<ApiResponse> buildApiResponse(
      ApiResponse apiResponse, HttpStatus httpStatus) {
    return new ResponseEntity<>(apiResponse, httpStatus);
  }

  protected ResponseEntity<ApiResponse> buildApiResponse(
      ApiResponse apiResponse, ResponseCodesEnum responseCodesEnum) {
    apiResponse.setCode(responseCodesEnum.getCode());
    apiResponse.setDescription(responseCodesEnum.getDescription());
    return new ResponseEntity<>(apiResponse, responseCodesEnum.getHttpStatus());
  }

  protected ResponseEntity<ApiResponse> buildApiResponse(
      ApiResponse apiResponse, ResponseCodesEnum responseCodesEnum, HttpStatus httpStatus) {
    apiResponse.setCode(responseCodesEnum.getCode());
    apiResponse.setDescription(responseCodesEnum.getDescription());
    return new ResponseEntity<>(apiResponse, httpStatus);
  }

  protected ResponseEntity<ApiResponse> buildApiResponse(ResponseCodesEnum responseCodesEnum) {
    return buildApiResponse(responseCodesEnum, responseCodesEnum.getHttpStatus());
  }

  protected ResponseEntity<ApiResponse> buildApiResponse(
      ResponseCodesEnum responseCodesEnum, HttpStatus httpStatus) {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setCode(responseCodesEnum.getCode());
    apiResponse.setDescription(responseCodesEnum.getDescription());
    return new ResponseEntity<>(apiResponse, httpStatus);
  }

  protected ResponseEntity<String> buildRawResponse(String body, HttpStatus httpStatus) {
    return new ResponseEntity<>(body, httpStatus);
  }

  protected ResponseEntity<ApiResponse> buildRawResponse(HttpStatus httpStatus) {
    return new ResponseEntity<ApiResponse>(httpStatus);
  }

}
