package idv.heimlich.spring;

import java.util.List;

import idv.heimlich.spring.dao.OrgDao;
import idv.heimlich.spring.domain.OrgDo;
import idv.heimlich.spring.utils.DaoUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcApp {
	
	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("bean-cp.xml");
		OrgDao dao = (OrgDao) ctx.getBean("orgDao");
		
		DaoUtils.createSeedDate(dao);
		
		List<OrgDo> orgs = dao.getAllOrgs();
		DaoUtils.printOrg(orgs, DaoUtils.READ_OPERATION);
		
		OrgDo orgDo = new OrgDo("TEST3", 2003, "CCCCC"); 
		boolean isCreated = dao.create(orgDo);
		DaoUtils.printSuccessFailure(DaoUtils.CREATE_OPERATION, isCreated);
		DaoUtils.printOrg(dao.getAllOrgs(), DaoUtils.READ_OPERATION);
		
		OrgDo orgDo2 = dao.getOrg(1);
		DaoUtils.printOrg(orgDo2, "getOrg");
		
		OrgDo orgDo3 = dao.getOrg(2);
		orgDo3.setCode("ZZZZZ");
		boolean isUpdated = dao.update(orgDo3);
		DaoUtils.printSuccessFailure(DaoUtils.UPDATE_OPERATION, isUpdated);
		DaoUtils.printOrg(dao.getOrg(2), DaoUtils.UPDATE_OPERATION);
		
		boolean isDeleted = dao.delete(dao.getOrg(3));
		DaoUtils.printSuccessFailure(DaoUtils.DELETE_OPERATION, isDeleted);
		DaoUtils.printOrg(dao.getAllOrgs(), DaoUtils.DELETE_OPERATION);
		
		dao.cleanup();
		DaoUtils.printOrgCount(dao.getAllOrgs(), DaoUtils.CLEANUP_OPERATION);
		
//		for (OrgDo orgDo : orgs) {
//			System.out.println(orgDo);
//		}
		
		((ClassPathXmlApplicationContext) ctx).close();		
	}

}
