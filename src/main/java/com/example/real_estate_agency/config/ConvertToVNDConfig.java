package com.example.real_estate_agency.config;

import java.text.DecimalFormat;

public class ConvertToVNDConfig {
    public String formatNumberWithCommas(double value) {
        int intValue = (int) value;
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedValue = decimalFormat.format(intValue);
        return formattedValue;
    }
}
