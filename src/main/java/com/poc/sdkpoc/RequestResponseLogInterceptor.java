package com.poc.sdkpoc;

import com.rcx.client.service.webclient.filter.RequestResponseFilter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

/** @author Vinay */
@Component
@Primary
public class RequestResponseLogInterceptor implements RequestResponseFilter {
	@Override
	public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {

		System.out.println(request.method());
		return next.exchange(request);
	}
}
