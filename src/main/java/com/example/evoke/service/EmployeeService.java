package com.example.evoke.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.evoke.entity.EmployeeEntity;

@Service
public class EmployeeService {
	
	Logger log = Logger.getLogger(EmployeeService.class);
	
	@Autowired
	SessionFactory factory;

	public void saveEmployee(EmployeeEntity entity) {
		Session session= null;
		
		try {
		session =factory.getCurrentSession();
		session.save(entity);
		
		}
		catch(Exception e) {
			log.debug("The excetion"+e.getMessage());
		}
		finally {
			session.flush();
		}
	}

	public List<EmployeeEntity> getEmployees() {
		Session session= null;
		try {
			session =factory.getCurrentSession();
			List<EmployeeEntity> list=session.createQuery("select * from Employee").list();
			return list;
			
			}
			catch(Exception e) {
				log.debug("The excetion"+e.getMessage());
				return null;
			}
			finally {
				session.flush();
			}
		
		
	}

	public EmployeeEntity getEmployee(Long id) {
		Session session= null;
		try {
			session =factory.getCurrentSession();
			return (EmployeeEntity) session.createQuery("SELECT * FROM Employee WHERE id=:id").setParameter("id", id).list().get(0);
			
			
			}
			catch(Exception e) {
				log.debug("The excetion"+e.getMessage());
				return null;
			}
			finally {
				session.flush();
			}
	}

	public void deleteEmployee(Long id) {
		Session session= null;
		try {
			session =factory.getCurrentSession();
		      session.createQuery("DELETE FROM Employee WHERE id=:id").setParameter("id", id).list();
			
			
			}
			catch(Exception e) {
				log.debug("The excetion"+e.getMessage());
				
			}
			finally {
				session.flush();
			}
		
	}

}
