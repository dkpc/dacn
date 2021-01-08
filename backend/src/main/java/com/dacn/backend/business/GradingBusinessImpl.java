package com.dacn.backend.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacn.backend.database.entity.Exam;
import com.dacn.backend.database.repo.ExamOptionSetRepo;
import com.dacn.backend.database.repo.ExamRepo;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Service
public class GradingBusinessImpl implements GradingBusiness{
	
	private final String username = "vtien";
	private final String password = "nhucu441";
	private final String host = "192.168.50.102";
	private final int port = 22;
	
	@Autowired
	private ExamRepo examRepo;
	
	@Autowired
	private ExamOptionSetRepo examOptionSetRepo;
	
	@Override
	public int grading(int examId, List<String> ans) throws Exception{
		int mark = 0;
		int count = 0;
		Exam exam = examRepo.getOne(examId);
		
		if (exam == null) {
			throw new Exception("Exam not found");
		}
		
		List<String> eptOutputList = examOptionSetRepo.getExpectedOutput(exam.getExamId());
		List<String> eptAnswerList = examOptionSetRepo.getExpectedAnswer(exam.getExamId());
		if (exam.getOneWay() == 1) {
			for (int i = 0 ; i < eptAnswerList.size(); i++) {
				if (ans.get(i).trim().equalsIgnoreCase((eptAnswerList.get(i).trim()))) {
					count++;
				}
			}
			mark = count / eptAnswerList.size() * 10;
		} else {
			for (int i = 0 ; i < ans.size(); i++) {
				String output = sendToGradingServer(ans.get(i));
				if (eptOutputList.contains(output.trim())) {
					count++;
				}
			}
			mark = count / eptOutputList.size() * 10;
		}
		return mark;
	}

	public String sendToGradingServer(String command) throws IOException {
		String res = "";
		try{
	    	
	    	java.util.Properties config = new java.util.Properties(); 
	    	config.put("StrictHostKeyChecking", "no");
	    	JSch jsch = new JSch();
	    	Session session=jsch.getSession(username, host, port);
	    	session.setPassword(password);
	    	session.setConfig(config);
	    	session.connect();
	    	System.out.println("Connected");
	    	
	    	Channel channel=session.openChannel("exec");
	        ((ChannelExec)channel).setCommand(command);
	        channel.setInputStream(null);
	        ((ChannelExec)channel).setErrStream(System.err);
	        
	        InputStream in=channel.getInputStream();
	        channel.connect();
	        byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            res = new String(tmp, 0, i);
	          }
	          if(channel.isClosed()){
	            System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	        }
	        channel.disconnect();
	        session.disconnect();
	        System.out.println("DONE");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }

		return res;
    }   
}
