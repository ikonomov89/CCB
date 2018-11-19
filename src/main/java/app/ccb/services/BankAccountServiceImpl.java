package app.ccb.services;

import app.ccb.domain.dtos.BankAccountImportDto;
import app.ccb.domain.dtos.BankAccountRootImportDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final static String BANK_ACCOUNTS_XML_FILE_PATH = "D:\\Repositories\\BitBucket\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\bank-accounts.xml";

    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, ClientRepository clientRepository, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public String readBankAccountsXmlFile() throws IOException {
        return this.fileUtil.readFile(BANK_ACCOUNTS_XML_FILE_PATH);
    }

    @Override
    public String importBankAccounts() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(BankAccountRootImportDto.class);
        InputStream inputStream = new FileInputStream(new File(BANK_ACCOUNTS_XML_FILE_PATH));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Unmarshaller unmarshaller = context.createUnmarshaller();

        BankAccountRootImportDto bankAccountRootImportDto = (BankAccountRootImportDto) unmarshaller.unmarshal(bufferedReader);

        StringBuilder sb = new StringBuilder();
        for (BankAccountImportDto bankAccountImportDto : bankAccountRootImportDto.getBankAccounts()) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountNumber(bankAccountImportDto.getAccountNumber());
            bankAccount.setBalance(bankAccountImportDto.getBalance());

            Client client = this.clientRepository.findByFullName(bankAccountImportDto.getClient()).orElse(null);
            if (client == null) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            client.setBankAccount(bankAccount);
            if (this.validationUtil.isValid(bankAccount)) {
                this.clientRepository.saveAndFlush(client);
                sb.append(String.format("Successfully imported Bank Account - %s", bankAccount.getAccountNumber())).append(System.lineSeparator());
            } else {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
