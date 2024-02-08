package org.standardsolvers.pscodesnippets.core;

import org.junit.jupiter.api.*;
import org.standardsolvers.pscodesnippets.solution.Ps;
import org.standardsolvers.pscodesnippets.solution.ps.SampleAlgorithm;

import java.util.List;

public class PsManagerTest {
    PsManager psManager = PsManagerImplement.getInstance();
    Class<SampleAlgorithm> samplePsClass = SampleAlgorithm.class;

    @Test
    void testCache() {
        Assertions.assertTrue(psManager.isCached(samplePsClass.getName()));
    }
    @Test
    void testFind() {
        List<Ps> result1 = psManager.find(samplePsClass.getName());
        Assertions.assertTrue(!result1.isEmpty());
    }
    @Test
    void testFindByLike() {
        Assertions.assertFalse(psManager.isExists(samplePsClass.getSimpleName()));
        List<Ps> result1 = psManager.find(samplePsClass.getSimpleName());

        Assertions.assertTrue(!result1.isEmpty());
    }

    @Test
    public void testIsExists() {
        // Replace with a valid ps name
        Assertions.assertTrue(psManager.isExists(samplePsClass.getName()));
    }
    @Test
    public void testExistsCachedLike() {
        // Replace with a valid ps name
        Assertions.assertTrue(psManager.existsCachedLike(samplePsClass.getSimpleName()));
    }

    @Test
    public void testIsExists_PsNotExists() {
        // Replace with an invalid ps name
        String invalidPsName = "InvalidPs";
        Assertions.assertFalse(psManager.isExists(invalidPsName));
        Assertions.assertFalse(psManager.existsCachedLike(invalidPsName));
    }

    @Test
    void testFindByName_PsNotExists() {
        String invalidPsName = "InvalidPs";
        Assertions.assertFalse(psManager.isExists(invalidPsName));

        List<Ps> result = psManager.find(invalidPsName);
        // The ps does not exist, so the result should be Optional.empty
        Assertions.assertEquals(0, result.size());
    }
}