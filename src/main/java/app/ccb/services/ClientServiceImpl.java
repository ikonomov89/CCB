package app.ccb.services;

import app.ccb.domain.dtos.ClientImportDto;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ClientServiceImpl implements ClientService {

    private static final String CLIENTS_JSON_FILE_PATH = "D:\\Repositories\\BitBucket\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\clients.json";

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public String readClientsJsonFile() throws IOException {
        return this.fileUtil.readFile(CLIENTS_JSON_FILE_PATH);
    }

    @Override
    public String importClients(String clients) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        ClientImportDto[] clientImportDtos = gson.fromJson(clients, ClientImportDto[].class);

        StringBuilder sb = new StringBuilder();
        for (ClientImportDto clientImportDto : clientImportDtos) {
            if (clientImportDto.getFirstName() == null || clientImportDto.getLastName() == null) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            Client client = this.clientRepository
                    .findByFullName(String.format("%s %s", clientImportDto.getFirstName(), clientImportDto.getLastName()))
                    .orElse(null);

            if (client != null) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            client = new Client();
            client.setFullName(String.format("%s %s", clientImportDto.getFirstName(), clientImportDto.getLastName()));
            client.setAge(clientImportDto.getAge());

            Employee employee = this.employeeRepository.findByFullName(clientImportDto.getAppointedEmployee()).orElse(null);

            if (employee == null) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            employee.getClients().add(client);
            this.employeeRepository.saveAndFlush(employee);
            sb.append(String.format("Successfully imported Client - %s.", client.getFullName())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
