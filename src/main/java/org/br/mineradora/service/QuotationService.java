package org.br.mineradora.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.br.mineradora.client.CurrencyPriceClient;
import org.br.mineradora.dto.CurrencyPriceDto;
import org.br.mineradora.dto.QuotationDto;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.message.KafkaEvents;
import org.br.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class QuotationService {

  @Inject
  @RestClient
  CurrencyPriceClient currencyPriceClient;

  @Inject
  QuotationRepository quotationRepository;

  @Inject
  KafkaEvents kafkaEvents;

  public void getCurrencyPrice() {

    CurrencyPriceDto currencyPriceDto = currencyPriceClient.getPriceByPair("USD-BRL");

    if (updateCurrentInfoPrice(currencyPriceDto)) {

      kafkaEvents.sendNewKafkaEvent(QuotationDto.builder()

          .currencyPrice(new BigDecimal(currencyPriceDto.getUSDBRL().getBid()))
          .date(LocalDateTime.now())
          .build()

      );

    }

  }

  private boolean updateCurrentInfoPrice(CurrencyPriceDto currencyInfo) {

    BigDecimal currentPrice = new BigDecimal(currencyInfo.getUSDBRL().getBid());

    boolean updatePrice = false;

    List<QuotationEntity> quotationList = quotationRepository.findAll().list();

    if (quotationList.isEmpty()) {
      saveQuotation(currencyInfo);
      updatePrice = true;
    } else {
      QuotationEntity lastDollarPrice = quotationList.get(quotationList.size() - 1);

      if (currentPrice.floatValue() > lastDollarPrice.getCurrencyPrice().floatValue()) {
        updatePrice = true;
        saveQuotation(currencyInfo);
      }

    }

    return updatePrice;
  }

  private void saveQuotation(CurrencyPriceDto currencyPriceDto) {

    QuotationEntity quotationEntity = QuotationEntity.builder()
        .date(LocalDateTime.now())
        .currencyPrice(new BigDecimal(currencyPriceDto.getUSDBRL().getBid()))
        .pctChange(currencyPriceDto.USDBRL.getPctChange())
        .pair("USD-BRL").build();

    quotationRepository.persist(quotationEntity);
  }

}
