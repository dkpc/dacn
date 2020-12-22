package com.dacn.backend.business;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.dacn.backend.database.entity.Machine;
import com.dacn.backend.database.repo.MachineRepo;
import com.dacn.backend.database.repo.UserRepo;
import com.dacn.backend.response.CreateInstanceResponse;
import com.dacn.backend.response.GetConsoleResponse;

@Service
public class MachineManageBusinessImpl implements MachineManageBusiness{
	
	private final RestTemplate restTemplate = new RestTemplate();
	private final int studentCount = 15;
	
	@Autowired
	private MachineRepo machineRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Transactional
	@Override
	public List<String> createMachine(int count){
		String token = getToken();
		String url = "http://112.137.129.214:58774/v2.1/servers";
		List<String> lst = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			String machineName = "machine" + i;
			String jsonString = "{\"server\": {"
					+ "\"name\": \""
					+ machineName
					+ "\","
					+ "\"imageRef\": \"3b6abd4c-de65-4836-adb7-a7fd12da8203\","
					+ "\"flavorRef\": \"f0f8b691-8373-414a-a2f5-1330df50e114\","
					+ "\"networks\": ["
						+ "{\"uuid\": \"61458a89-02ad-4b18-b073-49984497c8bf\"}"
					+ "]}}";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-Auth-Token", token);

			HttpEntity<String> entity = new HttpEntity<String>(jsonString,headers);
			CreateInstanceResponse response = restTemplate.postForObject(url, entity, CreateInstanceResponse.class);
			String machineId = response.getServer().getId();
			Machine machine = new Machine();
			machine.setCode(machineId);
			machine.setStudentCount(studentCount);
			machineRepo.save(machine);
			lst.add(machineId);
		}
		return lst;
	}
	
	public String getToken() {
	    String url = "http://112.137.129.214:55000/v3/auth/tokens";
		String jsonString = "{\"auth\": "
				+ "{\"identity\": "
					+ "{\"methods\": [\"password\"],"
					+ "\"password\": "
						+ "{\"user\": {\"name\": \"nvtien\","
							+ "\"domain\": {\"name\": \"Default\"},"
							+ "\"password\": \"nhucu441\"}}},"
				+ "\"scope\":"
					+ " {\"project\": "
						+ "{\"domain\": {\"id\": \"default\"},"
						+ "\"name\": \"thhdh-online\"}}}}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);;

		HttpEntity<String> entity = new HttpEntity<String>(jsonString,headers);
		String response = restTemplate.postForEntity(url, entity, String.class).getHeaders().get("X-Subject-Token").get(0);
		return response;
	}
	
	@Override
	public String getConsoleLink(String machineId) {
		String token = getToken();
		String url = "http://112.137.129.214:58774/v2.1/servers/"
				+ machineId
				+ "/action";
		String jsonString = "{\"os-getVNCConsole\": {\"type\": \"novnc\"}}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-Auth-Token", token);

		HttpEntity<String> entity = new HttpEntity<String>(jsonString,headers);
		GetConsoleResponse response = restTemplate.postForObject(url, entity, GetConsoleResponse.class);
		
		if (response == null) {
			
		}
		
		return response.getConsole().getUrl();
	}
	
	@Override
	@Transactional
	public void deleteAllMachine() {
		String token = getToken();
		List<Machine> lst = machineRepo.getAllMachine();
		lst.forEach(m -> {
			String url = "http://112.137.129.214:58774/v2.1/servers/" + m.getCode();
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.set("X-Auth-Token", token);
			
			HttpEntity<?> request = new HttpEntity<Object>(headers);
			restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
		});
		machineRepo.deleteAllMachine();
	}

	@Override
	@Transactional
	public List<String> createMachineWithClassname(String classname) {
		int machineCount = userRepo.getMachineInfo(classname);
		return createMachine(machineCount);
	}

	@Override
	public ResponseEntity<List<Machine>> getAllMachine() {
		return new ResponseEntity<>(machineRepo.getAllMachine(), HttpStatus.OK);
	}
}
