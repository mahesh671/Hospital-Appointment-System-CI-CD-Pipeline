package spring.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import spring.orm.contract.SpecializationDAO;
import spring.orm.model.Specialization;

@Component
public class SpecializationDAOImpl implements SpecializationDAO {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(SpecializationDAOImpl.class);

	@Transactional
	@Override
	public Specialization getSpecialization(String Id) {
		// Retrieve a specialization by its ID
		logger.info("Get Specialization by id:{}", Id);
		return entityManager.find(Specialization.class, Id);
	}

	@Override
	public List<Specialization> getAllSpecializations() {
		// Retrieve all specializations that are not marked as deleted
		logger.info("Gets all specializations");
		return entityManager.createQuery("select s from Specialization s where isDeleted = false", Specialization.class)
				.getResultList();
	}

	@Transactional
	@Override
	public void deleteSpecialization(String id) {
		// Delete a specialization by setting its 'isDeleted' flag to true
		Specialization specialization = entityManager.find(Specialization.class, id);
		logger.info("{}", specialization);
		specialization.setDeleted(true);
		entityManager.merge(specialization);
	}

	@Transactional
	@Override
	public void addSpecialization(Specialization specialization) {
		// Add a specialization to the database
		if (entityManager.find(Specialization.class, specialization.getId()) == null) {
			// If the specialization is new then add
			logger.info("If the Specialization is new:{}", specialization.getId());
			entityManager.persist(specialization);
		} else {
			// If the specialization is soft deleted then change the status
			Specialization alreadyexisted = entityManager.find(Specialization.class, specialization.getId());
			logger.info("The Specialization already exist:{}", specialization.getId());
			alreadyexisted.setDeleted(false);
			entityManager.merge(alreadyexisted);
		}
	}

	@Transactional
	@Override
	public void updateSpec(Specialization specialization) {
		// Update a specialization in the database
		logger.info("The Specialization need to be Updated:{}", specialization);
		entityManager.merge(specialization);

	}

}