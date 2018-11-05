package app.ccb.domain.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClientImportDto implements Serializable {

    @Expose
    @SerializedName("first_name")
    private String firstName;

    @Expose
    @SerializedName("last_name")
    private String lastName;

    @Expose
    private Integer age;

    @Expose
    @SerializedName("appointed_employee")
    private String appointedEmployee;

    public ClientImportDto() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAppointedEmployee() {
        return this.appointedEmployee;
    }

    public void setAppointedEmployee(String appointedEmployee) {
        this.appointedEmployee = appointedEmployee;
    }
}