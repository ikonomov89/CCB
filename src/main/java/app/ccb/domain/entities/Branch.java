package app.ccb.domain.entities;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "branches")
public class Branch extends BaseEntity {

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;


    public Branch() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
