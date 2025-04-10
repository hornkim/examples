package org.ai.samples.helloworld.simple;

import java.util.List;

public record Invoice(String date, String total, String taxAmount, String invoiceNumber, String customerInformation, String customerID, List<String> items) {

}