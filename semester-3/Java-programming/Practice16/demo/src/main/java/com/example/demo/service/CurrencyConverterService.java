package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class CurrencyConverterService {

    // Пример фиксированных курсов валют
    private static final double USD_TO_RUB = 103.95;
    private static final double EUR_TO_RUB = 108.27;
    private static final double USD_TO_EUR = 0.96;

    public double convert(double amount, String fromCurrency, String toCurrency) {
        double result = 0.0;

        if (fromCurrency.equals("USD") && toCurrency.equals("RUB")) {
            result = amount * USD_TO_RUB;
        } else if (fromCurrency.equals("EUR") && toCurrency.equals("RUB")) {
            result = amount * EUR_TO_RUB;
        } else if (fromCurrency.equals("USD") && toCurrency.equals("EUR")) {
            result = amount * USD_TO_EUR;
        } else if (fromCurrency.equals("RUB") && toCurrency.equals("USD")) {
            result = amount / USD_TO_RUB;
        } else if (fromCurrency.equals("RUB") && toCurrency.equals("EUR")) {
            result = amount / EUR_TO_RUB;
        } else if (fromCurrency.equals("EUR") && toCurrency.equals("USD")) {
            result = amount / USD_TO_EUR;
        } else {
            result = amount; // Если валюта одинаковая
        }
        return result;
    }
}
