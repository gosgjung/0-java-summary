package io.study.modernjavainaction.functional_study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ClosurePlus_CurringTest {

    @Test
    public void SIMPLE_CURRING(){
        Function<Integer, Function<Integer, Integer>> curringAdd = x -> y -> x+y;
//        Function<Integer, Function<Integer, Integer>> curringAdd = x -> (y -> x+y);

        // 1) Curring 함수 단순사용
        Function<Integer, Integer> addOneFunc = curringAdd.apply(1);
        int finalResult = addOneFunc.apply(2);

        assertThat(finalResult).isEqualTo(3);

        // 2) Curring 함수 축약형으로 사용해보기
        // 위의 구문을 더 줄여서 써보면
        assertThat(curringAdd.apply(1).apply(2))
                .isEqualTo(3);
    }
}
