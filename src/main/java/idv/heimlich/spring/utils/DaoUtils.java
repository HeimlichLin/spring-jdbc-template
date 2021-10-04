package idv.heimlich.spring.utils;

import idv.heimlich.spring.dao.OrgDao;
import idv.heimlich.spring.domain.OrgDo;

import java.util.ArrayList;
import java.util.List;

public class DaoUtils {
	
	public static final String CREATE_OPERATION = "CREATE";
	public static final String READ_OPERATION = "READ";
	public static final String UPDATE_OPERATION = "UPDATE";
	public static final String DELETE_OPERATION = "DELETE";
	public static final String CLEANUP_OPERATION = "TRUNCATE";
	
	public static void printOrg(List<OrgDo> orgs, String operation) {
		System.out.println("\n****** printing orgs after " + operation + " operation ******\n");
		for (OrgDo orgDo : orgs) {
			System.out.println(orgDo);
		}
	}
	
	public static void printOrg(OrgDo orgDo ,String operation) {
		System.out.println("\n****** printing orgs after invoking " + operation + " ******\n" + orgDo);
	}
	
	public static void printSuccessFailure(String operation, boolean param) {
		if (param) {
			System.out.println("\nOperation " + operation + " successful");
		} else {
			System.out.println("\nOperation " + operation + " failed");
		}
	}
	
	public static void createSeedDate(OrgDao dao) {
		OrgDo arg1 = new OrgDo("TEST1", 2001, "AAAAA");
		OrgDo arg2 = new OrgDo("TEST2", 2002, "BBBBB");
		
		List<OrgDo> orgs = new ArrayList<OrgDo>();
		orgs.add(arg1);
		orgs.add(arg2);
		
		int createCount = 0;
		for (OrgDo orgDo : orgs) {
			boolean isCreated = dao.create(orgDo);
			if (isCreated) {
				createCount += 1;
			}
		}
		
		System.out.println("Created " + createCount + " orgs");
	}
	
	public static void printOrgCount(List<OrgDo> orgs, String operation) {
		System.out.println("\n****** currently we have " + orgs.size() + " orgs after " + operation + " operation" + " ******");
	}

}
