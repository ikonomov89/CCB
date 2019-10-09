package app.ccb.services;

import app.ccb.domain.dtos.xmls.CardDto;
import app.ccb.domain.dtos.xmls.CardRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import app.ccb.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class CardServiceImpl implements CardService {

    private static final String CARD_XML_FILE_PATH = "D:\\GKI\\SoftUni\\Java\\Java Projects\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\cards.xml";

    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, BankAccountRepository bankAccountRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public Boolean cardsAreImported() {
        return this.cardRepository.count() != 0;

    }

    @Override
    public String readCardsXmlFile() throws IOException {
        return this.fileUtil.readFile(CARD_XML_FILE_PATH);
    }

    @Override
    public String importCards() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        CardRootDto rootDto = this.xmlParser.parseXml(CardRootDto.class, CARD_XML_FILE_PATH);

        for (CardDto dto : rootDto.getCardDtos()) {
            Card card = this.modelMapper.map(dto, Card.class);
            BankAccount bankAccount = this.bankAccountRepository.findByAccountNumber(dto.getAccountNumber());

            if (!this.validationUtil.isValid(card) || bankAccount == null) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            card.setBankAccount(bankAccount);
            this.cardRepository.saveAndFlush(card);
            sb.append(String.format("Successfully imported Card - %s.", card.getCardNumber())).append(System.lineSeparator());

        }

        return sb.toString();
    }
}
