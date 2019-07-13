package io.github.mat3e;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HelloServiceTest {
    private final static String WELCOME = "Hello";
    private final static String FALLBACK_ID_WELCOME = "Hola";
    @Test
    public void test_prepareGreeting_nullName_returnsGreetingWithFallbackName() {
        //given
        var mockRepository = allwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository);

        //when
        var result = SUT.prepareGreeting(null, "-1");

        //then
        assertEquals("Hello " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_name_returnsGreetingWithName() {
        //given
        var mockRepository = allwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository);
        String name = "test";

        //when
        var result = SUT.prepareGreeting(name, "-1");

        //then
        assertEquals("Hello " + name + "!", result);
    }

    @Test
    public void test_prepareGreeting_nullLang_returnsGreetingWithFallbackIdLang() {
        //given
        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);

        //when
        var result = SUT.prepareGreeting(null, null);

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_textLang_returnsGreetingWithFallbackIdLang() {
        //given
        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);

        //when
        var result = SUT.prepareGreeting(null, "abc");

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    private LangRepository fallbackLangIdRepository() {
        return new LangRepository() {
            @Override
            Optional<Lang> findById(Long id) {
                if (id.equals(HelloService.FALLBACK_LANG.getId())) {
                    return Optional.of(new Lang(null, FALLBACK_ID_WELCOME, null));
                }
                return Optional.empty();
            }
        };
    }


    private LangRepository allwaysReturningHelloRepository () {
        return new LangRepository() {
            @Override
            Optional<Lang> findById(Long id) {
                return  Optional.of(new Lang(null, WELCOME, null));
            }
        };
    }
}