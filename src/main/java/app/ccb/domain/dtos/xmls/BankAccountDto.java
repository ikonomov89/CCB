package app.ccb.domain.dtos.xmls;


import javax.xml.bind.annotation.*;
import java.math.BigDecimal;


@XmlRootElement(name = "bank-account")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountDto {

    @XmlAttribute(name = "client")
    private String clientName;

    @XmlElement(name = "account-number")
    private String accountNumber;

    @XmlElement
    private BigDecimal balance;

    public BankAccountDto() {
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
