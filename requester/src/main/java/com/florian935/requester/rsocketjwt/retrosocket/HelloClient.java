package com.florian935.requester.rsocketjwt.retrosocket;

import com.florian935.requester.rsocketjwt.domain.HelloRequest;
import com.florian935.requester.rsocketjwt.domain.HelloRequests;
import com.florian935.requester.rsocketjwt.domain.HelloResponse;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retrosocket.RSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.florian935.requester.rsocketjwt.utils.MimeTypeProperties.BEARER_MIMETYPE_VALUE;

@RSocketClient
public interface HelloClient {

    @MessageMapping("fire-and-forget")
    Mono<Void> fireAndForget(@Header(BEARER_MIMETYPE_VALUE) String token,
                             @Payload HelloRequest helloRequest);

    @MessageMapping("request-response.{id}")
    Mono<HelloResponse> requestResponse(@Header(BEARER_MIMETYPE_VALUE) String token,
                                        @DestinationVariable String id);

    @MessageMapping("request-stream")
    Flux<HelloResponse> requestStream(@Header(BEARER_MIMETYPE_VALUE) String token,
                                      @Payload HelloRequests helloRequests);

    @MessageMapping("channel")
    Flux<HelloResponse> requestChannel(@Header(BEARER_MIMETYPE_VALUE) String token,
                                       @Payload Flux<HelloRequest> helloRequestFlux);
}
