package app.ccb.services;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface BankAccountService {

    String readBankAccountsXmlFile() throws IOException;

    String importBankAccounts() throws JAXBException, FileNotFoundException;
}
