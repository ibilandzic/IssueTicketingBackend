package hr.codiraona.IssueTicketing.backend.utils;

import java.util.logging.Logger;

public class BackendUtils {

	public static void  handleException(Logger logger, Exception e){
		logger.warning(e.getMessage());
	}
}
