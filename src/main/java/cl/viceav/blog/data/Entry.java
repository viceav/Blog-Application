package cl.viceav.blog.data;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Entry {
  @Id
  private int id;

  @NotNull
  private String route;

  @NotNull
  private String name;

  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp created_at;

  public void setRoute(String route) {
    this.route = route;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getRoute() {
    return route;
  }

  public String getName() {
    return name;
  }

  public Timestamp getCreated_at() {
    return created_at;
  }
}
