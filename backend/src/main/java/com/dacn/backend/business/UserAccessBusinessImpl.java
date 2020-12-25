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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dacn.backend.BO.AssignMachineBO;
import com.dacn.backend.database.entity.Machine;
import com.dacn.backend.database.entity.User;
import com.dacn.backend.database.repo.MachineRepo;
import com.dacn.backend.database.repo.UserRepo;
import com.dacn.backend.response.LoginRes;

@Service
public class UserAccessBusinessImpl implements UserAccessBusiness{
	
	private final double studCount = 15.0;
	private double machine;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MachineRepo machineRepo;
	
	@Autowired
	private MachineManageBusiness machineManageBusiness;
	
	@Override
	public User addUser(String username, String password, String name, String className, int assignedMachineId,
			String role, int studentId) throws Exception {
		User user = userRepo.addUser(username, password, name, className, assignedMachineId, role, studentId, "", "");
		return user;
	}

	@Override
	public User findUserByStudentId(int studentId) throws Exception {
		User user = userRepo.findUserByStudentId(studentId);
		if (user == null) {
			user = new User();
		}
		
		return user;
	}

	@SuppressWarnings("resource")
	@Override
	public List<User> insertStudentList(MultipartFile file, String className) throws Exception {
		List<Machine> lstMachine = machineRepo.getAllMachine();
		List<User> lst = new ArrayList<>();
		try {
			if (file.getOriginalFilename().endsWith(".xlsx")) {
				XSSFWorkbook myWorkBook = new XSSFWorkbook (file.getInputStream());
				XSSFSheet mySheet = myWorkBook.getSheetAt(0); 
				machine = Math.ceil(mySheet.getPhysicalNumberOfRows() / studCount);
				if (machine + lstMachine.size() <= 10) {
					int count = 1;
					int machineCount = 1;
					for(int i=0; i < mySheet.getPhysicalNumberOfRows(); i++) {		
				        XSSFRow row = mySheet.getRow(i);
				        if (row.getPhysicalNumberOfCells() != 0) {
				        	User user = new User();
					        if (count > studCount) {
					        	count = 1;
					        	machineCount++;
					        } 
					        if (count <= studCount) {
					        	if (machineCount <= machine) {
					        		user.setAssignedMachineId(machineCount);
						        	user.setMachineUsername("user" + count);
						        	user.setMachinePassword("user" + count);
						        	count++;
					        	}
					        } 
					        user.setClassName(className);
					        user.setRole("std");
					        user.setStudentId(Double.valueOf(row.getCell(1).getNumericCellValue()).intValue());
					        user.setUsername(Integer.toString(user.getStudentId()));
							user.setPassword(Integer.toString(user.getStudentId()));
							user.setName(row.getCell(2).getStringCellValue());
							lst.add(user);
							userRepo.save(user);
				        }				        
					}	
					machineManageBusiness.createMachine((int) machine);
				}
			} else if (file.getOriginalFilename().endsWith(".xls")) {
				HSSFWorkbook myWorkBook = new HSSFWorkbook(file.getInputStream());
				HSSFSheet mySheet = myWorkBook.getSheetAt(0);
				machine = Math.ceil(mySheet.getPhysicalNumberOfRows() / studCount);
				if (machine + lstMachine.size() <= 10) {
					int count = 1;
					int machineCount = 1;
					for (int i = 0; i < mySheet.getPhysicalNumberOfRows(); i++) {
				        HSSFRow row = mySheet.getRow(i);
				        if (row.getPhysicalNumberOfCells() != 0) {
					        User user = new User();
					        if (count > studCount) {
					        	count = 1;
					        	machineCount++;
					        } 
					        if (count <= studCount) {
					        	if (machineCount <= machine) {
					        		user.setAssignedMachineId(machineCount);
						        	user.setMachineUsername("user" + count);
						        	user.setMachinePassword("user" + count);
						        	count++;
					        	}
					        } 
					        user.setClassName(className);
					        user.setRole("std");
					        user.setStudentId(Double.valueOf(row.getCell(1).getNumericCellValue()).intValue());
					        user.setUsername(Integer.toString(user.getStudentId()));
					        user.setPassword(Integer.toString(user.getStudentId()));
					        user.setName(row.getCell(2).getStringCellValue());
					        lst.add(user);
					        userRepo.save(user);
				        }
				    }
					machineManageBusiness.createMachine((int) machine);
				}		
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return lst;
	}

	@Override
	public List<User> assignMachine(List<AssignMachineBO> lst) throws Exception{
		List<User> userList = new ArrayList<>();
		for (int i = 0; i < lst.size(); i++) {
			AssignMachineBO a = lst.get(i);
			User user = userRepo.findUserByStudentId(a.getStudentId());
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
		return userList;
	}
	

	@Override
	public Object login(String username, String password) throws Exception {
		User user = userRepo.getLoginInfo(username);
		
		if (user == null) {
			throw new Exception("user not found");
		}
		if (!user.getPassword().equals(password)) {
			throw new Exception("wrong password");
		}
		if (user.getRole().equals("std")) {
			
			LoginRes res = new LoginRes();
			res.setUser(user);
			Machine machine = machineRepo.getOne(user.getAssignedMachineId());
			String console = machineManageBusiness.getConsoleLink(machine.getCode());
			res.setConsole(console);
			return res;
		}
		return user;
	}

	@Override
	@Transactional
	public Object deleteByClassname(String classname) {
		userRepo.deleteAll(classname);
		return "Deleted Successfully";
	}

}
