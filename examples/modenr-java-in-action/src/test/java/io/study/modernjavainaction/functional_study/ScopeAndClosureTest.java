package io.study.modernjavainaction.functional_study;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class ScopeAndClosureTest {

    public Supplier<String> getKrMsgSupplier(){
        String hello = "안녕하세요~~~ ";
        System.out.println("[메서드] getKrMsgSupplier() >>> 메서드 호출 ");

        Supplier<String> msgSupplier = ()->{
            String everybody = "여러분";
            return hello + everybody;
        };

        return msgSupplier;
    }

    @Test
    public void TEST_JUST_CALL(){
        Supplier<String> msgSupplier = getKrMsgSupplier();
        System.out.println(msgSupplier.get());
    }

    @Test
    public void TEST_SCOPE_EXIST_AFTER_CALL(){
        Supplier<String> msgSupplier = getKrMsgSupplier();
        System.out.println(msgSupplier.get());

        System.out.println(msgSupplier.get());
    }
}
