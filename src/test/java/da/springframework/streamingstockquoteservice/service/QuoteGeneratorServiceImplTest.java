package da.springframework.streamingstockquoteservice.service;

import da.springframework.streamingstockquoteservice.model.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

class QuoteGeneratorServiceImplTest {

    QuoteGeneratorService quoteGeneratorService;

    @BeforeEach
    void setUp() {
        quoteGeneratorService = new QuoteGeneratorServiceImpl();
    }

    @Test
    void fetchQuoteStream() throws InterruptedException {
        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100l));

        Consumer<Quote> quoteConsumer = System.out::println;

        Consumer<Throwable> throwableConsumer = throwable -> System.out.println(throwable.getMessage());

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable done = () -> countDownLatch.countDown();

        quoteFlux.take(30).subscribe(quoteConsumer, throwableConsumer, done);

        countDownLatch.await();
    }
}