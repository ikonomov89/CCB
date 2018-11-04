package app.ccb.domain.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class BranchImportDto implements Serializable {
    @Expose
    private String name;

    public BranchImportDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
