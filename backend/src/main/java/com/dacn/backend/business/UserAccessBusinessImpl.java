package com.dacn.backend.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dacn.backend.BO.AssignMachineBO;
import com.dacn.backend.database.entity.Machine;
import com.dacn.backend.database.entity.User;
import com.dacn.backend.database.repo.MachineRepo;
import com.dacn.backend.database.repo.UserRepo;

@Service
public class UserAccessBusinessImpl implements UserAccessBusiness{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MachineManageBusiness machineManageBusiness;
	
	@Autowired
	private MachineRepo machineRepo;
	
	@Override
	public ResponseEntity<User> addUser(String username, String password, String name, String className, int assignedMachineId,
			String role, int studentId) throws Exception {
		User user = userRepo.addUser(username, password, name, className, assignedMachineId, role, studentId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<User> findUserById(int studentId) throws Exception {
		User user = userRepo.findUserById(studentId);
		
		if (user == null) {
			throw new Exception();
		}
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@SuppressWarnings("resource")
	@Override
	public ResponseEntity<List<User>> insertStudentList(MultipartFile file, String className) throws Exception {
		List<User> lst = new ArrayList<>();
		try {
			User user = new User();
			if (file.getOriginalFilename().endsWith(".xlsx")) {
				XSSFWorkbook myWorkBook = new XSSFWorkbook (file.getInputStream());
				XSSFSheet mySheet = myWorkBook.getSheetAt(0); 
				for(int i=1; i < mySheet.getPhysicalNumberOfRows(); i++) {		
			            
			        XSSFRow row = mySheet.getRow(i);
			        Double studentId = Double.valueOf(row.getCell(1).getNumericCellValue());
			        String name = row.getCell(2).getStringCellValue();
			        user.setClassName(className);
			        user.setRole("std");
			        user.setStudentId(Double.valueOf(row.getCell(1).getNumericCellValue()).intValue());
			        user.setUsername(Integer.toString(user.getStudentId()));
					user.setPassword(Integer.toString(user.getStudentId()));
					user.setName(row.getCell(2).getStringCellValue());
					lst.add(user);
					userRepo.addUser(Integer.toString(studentId.intValue()), Integer.toString(studentId.intValue()),
							name, className, 0, "std", studentId.intValue());
				}
			} else if (file.getOriginalFilename().endsWith(".xls")) {
				HSSFWorkbook myWorkBook = new HSSFWorkbook(file.getInputStream());
				HSSFSheet mySheet = myWorkBook.getSheetAt(0);
				for (int i = 1; i < mySheet.getPhysicalNumberOfRows(); i++) {

			        HSSFRow row = mySheet.getRow(i);
			        Double studentId = Double.valueOf(row.getCell(1).getNumericCellValue());
			        String name = row.getCell(2).getStringCellValue();
			        user.setClassName(className);
			        user.setRole("std");
			        user.setStudentId(Double.valueOf(row.getCell(1).getNumericCellValue()).intValue());
			        user.setUsername(Integer.toString(user.getStudentId()));
			        user.setPassword(Integer.toString(user.getStudentId()));
			        user.setName(row.getCell(2).getStringCellValue());
			        lst.add(user);
			        userRepo.addUser(Integer.toString(studentId.intValue()), Integer.toString(studentId.intValue()),
							name, className, 0, "std", studentId.intValue());
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int count = lst.size();
		machineManageBusiness.getMachine(count);
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<User>> assignMachine(List<AssignMachineBO> lst) throws Exception{
		List<User> userList = new ArrayList<>();
		for (int i = 0; i < lst.size(); i++) {
			AssignMachineBO a = lst.get(i);
			User user = userRepo.findUserById(a.getStudentId());
			if (user == null) {
				throw new Exception("User not found");
			}
			Machine machine = machineRepo.getOne(a.getMachineId());
			if (machine == null) {
				throw new Exception("Machine not found");
			}
			user.setAssignedMachineId(a.getMachineId());
			user.setMachinePassword(a.getMachinePassword());
			user.setMachineUsername(a.getMachineUsername());
			userRepo.save(user);
			userList.add(user);
		}
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

}
