package spring.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import spring.orm.contract.SpecializationDAO;
import spring.orm.model.Specialization;

@Component
public class SpecializationDAOImpl implements SpecializationDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public Specialization getSpecialization(String Id) {
		// Retrieve a specialization by its ID
		return entityManager.find(Specialization.class, Id);
	}

	@Override
	public List<Specialization> getAllSpecializations() {
		// Retrieve all specializations that are not marked as deleted
		return entityManager.createQuery("select s from Specialization s where isDeleted = false", Specialization.class)
				.getResultList();
	}

	@Transactional
	@Override
	public void deleteSpecialization(String id) {
		// Delete a specialization by setting its 'isDeleted' flag to true
		Specialization specialization = entityManager.find(Specialization.class, id);
		specialization.setDeleted(true);
		entityManager.merge(specialization);
	}

	@Transactional
	@Override
	public void addSpecialization(Specialization specialization) {
		// Add a specialization to the database
		if (entityManager.find(Specialization.class, specialization.getId()) == null) {
			// If the specialization is new then add
			entityManager.persist(specialization);
		} else {
			// If the specialization is soft deleted then change the status
			Specialization alreadyexisted = entityManager.find(Specialization.class, specialization.getId());
			alreadyexisted.setDeleted(false);
			entityManager.merge(alreadyexisted);
		}
	}

	@Transactional
	@Override
	public void updateSpec(Specialization specialization) {
		// Update a specialization in the database
		entityManager.merge(specialization);

	}

}