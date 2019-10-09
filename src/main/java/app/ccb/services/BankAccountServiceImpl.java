package app.ccb.services;

import app.ccb.domain.dtos.xmls.BankAccountDto;
import app.ccb.domain.dtos.xmls.BankAccountRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import app.ccb.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private static final String BANK_ACCOUNT_XML_FILE_PATH = "D:\\GKI\\SoftUni\\Java\\Java Projects\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\bank-accounts.xml";

    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, ClientRepository clientRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public Boolean bankAccountsAreImported() {
        return this.bankAccountRepository.count() != 0;
    }

    @Override
    public String readBankAccountsXmlFile() throws IOException {
        return this.fileUtil.readFile(BANK_ACCOUNT_XML_FILE_PATH);
    }

    @Override
    public String importBankAccounts() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        BankAccountRootDto rootDto = this.xmlParser.parseXml(BankAccountRootDto.class, BANK_ACCOUNT_XML_FILE_PATH);

        for (BankAccountDto dto : rootDto.getDtoList()) {
            BankAccount bankAccount = this.modelMapper.map(dto, BankAccount.class);
            Client client = this.clientRepository.findByFullName(dto.getClientName());

            if (!this.validationUtil.isValid(bankAccount) || client == null) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            bankAccount.setClient(client);

            this.bankAccountRepository.saveAndFlush(bankAccount);
            sb.append(String.format("Successfully imported Bank Account - %s.", bankAccount.getAccountNumber())).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
