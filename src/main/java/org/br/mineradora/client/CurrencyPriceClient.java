package org.br.mineradora.client;

import org.br.mineradora.dto.CurrencyPriceDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/last")
@RegisterRestClient(configKey = "cotacao-api")
@ApplicationScoped
public interface CurrencyPriceClient {

  @GET
  @Path("/{pair}")
  CurrencyPriceDto getPriceByPair(@PathParam("pair") String pair);

}
