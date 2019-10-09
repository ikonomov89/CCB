package app.ccb.domain.dtos.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cards")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardRootDto {

    @XmlElement(name = "card")
    private List<CardDto> cardDtos;

    public CardRootDto() {
    }

    public List<CardDto> getCardDtos() {
        return cardDtos;
    }

    public void setCardDtos(List<CardDto> cardDtos) {
        this.cardDtos = cardDtos;
    }
}
