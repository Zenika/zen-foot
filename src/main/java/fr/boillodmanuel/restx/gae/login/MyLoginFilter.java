package fr.boillodmanuel.restx.gae.login;

import java.io.IOException;


import javax.inject.Named;

import restx.RestxContext;
import restx.RestxFilter;
import restx.RestxHandler;
import restx.RestxHandlerMatch;
import restx.RestxRequest;
import restx.RestxRequestMatch;
import restx.RestxResponse;
import restx.RestxRoute;
import restx.RouteLifecycleListener;
import restx.StdRestxRequestMatch;
import restx.factory.Component;
import restx.http.HttpStatus;

import com.google.common.base.Optional;

import fr.boillodmanuel.restx.gae.dao.token.Token;
import fr.boillodmanuel.restx.gae.dao.token.TokenService;

@Component
@Deprecated
public class MyLoginFilter implements RestxFilter, RestxHandler,
		RouteLifecycleListener {

	private TokenService tokenService;

	public MyLoginFilter(@Named("tokenService") TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public Optional<RestxHandlerMatch> match(RestxRequest req) {
		return Optional.of(new RestxHandlerMatch(new StdRestxRequestMatch(req
				.getRestxPath()), this));
	}

	@Override
	public void onRouteMatch(RestxRoute route, RestxRequest req,
			RestxResponse resp) {

	}

	@Override
	public void onEntityInput(RestxRoute route, RestxRequest req,
			RestxResponse resp, Optional<?> input) {

	}

	@Override
	public void onEntityOutput(RestxRoute route, RestxRequest req,
			RestxResponse resp, Optional<?> input, Optional<?> output) {

	}

	@Override
	public void onBeforeWriteContent(RestxRequest req, RestxResponse resp) {

		// Checks whether the request is meant to log the user
		if (!req.getRestxUri().startsWith("/auth")) {
			this.checksToken(req, resp);
		}
	}
	
	private void checksToken(RestxRequest req, RestxResponse resp){
		Optional<String> authToken = req.getCookieValue("Auth-Token");

		boolean unauthorized = !authToken.isPresent();
		if (!unauthorized) {
			Token cookieToken = Token.getInstance(authToken.get());
			unauthorized = !this.tokenService.isValid(cookieToken);
		}

		if (unauthorized) {
			resp.setStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void onAfterWriteContent(RestxRequest req, RestxResponse resp) {

	}

	@Override
	public void handle(RestxRequestMatch match, RestxRequest req,
			RestxResponse resp, RestxContext ctx) throws IOException {

		ctx.nextHandlerMatch().handle(req, resp, ctx.withListener(this));

	}

}
