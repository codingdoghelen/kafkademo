package com.example.kafkademo.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
//                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))

                .log();


        stringFlux.subscribe(System.out::println,
                (e) ->System.err.println("Exception is " + e.getMessage())
        );
    }


    @Test
    public void fluxTestElements_WithError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")));

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                // .expectError(RuntimeException.class)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTestElementsCount_WithError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")));

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTestElements_WithoutError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }
    @Test
    public void fluxTestElements_WithoutError2() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
//                .expectNext("Spring Boot")
//                .expectNext("Reactive Spring")
                .verifyComplete();
    }


    //Flux with Delay
    @Test
    public void merge() {
        Flux<String> flux1 = Flux.just("Hello", "Vikram");

        flux1 = Flux.interval(Duration.ofMillis(3000))
                .zipWith(flux1, (i, msg) -> msg);


        Flux<String> flux2 = Flux.just("reactive");
        flux2 = Flux.interval(Duration.ofMillis(2000))
                .zipWith(flux2, (i, msg) -> msg);

        Flux<String> flux3 = Flux.just("world");

        Flux.merge(flux1, flux2, flux3)
                .subscribe(System.out::println);

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void concat() {
        Flux<String> flux1 = Flux.just("Hello", "Vikram");

        flux1 = Flux.interval(Duration.ofMillis(3000))
                .zipWith(flux1, (i, msg) -> msg);


        Flux<String> flux2 = Flux.just("reactive");
        flux2 = Flux.interval(Duration.ofMillis(2000))
                .zipWith(flux2, (i, msg) -> msg);

        Flux<String> flux3 = Flux.just("world");
        Flux.concat(flux1, flux2, flux3)
                .subscribe(System.out::println);

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
