package app.ccb.services;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface CardService {

    String readCardsXmlFile() throws IOException;

    String importCards() throws JAXBException, FileNotFoundException;
}
