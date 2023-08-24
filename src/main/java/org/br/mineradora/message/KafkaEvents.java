package org.br.mineradora.message;

import org.br.mineradora.dto.QuotationDto;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KafkaEvents {

  private final Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

  @Channel("quotation-channel")
  Emitter<QuotationDto> quotationRequestEmitter;

  public void sendNewKafkaEvent(QuotationDto dto) {

    LOG.info("-- Enviando cotação para o topico kafika --");
    quotationRequestEmitter.send(dto).toCompletableFuture().join();

  }

}
