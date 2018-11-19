package app.ccb.domain.dtos;


import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "bank-account")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountImportDto implements Serializable {

    @XmlAttribute(name = "client")
    private String client;

    @XmlElement(name = "account-number")
    private String accountNumber;

    @XmlElement(name = "balance")
    private BigDecimal balance;

    public BankAccountImportDto() {
    }

    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
