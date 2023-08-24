package org.br.mineradora.scheduler;

import org.br.mineradora.message.KafkaEvents;
import org.br.mineradora.service.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.jackson.Jacksonized;

@ApplicationScoped
public class QuotationScheduler {

  private final Logger LOG = LoggerFactory.getLogger(QuotationScheduler.class);

  @Inject
  QuotationService service;

  @Transactional
  @Scheduled(every = "35s", identity = "task-job")
  void schedule() {
    LOG.info("-- Executando schedule --");
    service.getCurrencyPrice();
  }

}
