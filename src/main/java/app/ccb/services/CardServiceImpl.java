package app.ccb.services;

import app.ccb.domain.dtos.CardImportDto;
import app.ccb.domain.dtos.CardRootImportDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

@Service
public class CardServiceImpl implements CardService {

    private final static String CARDS_XML_FILE_PATH = "D:\\Repositories\\BitBucket\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\cards.xml";

    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, BankAccountRepository bankAccountRepository, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean cardsAreImported() {
        return this.cardRepository.count() != 0;
    }

    @Override
    public String readCardsXmlFile() throws IOException {
        return this.fileUtil.readFile(CARDS_XML_FILE_PATH);
    }

    @Override
    public String importCards() throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CardRootImportDto.class);
        InputStream inputStream = new FileInputStream(new File(CARDS_XML_FILE_PATH));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        CardRootImportDto cardRootImportDto = (CardRootImportDto) unmarshaller.unmarshal(bufferedReader);

        StringBuilder sb = new StringBuilder();
        for (CardImportDto cardImportDto : cardRootImportDto.getCards()) {
            Card card = new Card();
            card.setCardNumber(cardImportDto.getCardNumber());
            card.setCardStatus(cardImportDto.getCardStatus());

            BankAccount bankAccount = this.bankAccountRepository.findByAccountNumber(cardImportDto.getBankAccountNumber()).orElse(null);
            if (bankAccount == null) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            card.setBankAccount(bankAccount);
            if (validationUtil.isValid(card)) {
                this.cardRepository.saveAndFlush(card);
                sb.append(String.format("Successfully imported Card - %s", card.getCardNumber())).append(System.lineSeparator());
            } else {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
