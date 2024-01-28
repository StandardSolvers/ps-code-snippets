package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

// 인텔리제이 플러그인 연결에 대한 구체 정보를 구현합니다.
// 알고리즘 제공에 대한 구체 정보를 구현합니다.
public class AlgorithmHelper {
    static AlgorithmHelper algorithmHelper = new AlgorithmHelper();
    static Map<Class<? extends Algorithm>, Algorithm> algorithmMap = new HashMap<>();
    private AlgorithmHelper(){}


    public static AlgorithmHelper getInstance() {
        return algorithmHelper;
    }

    public <T  extends Algorithm> List<Algorithm> find(String algorithmName){
        List<Algorithm> result = new ArrayList<>();
        
        // todo
        // 모든 경우의 수 뽑기
        // 찾기   - with find(Class<T> algorithmClass)
        // 리턴

        return null;
    }

    public <T  extends Algorithm> Optional<Algorithm> find(Class<T> algorithmClass){

        if(algorithmMap.containsKey(algorithmClass)){
            return Optional.ofNullable(algorithmMap.get(algorithmClass));
        }

        try{
            Constructor<T> constructor = algorithmClass.getConstructor();
            T algorithm = constructor.newInstance();
            return Optional.ofNullable(algorithm);

        } catch (NoSuchMethodException exception){
            return Optional.empty();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception){
            return Optional.empty();

        }
    }

}
