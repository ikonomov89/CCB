package app.ccb.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cards")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardRootImportDto {

    @XmlElement(name = "card")
    private List<CardImportDto> cards;

    public CardRootImportDto() {
    }

    public List<CardImportDto> getCards() {
        return this.cards;
    }

    public void setCards(List<CardImportDto> cards) {
        this.cards = cards;
    }
}
