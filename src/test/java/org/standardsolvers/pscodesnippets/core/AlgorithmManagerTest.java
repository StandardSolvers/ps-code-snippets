package org.standardsolvers.pscodesnippets.core;

import org.junit.jupiter.api.*;
import org.standardsolvers.pscodesnippets.solution.Algorithm;
import org.standardsolvers.pscodesnippets.solution.algorithm.SampleAlgorithm;

import java.util.List;

public class AlgorithmManagerTest {
    AlgorithmManager algorithmManager = AlgorithmManagerImplement.getInstance();
    Class<SampleAlgorithm> sampleAlgorithmClass = SampleAlgorithm.class;

    @Test
    void testCache() {
        Assertions.assertTrue(algorithmManager.isCached(sampleAlgorithmClass.getName()));
    }
    @Test
    void testFind() {
        List<Algorithm> result1 = algorithmManager.find(sampleAlgorithmClass.getName());
        Assertions.assertTrue(!result1.isEmpty());
    }
    @Test
    void testFindByLike() {
        Assertions.assertFalse(algorithmManager.isExists(sampleAlgorithmClass.getSimpleName()));
        List<Algorithm> result1 = algorithmManager.find(sampleAlgorithmClass.getSimpleName());

        Assertions.assertTrue(!result1.isEmpty());
    }

    @Test
    public void testIsExists() {
        // Replace with a valid algorithm name
        Assertions.assertTrue(algorithmManager.isExists(sampleAlgorithmClass.getName()));
    }
    @Test
    public void testExistsCachedLike() {
        // Replace with a valid algorithm name
        Assertions.assertTrue(algorithmManager.existsCachedLike(sampleAlgorithmClass.getSimpleName()));
    }

    @Test
    public void testIsExists_AlgorithmNotExists() {
        // Replace with an invalid algorithm name
        String invalidAlgorithmName = "InvalidAlgorithm";
        Assertions.assertFalse(algorithmManager.isExists(invalidAlgorithmName));
        Assertions.assertFalse(algorithmManager.existsCachedLike(invalidAlgorithmName));
    }

    @Test
    void testFindByName_AlgorithmNotExists() {
        String invalidAlgorithmName = "InvalidAlgorithm";
        Assertions.assertFalse(algorithmManager.isExists(invalidAlgorithmName));

        List<Algorithm> result = algorithmManager.find(invalidAlgorithmName);
        // The algorithm does not exist, so the result should be Optional.empty
        Assertions.assertEquals(0, result.size());
    }
}