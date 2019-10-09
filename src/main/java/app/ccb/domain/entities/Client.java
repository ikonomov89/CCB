package app.ccb.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client extends BaseEntity {

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;


    @Column(name = "age")
    private Integer age;

    @ManyToMany
    @JoinTable(name = "clients_employees", joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;

    //    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    @OneToOne(mappedBy = "client")
    private BankAccount bankAccount;

    public Client() {
        this.employees = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
