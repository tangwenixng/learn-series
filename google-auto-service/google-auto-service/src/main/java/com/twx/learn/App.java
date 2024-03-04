package com.twx.learn;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class App {
    public static void main(String[] args) {
        ServiceLoader<TranslationService> loader = ServiceLoader.load(TranslationService.class);
        long count = StreamSupport.stream(loader.spliterator(), false).count();
        System.out.println(count);//2

        ServiceLoader<TranslationService> serviceLoader = ServiceLoader.load(TranslationService.class);
        for (TranslationService service : serviceLoader) {
            String res = service.translate("message", null, null);
            System.out.println("res=>" + res);
        }
    }
}
