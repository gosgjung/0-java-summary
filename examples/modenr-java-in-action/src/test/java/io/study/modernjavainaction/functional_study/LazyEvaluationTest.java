package io.study.modernjavainaction.functional_study;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LazyEvaluationTest {

    public boolean returnTrue(){
        System.out.println("[returnTrue] " + true + " 를 리턴하겠습니다.");
        return true;
    }

    public boolean returnFalse(){
        System.out.println("[returnFalse] " + false + " 를 리턴하겠습니다.");
        return false;
    }

    @Test
    public void SIMPLE_OR_CASE1(){
        if(true || returnFalse()){
            System.out.println("true");
        }
    }

    @Test
    public void SIMPLE_OR_CASE2(){
        if(returnTrue() || returnFalse()){
            System.out.println("true");
        }
    }

    public boolean or(boolean x, boolean y){
        return x || y;
    }

    @Test
    public void TEST_VALUE_BASED_CHECK(){
        if(or(returnTrue(), returnFalse())){
            System.out.println("true");;
        }
    }

    public boolean lazyOr(Supplier<Boolean> x, Supplier<Boolean> y){
        return x.get() || y.get();
    }

    @Test
    public void TEST_LAZY_OR(){
        if(lazyOr(()->returnTrue(), ()->returnFalse())){
            System.out.println("true");
        }
    }

    @Test
    public void LAZY_EVALUATION을_Stream에서_사용해보기(){
        Stream<Integer> intStream = Stream.of(1,3,5,7,9)
                .filter(x -> x > 0)
                .peek(x -> System.out.println("peeking " + x + " ..."))
                .filter(x -> x % 2 == 0);

        System.out.println("Before Collect");
        List<Integer> numbers = intStream.collect(Collectors.toList());
        System.out.println("After Collect");
    }

}
