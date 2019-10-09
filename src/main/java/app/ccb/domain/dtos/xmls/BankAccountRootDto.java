package app.ccb.domain.dtos.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "bank-accounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountRootDto {

    @XmlElement(name = "bank-account")
    private List<BankAccountDto> dtoList;

    public BankAccountRootDto() {
    }

    public List<BankAccountDto> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<BankAccountDto> dtoList) {
        this.dtoList = dtoList;
    }
}
