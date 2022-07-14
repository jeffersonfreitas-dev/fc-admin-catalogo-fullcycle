package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void testNewCategory(){
       final var expName = "Movies";
       final var expDescription = "Some description movie";
       final var expIsActive = true;

       final var actCategory = Category.newCategory(expName, expDescription, expIsActive );

       assertNotNull(actCategory);
       assertNotNull(actCategory.getId());
       assertEquals(expName, actCategory.getName());
       assertEquals(expDescription, actCategory.getDescription());
       assertEquals(expIsActive, actCategory.isActive());
       assertNotNull(actCategory.getCreatedAt());
       assertNotNull(actCategory.getUpdatedAt());
       assertNull(actCategory.getDeletedAt());
    }

    @Test
    public void throwErrorWhenNameIsNull(){
        final String expName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expDescription = "Some description movie";
        final var expIsActive = true;

        final var actCategory = Category.newCategory(expName, expDescription, expIsActive );

        final var actException = assertThrows(DomainException.class, () -> actCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount ,actException.getErrors().size());
        assertEquals(expectedErrorMessage ,actException.getErrors().get(0).message());
    }

    @Test
    public void throwErrorWhenNameIsEmpty(){
        final String expName = "";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expDescription = "Some description movie";
        final var expIsActive = true;

        final var actCategory = Category.newCategory(expName, expDescription, expIsActive );

        final var actException = assertThrows(DomainException.class, () -> actCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount ,actException.getErrors().size());
        assertEquals(expectedErrorMessage ,actException.getErrors().get(0).message());
    }


    @Test
    public void throwErrorWhenNameIsLessThan3(){
        final String expName = "ab ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expDescription = "Some description movie";
        final var expIsActive = true;

        final var actCategory = Category.newCategory(expName, expDescription, expIsActive );

        final var actException = assertThrows(DomainException.class, () -> actCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount ,actException.getErrors().size());
        assertEquals(expectedErrorMessage ,actException.getErrors().get(0).message());
    }

    @Test
    public void throwErrorWhenNameIsMoreThan255(){
        final String expName = """
                O incentivo ao avanço tecnológico, assim como o julgamento imparcial das eventualidades oferece 
                uma interessante oportunidade para verificação das condições financeiras e administrativas 
                exigidas
                O incentivo ao avanço tecnológico, assim como o julgamento imparcial das eventualidades oferece 
                uma interessante oportunidade para verificação das condições financeiras e administrativas 
                exigidas
                O incentivo ao avanço tecnológico, assim como o julgamento imparcial das eventualidades oferece 
                uma interessante oportunidade para verificação das condições financeiras e administrativas 
                exigidas
                """;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expDescription = "Some description movie";
        final var expIsActive = true;

        final var actCategory = Category.newCategory(expName, expDescription, expIsActive );

        final var actException = assertThrows(DomainException.class, () -> actCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount ,actException.getErrors().size());
        assertEquals(expectedErrorMessage ,actException.getErrors().get(0).message());
    }

    @Test
    public void testNewCategoryWithEmptyDescription(){
        final var expName = "Movies";
        final var expDescription = " ";
        final var expIsActive = true;

        final var actCategory = Category.newCategory(expName, expDescription, expIsActive );

        assertDoesNotThrow(() -> actCategory.validate(new ThrowsValidationHandler()));

        assertNotNull(actCategory);
        assertNotNull(actCategory.getId());
        assertEquals(expName, actCategory.getName());
        assertEquals(expDescription, actCategory.getDescription());
        assertEquals(expIsActive, actCategory.isActive());
        assertNotNull(actCategory.getCreatedAt());
        assertNotNull(actCategory.getUpdatedAt());
        assertNull(actCategory.getDeletedAt());
    }

    @Test
    public void testNewCategoryWithIsActiveFalse(){
        final var expName = "Movies";
        final var expDescription = " ";
        final var expIsActive = false;

        final var actCategory = Category.newCategory(expName, expDescription, expIsActive );

        assertDoesNotThrow(() -> actCategory.validate(new ThrowsValidationHandler()));

        assertNotNull(actCategory);
        assertNotNull(actCategory.getId());
        assertEquals(expName, actCategory.getName());
        assertEquals(expDescription, actCategory.getDescription());
        assertEquals(expIsActive, actCategory.isActive());
        assertNotNull(actCategory.getCreatedAt());
        assertNotNull(actCategory.getUpdatedAt());
        assertNotNull(actCategory.getDeletedAt());
    }


    @Test
    public void deActiveProductActive(){
        final var expName = "Movies";
        final var expDescription = " ";
        final var expIsActive = false;

        final var actCategory = Category.newCategory(expName, expDescription, true );

        assertDoesNotThrow(() -> actCategory.validate(new ThrowsValidationHandler()));

        final var updateAt = actCategory.getUpdatedAt();

        assertTrue(actCategory.isActive());
        assertNull(actCategory.getDeletedAt());

        final var deactiveCategory = actCategory.deactivate();

        assertDoesNotThrow(() -> actCategory.validate(new ThrowsValidationHandler()));

        assertEquals(actCategory.getId(), deactiveCategory.getId());
        assertEquals(expName, deactiveCategory.getName());
        assertEquals(expDescription, deactiveCategory.getDescription());
        assertEquals(expIsActive, deactiveCategory.isActive());
        assertNotNull(deactiveCategory.getCreatedAt());
        assertTrue(deactiveCategory.getUpdatedAt().isAfter(updateAt));
        assertNotNull(deactiveCategory.getDeletedAt());
    }


    @Test
    public void activeProductDeactive(){
        final var expName = "Movies";
        final var expDescription = " ";
        final var expIsActive = true;

        final var actCategory = Category.newCategory(expName, expDescription, false );

        assertDoesNotThrow(() -> actCategory.validate(new ThrowsValidationHandler()));

        final var updateAt = actCategory.getUpdatedAt();

        assertFalse(actCategory.isActive());
        assertNotNull(actCategory.getDeletedAt());

        final var deactiveCategory = actCategory.activate();

        assertDoesNotThrow(() -> actCategory.validate(new ThrowsValidationHandler()));

        assertEquals(actCategory.getId(), deactiveCategory.getId());
        assertEquals(expName, deactiveCategory.getName());
        assertEquals(expDescription, deactiveCategory.getDescription());
        assertEquals(expIsActive, deactiveCategory.isActive());
        assertNotNull(deactiveCategory.getCreatedAt());
        assertTrue(deactiveCategory.getUpdatedAt().isAfter(updateAt));
        assertNull(deactiveCategory.getDeletedAt());
    }


    @Test
    public void updateProductSuccessfuly(){
        final var expName = "Movies";
        final var expDescription = "Description";
        final var expIsActive = true;

        final var actCategory = Category.newCategory("Filme", "Desc", expIsActive );

        assertDoesNotThrow(() -> actCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = actCategory.getCreatedAt();
        final var updateAt = actCategory.getUpdatedAt();

        final var updatedCategory = actCategory.update(expName, expDescription, expIsActive);

        assertDoesNotThrow(() -> actCategory.validate(new ThrowsValidationHandler()));

        assertEquals(actCategory.getId(), updatedCategory.getId());
        assertEquals(expName, updatedCategory.getName());
        assertEquals(expDescription, updatedCategory.getDescription());
        assertEquals(expIsActive, updatedCategory.isActive());
        assertEquals(createdAt, updatedCategory.getCreatedAt());
        assertTrue(updatedCategory.getUpdatedAt().isAfter(updateAt));
        assertNull(updatedCategory.getDeletedAt());
    }

}
