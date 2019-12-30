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
public class News extends BaseEntity implements Serializable {

    private Date datePublic;
    private Date dti;
    private String shortTitle;
    private String status;

}
