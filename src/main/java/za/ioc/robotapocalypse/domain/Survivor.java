package za.ioc.robotapocalypse.domain;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import za.ioc.robotapocalypse.enums.Gender;
import za.ioc.robotapocalypse.enums.Resource;


/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Entity
@Table(name="survivor")
public class Survivor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private int age;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Location location;

  private boolean infected;

  private int reportCount;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "survivor_resource", joinColumns = @JoinColumn(name = "survivor_id"))
  private Set<Resource> resource;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public boolean isInfected() {
    return infected;
  }

  public void setInfected(boolean infected) {
    this.infected = infected;
  }

  public int getReportCount() {
    return reportCount;
  }

  public void setReportCount(int reportCount) {
    this.reportCount = reportCount;
  }

  public Set<Resource> getResource() {
    return resource;
  }

  public void setResource(Set<Resource> resource) {
    this.resource = resource;
  }
}
