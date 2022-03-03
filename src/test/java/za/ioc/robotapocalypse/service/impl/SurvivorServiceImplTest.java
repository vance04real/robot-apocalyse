package za.ioc.robotapocalypse.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import za.ioc.robotapocalypse.domain.Location;
import za.ioc.robotapocalypse.domain.Survivor;
import za.ioc.robotapocalypse.dtos.requests.LocationRequest;
import za.ioc.robotapocalypse.dtos.requests.UpdateSurvivorRequest;
import za.ioc.robotapocalypse.dtos.response.ApiResponse;
import za.ioc.robotapocalypse.dtos.response.InfectedListResponse;
import za.ioc.robotapocalypse.dtos.response.ReportsResponse;
import za.ioc.robotapocalypse.dtos.response.RobotResponse;
import za.ioc.robotapocalypse.enums.Gender;
import za.ioc.robotapocalypse.enums.Resource;
import za.ioc.robotapocalypse.integration.api.RobotService;
import za.ioc.robotapocalypse.repository.SurvivorRepository;

@ContextConfiguration(classes = {SurvivorServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SurvivorServiceImplTest {

  @MockBean
  private RobotService robotService;

  @MockBean
  private SurvivorRepository survivorRepository;

  @Autowired
  private SurvivorServiceImpl survivorServiceImpl;

  @Test
  void updateSurvivor_expectOK() {
    Location location = new Location();
    location.setId(123L);
    location.setLatitude("Latitude");
    location.setLongitude("Longitude");

    Survivor survivor = new Survivor();
    survivor.setAge(1);
    survivor.setGender(Gender.MALE);
    survivor.setId(123L);
    survivor.setInfected(true);
    survivor.setLocation(location);
    survivor.setName("Name");
    survivor.setReportCount(3);
    survivor.setResource(new HashSet<>());
    Optional<Survivor> ofResult = Optional.of(survivor);

    Location location1 = new Location();
    location1.setId(123L);
    location1.setLatitude("Latitude");
    location1.setLongitude("Longitude");

    Survivor survivor1 = new Survivor();
    survivor1.setAge(1);
    survivor1.setGender(Gender.MALE);
    survivor1.setId(123L);
    survivor1.setInfected(true);
    survivor1.setLocation(location1);
    survivor1.setName("Name");
    survivor1.setReportCount(3);
    survivor1.setResource(new HashSet<>());
    when(this.survivorRepository.save((Survivor) any())).thenReturn(survivor1);
    when(this.survivorRepository.findById((Long) any())).thenReturn(ofResult);

    LocationRequest locationRequest = new LocationRequest();
    locationRequest.setLatitude("Latitude");
    locationRequest.setLongitude("Longitude");

    UpdateSurvivorRequest updateSurvivorRequest = new UpdateSurvivorRequest();
    updateSurvivorRequest.setLocation(locationRequest);
    ApiResponse actualUpdateSurvivorResult = this.survivorServiceImpl.updateSurvivor(123L,
        updateSurvivorRequest);
    assertEquals(200, actualUpdateSurvivorResult.getCode());
    assertEquals("Update Success ", actualUpdateSurvivorResult.getDescription());
    verify(this.survivorRepository).save((Survivor) any());
    verify(this.survivorRepository).findById((Long) any());
    InfectedListResponse expectedListofNoneInfectedSurvivors = this.survivorServiceImpl.getListofInfectedSurvivors();
    assertEquals(expectedListofNoneInfectedSurvivors,
        this.survivorServiceImpl.getListofNoneInfectedSurvivors());
  }

  @Test
  void getReportInfection_withSameId_expect_badRequest() {
    ApiResponse actualReportInfectionResult = this.survivorServiceImpl.reportInfection(123L, 123L);
    assertEquals(400, actualReportInfectionResult.getCode());
    assertEquals("Survivor cannot report themselves", actualReportInfectionResult.getDescription());
  }

  @Test
  void reportInfection_withDifferentReporterId_expectSuccess() {
    Location location = new Location();
    location.setId(123L);
    location.setLatitude("Latitude");
    location.setLongitude("Longitude");

    Survivor survivor = new Survivor();
    survivor.setAge(1);
    survivor.setGender(Gender.MALE);
    survivor.setId(123L);
    survivor.setInfected(true);
    survivor.setLocation(location);
    survivor.setName("Name");
    survivor.setReportCount(3);
    survivor.setResource(new HashSet<>());
    Optional<Survivor> ofResult = Optional.of(survivor);
    when(this.survivorRepository.findById((Long) any())).thenReturn(ofResult);
    ApiResponse actualReportInfectionResult = this.survivorServiceImpl.reportInfection(1L, 123L);
    assertEquals(400, actualReportInfectionResult.getCode());
    assertEquals("Survivor already infect", actualReportInfectionResult.getDescription());
    verify(this.survivorRepository).findById((Long) any());
    InfectedListResponse expectedListofNoneInfectedSurvivors = this.survivorServiceImpl.getListofInfectedSurvivors();
    assertEquals(expectedListofNoneInfectedSurvivors,
        this.survivorServiceImpl.getListofNoneInfectedSurvivors());
  }

  @Test
  void reportInfectionOfSurvivorAlreadyFlaggedAsInfected_expectOk() {
    Location location = new Location();
    location.setId(123L);
    location.setLatitude("Latitude");
    location.setLongitude("Longitude");
    Survivor survivor = mock(Survivor.class);
    when(survivor.isInfected()).thenReturn(false);
    when(survivor.getReportCount()).thenReturn(3);
    doNothing().when(survivor).setAge(anyInt());
    doNothing().when(survivor).setGender((Gender) any());
    doNothing().when(survivor).setId((Long) any());
    doNothing().when(survivor).setInfected(anyBoolean());
    doNothing().when(survivor).setLocation((Location) any());
    doNothing().when(survivor).setName((String) any());
    doNothing().when(survivor).setReportCount(anyInt());
    doNothing().when(survivor).setResource((java.util.Set<Resource>) any());
    survivor.setAge(1);
    survivor.setGender(Gender.MALE);
    survivor.setId(123L);
    survivor.setInfected(true);
    survivor.setLocation(location);
    survivor.setName("Name");
    survivor.setReportCount(3);
    survivor.setResource(new HashSet<>());
    Optional<Survivor> ofResult = Optional.of(survivor);

    Location location1 = new Location();
    location1.setId(123L);
    location1.setLatitude("Latitude");
    location1.setLongitude("Longitude");

    Survivor survivor1 = new Survivor();
    survivor1.setAge(1);
    survivor1.setGender(Gender.MALE);
    survivor1.setId(123L);
    survivor1.setInfected(true);
    survivor1.setLocation(location1);
    survivor1.setName("Name");
    survivor1.setReportCount(3);
    survivor1.setResource(new HashSet<>());
    when(this.survivorRepository.save((Survivor) any())).thenReturn(survivor1);
    when(this.survivorRepository.findById((Long) any())).thenReturn(ofResult);
    ApiResponse actualReportInfectionResult = this.survivorServiceImpl.reportInfection(1L, 123L);
    assertEquals(200, actualReportInfectionResult.getCode());
    assertEquals("Survivor flagged as infected", actualReportInfectionResult.getDescription());
    verify(this.survivorRepository, atLeast(1)).save((Survivor) any());
    verify(this.survivorRepository).findById((Long) any());
    verify(survivor).isInfected();
    verify(survivor).getReportCount();
    verify(survivor).setAge(anyInt());
    verify(survivor).setGender((Gender) any());
    verify(survivor).setId((Long) any());
    verify(survivor).setInfected(anyBoolean());
    verify(survivor).setLocation((Location) any());
    verify(survivor).setName((String) any());
    verify(survivor, atLeast(1)).setReportCount(anyInt());
    verify(survivor).setResource((java.util.Set<Resource>) any());
    InfectedListResponse expectedListofNoneInfectedSurvivors = this.survivorServiceImpl.getListofInfectedSurvivors();
    assertEquals(expectedListofNoneInfectedSurvivors,
        this.survivorServiceImpl.getListofNoneInfectedSurvivors());
  }

  @Test
  void reportInfectionWithNoValidId_expectOK() {
    Location location = new Location();
    location.setId(123L);
    location.setLatitude("Latitude");
    location.setLongitude("Longitude");

    Survivor survivor = new Survivor();
    survivor.setAge(1);
    survivor.setGender(Gender.MALE);
    survivor.setId(123L);
    survivor.setInfected(true);
    survivor.setLocation(location);
    survivor.setName("Name");
    survivor.setReportCount(3);
    survivor.setResource(new HashSet<>());
    when(this.survivorRepository.save((Survivor) any())).thenReturn(survivor);
    when(this.survivorRepository.findById((Long) any())).thenReturn(Optional.empty());

    Location location1 = new Location();
    location1.setId(123L);
    location1.setLatitude("Latitude");
    location1.setLongitude("Longitude");
    Survivor survivor1 = mock(Survivor.class);
    when(survivor1.isInfected()).thenReturn(true);
    when(survivor1.getReportCount()).thenReturn(3);
    doNothing().when(survivor1).setAge(anyInt());
    doNothing().when(survivor1).setGender((Gender) any());
    doNothing().when(survivor1).setId((Long) any());
    doNothing().when(survivor1).setInfected(anyBoolean());
    doNothing().when(survivor1).setLocation((Location) any());
    doNothing().when(survivor1).setName((String) any());
    doNothing().when(survivor1).setReportCount(anyInt());
    doNothing().when(survivor1).setResource((java.util.Set<Resource>) any());
    survivor1.setAge(1);
    survivor1.setGender(Gender.MALE);
    survivor1.setId(123L);
    survivor1.setInfected(true);
    survivor1.setLocation(location1);
    survivor1.setName("Name");
    survivor1.setReportCount(3);
    survivor1.setResource(new HashSet<>());
    ApiResponse actualReportInfectionResult = this.survivorServiceImpl.reportInfection(1L, 123L);
    assertEquals(404, actualReportInfectionResult.getCode());
    assertEquals("No Survivor found for the supplied id",
        actualReportInfectionResult.getDescription());
    verify(this.survivorRepository).findById((Long) any());
    verify(survivor1).setAge(anyInt());
    verify(survivor1).setGender((Gender) any());
    verify(survivor1).setId((Long) any());
    verify(survivor1).setInfected(anyBoolean());
    verify(survivor1).setLocation((Location) any());
    verify(survivor1).setName((String) any());
    verify(survivor1).setReportCount(anyInt());
    verify(survivor1).setResource((java.util.Set<Resource>) any());
    InfectedListResponse expectedListofNoneInfectedSurvivors = this.survivorServiceImpl.getListofInfectedSurvivors();
    assertEquals(expectedListofNoneInfectedSurvivors,
        this.survivorServiceImpl.getListofNoneInfectedSurvivors());
  }

  @Test
  void reportInfectionWithNotEnoughCount_shouldReturnSurvivorNotYetFlaggedAsInfected() {
    Location location = new Location();
    location.setId(123L);
    location.setLatitude("Latitude");
    location.setLongitude("Longitude");
    Survivor survivor = mock(Survivor.class);
    when(survivor.isInfected()).thenReturn(false);
    when(survivor.getReportCount()).thenReturn(3);
    doNothing().when(survivor).setAge(anyInt());
    doNothing().when(survivor).setGender((Gender) any());
    doNothing().when(survivor).setId((Long) any());
    doNothing().when(survivor).setInfected(anyBoolean());
    doNothing().when(survivor).setLocation((Location) any());
    doNothing().when(survivor).setName((String) any());
    doNothing().when(survivor).setReportCount(anyInt());
    doNothing().when(survivor).setResource((java.util.Set<Resource>) any());
    survivor.setAge(1);
    survivor.setGender(Gender.MALE);
    survivor.setId(123L);
    survivor.setInfected(true);
    survivor.setLocation(location);
    survivor.setName("Name");
    survivor.setReportCount(3);
    survivor.setResource(new HashSet<>());
    Optional<Survivor> ofResult = Optional.of(survivor);

    Location location1 = new Location();
    location1.setId(123L);
    location1.setLatitude("Latitude");
    location1.setLongitude("Longitude");
    Survivor survivor1 = mock(Survivor.class);
    when(survivor1.getReportCount()).thenReturn(1);
    doNothing().when(survivor1).setAge(anyInt());
    doNothing().when(survivor1).setGender((Gender) any());
    doNothing().when(survivor1).setId((Long) any());
    doNothing().when(survivor1).setInfected(anyBoolean());
    doNothing().when(survivor1).setLocation((Location) any());
    doNothing().when(survivor1).setName((String) any());
    doNothing().when(survivor1).setReportCount(anyInt());
    doNothing().when(survivor1).setResource((java.util.Set<Resource>) any());
    survivor1.setAge(1);
    survivor1.setGender(Gender.MALE);
    survivor1.setId(123L);
    survivor1.setInfected(true);
    survivor1.setLocation(location1);
    survivor1.setName("Name");
    survivor1.setReportCount(3);
    survivor1.setResource(new HashSet<>());
    when(this.survivorRepository.save((Survivor) any())).thenReturn(survivor1);
    when(this.survivorRepository.findById((Long) any())).thenReturn(ofResult);
    ApiResponse actualReportInfectionResult = this.survivorServiceImpl.reportInfection(1L, 123L);
    assertEquals(200, actualReportInfectionResult.getCode());
    assertEquals("Survivor is flagged but pending enough count to be deemed as infected",
        actualReportInfectionResult.getDescription());
    verify(this.survivorRepository, atLeast(1)).save((Survivor) any());
    verify(this.survivorRepository).findById((Long) any());
    verify(survivor1).getReportCount();
    verify(survivor1).setAge(anyInt());
    verify(survivor1).setGender((Gender) any());
    verify(survivor1).setId((Long) any());
    verify(survivor1).setInfected(anyBoolean());
    verify(survivor1).setLocation((Location) any());
    verify(survivor1).setName((String) any());
    verify(survivor1).setReportCount(anyInt());
    verify(survivor1).setResource((java.util.Set<Resource>) any());
    verify(survivor).isInfected();
    verify(survivor).getReportCount();
    verify(survivor).setAge(anyInt());
    verify(survivor).setGender((Gender) any());
    verify(survivor).setId((Long) any());
    verify(survivor).setInfected(anyBoolean());
    verify(survivor).setLocation((Location) any());
    verify(survivor).setName((String) any());
    verify(survivor, atLeast(1)).setReportCount(anyInt());
    verify(survivor).setResource((java.util.Set<Resource>) any());
    InfectedListResponse expectedListofNoneInfectedSurvivors = this.survivorServiceImpl.getListofInfectedSurvivors();
    assertEquals(expectedListofNoneInfectedSurvivors,
        this.survivorServiceImpl.getListofNoneInfectedSurvivors());
  }

  @Test
  void getInfectedreports_expectOk() {
    when(this.survivorRepository.countAllByInfectedTrue()).thenReturn(3);
    when(this.survivorRepository.count()).thenReturn(3L);
    ReportsResponse actualInfectedreports = this.survivorServiceImpl.getInfectedreports();
    assertEquals(200, actualInfectedreports.getCode());
    assertEquals("ReportsResponse(percentageValue=100.0)", actualInfectedreports.toString());
    assertEquals("Success", actualInfectedreports.getDescription());
    verify(this.survivorRepository).countAllByInfectedTrue();
    verify(this.survivorRepository).count();
    InfectedListResponse expectedListofNoneInfectedSurvivors = this.survivorServiceImpl.getListofInfectedSurvivors();
    assertEquals(expectedListofNoneInfectedSurvivors,
        this.survivorServiceImpl.getListofNoneInfectedSurvivors());
  }

  @Test
  void getInfectedreport_expectOK() {
    when(this.survivorRepository.countAllByInfectedTrue()).thenReturn(3);
    when(this.survivorRepository.count()).thenReturn(0L);
    ReportsResponse actualInfectedreports = this.survivorServiceImpl.getInfectedreports();
    assertEquals(200, actualInfectedreports.getCode());
    assertEquals("ReportsResponse(percentageValue=Infinity)", actualInfectedreports.toString());
    assertEquals("Success", actualInfectedreports.getDescription());
    verify(this.survivorRepository).countAllByInfectedTrue();
    verify(this.survivorRepository).count();
    InfectedListResponse expectedListofNoneInfectedSurvivors = this.survivorServiceImpl.getListofInfectedSurvivors();
    assertEquals(expectedListofNoneInfectedSurvivors,
        this.survivorServiceImpl.getListofNoneInfectedSurvivors());
  }

  @Test
  void getNonInfectedreports_expectOk() {
    when(this.survivorRepository.countAllByInfectedFalse()).thenReturn(3);
    when(this.survivorRepository.count()).thenReturn(3L);
    ApiResponse actualNonInfectedreports = this.survivorServiceImpl.getNonInfectedreports();
    assertEquals(200, actualNonInfectedreports.getCode());
    assertEquals("ReportsResponse(percentageValue=100.0)", actualNonInfectedreports.toString());
    assertEquals("Success", actualNonInfectedreports.getDescription());
    verify(this.survivorRepository).countAllByInfectedFalse();
    verify(this.survivorRepository).count();
    InfectedListResponse expectedListofInfectedSurvivors = this.survivorServiceImpl.getListofNoneInfectedSurvivors();
    assertEquals(expectedListofInfectedSurvivors,
        this.survivorServiceImpl.getListofInfectedSurvivors());
  }

  @Test
  void getNonInfectedreports_whenNoRecordsAvailable_shouldReturnNotFound() {
    when(this.survivorRepository.countAllByInfectedFalse()).thenReturn(3);
    when(this.survivorRepository.count()).thenReturn(0L);
    ApiResponse actualNonInfectedreports = this.survivorServiceImpl.getNonInfectedreports();
    assertEquals(404, actualNonInfectedreports.getCode());
    assertEquals("There are no records to compute for now. Please try again",
        actualNonInfectedreports.getDescription());
    verify(this.survivorRepository).countAllByInfectedFalse();
    verify(this.survivorRepository).count();
    InfectedListResponse expectedListofInfectedSurvivors = this.survivorServiceImpl.getListofNoneInfectedSurvivors();
    assertEquals(expectedListofInfectedSurvivors,
        this.survivorServiceImpl.getListofInfectedSurvivors());
  }

  @Test
  void testListAllRobots() throws IOException {
    RobotResponse robotResponse = new RobotResponse();
    robotResponse.setCategory("Category");
    LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
    robotResponse.setManufacturedDate(
        Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
    robotResponse.setModel("Model");
    robotResponse.setSerialNumber("42");
    when(this.robotService.fetchRobots()).thenReturn(new RobotResponse[]{robotResponse});
    assertEquals(1, this.survivorServiceImpl.listAllRobots().length);
    verify(this.robotService).fetchRobots();
    InfectedListResponse expectedListofNoneInfectedSurvivors = this.survivorServiceImpl.getListofInfectedSurvivors();
    assertEquals(expectedListofNoneInfectedSurvivors,
        this.survivorServiceImpl.getListofNoneInfectedSurvivors());
  }

  @Test
  void listAllRobots_withError_shouldThrowIOException() throws IOException {
    when(this.robotService.fetchRobots()).thenThrow(new IOException("An error occurred"));
    assertThrows(IOException.class, () -> this.survivorServiceImpl.listAllRobots());
    verify(this.robotService).fetchRobots();
  }

  @Test
  void getListofInfectedSurvivors_withNoRecords_shouldReturnEmptyList() {
    when(this.survivorRepository.findAllByInfectedTrue()).thenReturn(new ArrayList<>());
    InfectedListResponse actualListofInfectedSurvivors = this.survivorServiceImpl.getListofInfectedSurvivors();
    assertEquals("No infected survivors found.", actualListofInfectedSurvivors.getDescription());
    assertEquals(404, actualListofInfectedSurvivors.getCode());
    verify(this.survivorRepository).findAllByInfectedTrue();
    assertEquals(actualListofInfectedSurvivors,
        this.survivorServiceImpl.getListofNoneInfectedSurvivors());
  }

  @Test
  void getListofInfectedSurvivors_shouldReturnOK() {
    Location location = new Location();
    location.setId(123L);
    location.setLatitude("Latitude");
    location.setLongitude("Longitude");

    Survivor survivor = new Survivor();
    survivor.setAge(1);
    survivor.setGender(Gender.MALE);
    survivor.setId(123L);
    survivor.setInfected(true);
    survivor.setLocation(location);
    survivor.setName("Name");
    survivor.setReportCount(3);
    survivor.setResource(new HashSet<>());

    ArrayList<Survivor> survivorList = new ArrayList<>();
    survivorList.add(survivor);
    when(this.survivorRepository.findAllByInfectedTrue()).thenReturn(survivorList);
    InfectedListResponse actualListofInfectedSurvivors = this.survivorServiceImpl.getListofInfectedSurvivors();
    assertEquals(1, actualListofInfectedSurvivors.getBody().size());
    assertEquals("Success", actualListofInfectedSurvivors.getDescription());
    assertEquals(200, actualListofInfectedSurvivors.getCode());
    verify(this.survivorRepository).findAllByInfectedTrue();
    assertTrue(this.survivorServiceImpl
        .getNonInfectedreports() instanceof za.ioc.robotapocalypse.dtos.response.ReportsResponse);
  }

  @Test
  void getListofNoneInfectedSurvivor_shouldReturnEmptyList() {
    when(this.survivorRepository.findAllByInfectedFalse()).thenReturn(new ArrayList<>());
    InfectedListResponse actualListofNoneInfectedSurvivors = this.survivorServiceImpl.getListofNoneInfectedSurvivors();
    assertEquals("No infected survivors found.",
        actualListofNoneInfectedSurvivors.getDescription());
    assertEquals(404, actualListofNoneInfectedSurvivors.getCode());
    verify(this.survivorRepository).findAllByInfectedFalse();
    assertEquals(actualListofNoneInfectedSurvivors,
        this.survivorServiceImpl.getListofInfectedSurvivors());
  }

  @Test
  void getListofNoneInfectedSurvivors_shouldReturnSucces() {
    Location location = new Location();
    location.setId(123L);
    location.setLatitude("Latitude");
    location.setLongitude("Longitude");

    Survivor survivor = new Survivor();
    survivor.setAge(1);
    survivor.setGender(Gender.MALE);
    survivor.setId(123L);
    survivor.setInfected(true);
    survivor.setLocation(location);
    survivor.setName("Name");
    survivor.setReportCount(3);
    survivor.setResource(new HashSet<>());

    ArrayList<Survivor> survivorList = new ArrayList<>();
    survivorList.add(survivor);
    when(this.survivorRepository.findAllByInfectedFalse()).thenReturn(survivorList);
    InfectedListResponse actualListofNoneInfectedSurvivors = this.survivorServiceImpl.getListofNoneInfectedSurvivors();
    assertEquals(1, actualListofNoneInfectedSurvivors.getBody().size());
    assertEquals("Success", actualListofNoneInfectedSurvivors.getDescription());
    assertEquals(200, actualListofNoneInfectedSurvivors.getCode());
    verify(this.survivorRepository).findAllByInfectedFalse();
    assertTrue(this.survivorServiceImpl
        .getNonInfectedreports() instanceof za.ioc.robotapocalypse.dtos.response.ReportsResponse);
  }
}

