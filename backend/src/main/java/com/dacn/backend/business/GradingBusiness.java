package com.dacn.backend.business;

import java.io.IOException;
import java.util.List;

public interface GradingBusiness {
	public int grading(int examId, List<String> ans) throws IOException, Exception;
}
