package com.dacn.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dacn.backend.database.entity.Machine;
import com.dacn.backend.response.CreateInstanceResponse;
import com.dacn.backend.response.GetConsoleResponse;

@Service
public class MachineManageBusinessImpl implements MachineManageBusiness{
	
	private final RestTemplate restTemplate = new RestTemplate();
	private final int studentCount = 15;

	@Override
	public List<Machine> getMachine(int count) {
		List<Machine> lst = new ArrayList<>();
		String token = getToken();
		List<String> machineIds = createMachine(count, token);
		machineIds.forEach(m -> {
			Machine machine = new Machine();
			String machineConsoleUrl = getConsoleLink(m, token);
			machine.setCode(machineConsoleUrl);
			machine.setStudentCount(studentCount);
			lst.add(machine);
		});
		return lst;
	}
	
	public List<String> createMachine(int count, String token){
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
	
	public String getConsoleLink(String machineId, String token) {
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
}
