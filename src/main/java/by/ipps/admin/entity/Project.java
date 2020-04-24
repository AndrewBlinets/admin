package by.ipps.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project extends BaseEntity implements Serializable {

  protected Date dti;
  protected Date datePublic;
  private List<ProjectLanguageVersion> languageVersions;
  private long mainImage;
  private int status;
  private Department department;
  private Page page;
  private String shortTitle;
  private boolean publicForCustomer;

  public void setMainImage(BaseEntity mainImage) {
    try {
      this.mainImage = mainImage.getId();
    } catch (NullPointerException e) {
    }
  }
}
