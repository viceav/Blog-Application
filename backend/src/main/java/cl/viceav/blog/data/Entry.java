package cl.viceav.blog.data;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Entry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  private String route;

  @NotNull
  private String fileName;

  @NotNull
  private String title;

  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
  private ZonedDateTime createdAt;

  private Boolean modified;

  private Timestamp modifiedAt;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getModified() {
    return modified;
  }

  public void setModified(Boolean modified) {
    this.modified = modified;
  }

  public Timestamp getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Timestamp modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public void setRoute(String route) {
    this.route = route;
  }

  public void setFileName(String name) {
    this.fileName = name;
  }

  public int getId() {
    return id;
  }

  public String getRoute() {
    return route;
  }

  public String getFileName() {
    return fileName;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }
}
