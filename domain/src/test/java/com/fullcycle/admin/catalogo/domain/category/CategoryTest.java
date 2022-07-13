package com.fullcycle.admin.catalogo.domain.category;

import org.junit.jupiter.api.Assertions;
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
}
