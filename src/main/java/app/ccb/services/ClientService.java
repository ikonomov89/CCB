package app.ccb.services;

import java.io.IOException;

public interface ClientService {

    String readClientsJsonFile() throws IOException;

    String importClients(String clients);
}
