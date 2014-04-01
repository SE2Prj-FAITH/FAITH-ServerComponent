package ch.hsr.faith.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.hsr.faith.domain.Furniture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:META-INF/spring/repository-test-context.xml" })
public class FurnitureRepositoryTest {

	@Autowired
	private FurnitureRepository furnitureRepository;

	@Test
	public void testFurnitureCreate() {
		Furniture furniture = new Furniture();
		furniture.setName("Bett");

		furnitureRepository.save(furniture);
		assertEquals(1, furnitureRepository.findAll().size());
	}

	@Test
	public void testFurnitureRead() {
		String furnitureName = "MeinBett";

		Furniture furniture = new Furniture();
		furniture.setName(furnitureName);

		Furniture result = furnitureRepository.save(furniture);
		Furniture read = furnitureRepository.findById(result.getId());
		assertEquals(result.getName(), read.getName());
	}

	@Test
	public void testFurnitureUpdate() {
		String furnitureNameInitial = "MeinBett";
		String furnitureNameUpdated = "DeinBett";

		Furniture furniture = new Furniture();
		furniture.setName(furnitureNameInitial);

		Furniture result = furnitureRepository.save(furniture);
		result.setName(furnitureNameUpdated);
		result = furnitureRepository.save(furniture);
		Furniture read = furnitureRepository.findById(result.getId());
		
		assertEquals(result.getName(), read.getName());
	}
	
	@Test
	public void testFurnitureDelete() {
		String furnitureNameInitial = "SeinBett";

		Furniture furniture = new Furniture();
		furniture.setName(furnitureNameInitial);

		Furniture result = furnitureRepository.save(furniture);
		Furniture toRemove = new Furniture();
		toRemove.setId(result.getId());
		furnitureRepository.delete(toRemove);
		Furniture read = furnitureRepository.findById(result.getId());
		
		assertEquals(read, null);
	}


}