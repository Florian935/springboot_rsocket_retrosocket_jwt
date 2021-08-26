package com.florian935.requester.rsocketjwt.service.implementation;

import com.florian935.requester.rsocketjwt.domain.HelloRequest;
import com.florian935.requester.rsocketjwt.domain.HelloRequests;
import com.florian935.requester.rsocketjwt.domain.HelloResponse;
import com.florian935.requester.rsocketjwt.retrosocket.HelloClient;
import com.florian935.requester.rsocketjwt.service.HelloService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class HelloServiceImpl implements HelloService {

    RSocketRequester rSocketRequester;
    HelloClient helloClient;

    @Override
    public Mono<Void> fireAndForget(String token, String id) {

        return helloClient.fireAndForget(token, new HelloRequest(id));
    }

    @Override
    public Mono<HelloResponse> requestResponse(String token, HelloRequest helloRequest) {

        return helloClient.requestResponse(token, helloRequest.getId());
    }

    @Override
    public Flux<HelloResponse> requestStream(String token, HelloRequests helloRequests) {

        return helloClient.requestStream(token, helloRequests);
    }

    @Override
    public Flux<HelloResponse> requestChannel(String token) {

        final Flux<HelloRequest> requestFlux = getHelloRequests();

        return helloClient.requestChannel(token, requestFlux);
    }

    Flux<HelloRequest> getHelloRequests() {

        return Flux.just(
                new HelloRequest("0"),
                new HelloRequest("1"),
                new HelloRequest("2"),
                new HelloRequest("3")
        );
    }
}
