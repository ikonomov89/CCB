package app.ccb.services;

import app.ccb.domain.dtos.ClientDto;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ClientServiceImpl implements ClientService {

    private static final String CLIENT_JSON_FILE_PATH = "D:\\GKI\\SoftUni\\Java\\Java Projects\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\clients.json";

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository, FileUtil fileUtil, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }


    @Override
    public Boolean clientsAreImported() {
        return this.clientRepository.count() != 0;

    }

    @Override
    public String readClientsJsonFile() throws IOException {
        return this.fileUtil.readFile(CLIENT_JSON_FILE_PATH);
    }

    @Override
    public String importClients(String clients) {
        StringBuilder sb = new StringBuilder();

        ClientDto[] clientDtos = this.gson.fromJson(clients, ClientDto[].class);
        for (ClientDto dto : clientDtos) {
            Client client = this.modelMapper.map(dto, Client.class);
            client.setFullName(dto.getFirstName() + " " + dto.getLastName());

            String[] empFullName = dto.getAppointedEmployee().split(" ");
            Employee employee = this.employeeRepository.findByFirstNameAndLastName(empFullName[0], empFullName[1]);

            if (employee == null) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            Client clientInDB = this.clientRepository.findByFullName(client.getFullName());
            if (clientInDB != null) {
                sb.append("Error: Duplicate Data!").append(System.lineSeparator());
                continue;
            }

            if (!this.validationUtil.isValid(client)) {
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            client.getEmployees().add(employee);
            this.clientRepository.saveAndFlush(client);
            sb.append(String.format("Successfully imported Client - %s.", client.getFullName())).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportFamilyGuy() {
        // TODO : Implement Me
        return null;
    }
}
