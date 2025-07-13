package pl.dawidfendler.util

object CurrencyFlagEmoji {
    private val currencyToCountry = mapOf(
        "AED" to "AE", // United Arab Emirates
        "AFN" to "AF", // Afghanistan
        "ALL" to "AL", // Albania
        "AMD" to "AM", // Armenia
        "ANG" to "NL", // Netherlands Antilles
        "AOA" to "AO", // Angola
        "ARS" to "AR", // Argentina
        "AUD" to "AU", // Australia
        "AWG" to "AW", // Aruba
        "AZN" to "AZ", // Azerbaijan
        "BAM" to "BA", // Bosnia and Herzegovina
        "BBD" to "BB", // Barbados
        "BDT" to "BD", // Bangladesh
        "BGN" to "BG", // Bulgaria
        "BHD" to "BH", // Bahrain
        "BIF" to "BI", // Burundi
        "BMD" to "BM", // Bermuda
        "BND" to "BN", // Brunei
        "BOB" to "BO", // Bolivia
        "BRL" to "BR", // Brazil
        "BSD" to "BS", // Bahamas
        "BTN" to "BT", // Bhutan
        "BWP" to "BW", // Botswana
        "BYN" to "BY", // Belarus
        "BZD" to "BZ", // Belize
        "CAD" to "CA", // Canada
        "CDF" to "CD", // Congo (Democratic Republic)
        "CHF" to "CH", // Switzerland
        "CLP" to "CL", // Chile
        "CNY" to "CN", // China
        "COP" to "CO", // Colombia
        "CRC" to "CR", // Costa Rica
        "CUP" to "CU", // Cuba
        "CVE" to "CV", // Cape Verde
        "CZK" to "CZ", // Czech Republic
        "DJF" to "DJ", // Djibouti
        "DKK" to "DK", // Denmark
        "DOP" to "DO", // Dominican Republic
        "DZD" to "DZ", // Algeria
        "EGP" to "EG", // Egypt
        "ERN" to "ER", // Eritrea
        "ETB" to "ET", // Ethiopia
        "EUR" to "EU", // European Union
        "FJD" to "FJ", // Fiji
        "FKP" to "FK", // Falkland Islands
        "FOK" to "FO", // Faroe Islands
        "GBP" to "GB", // United Kingdom
        "GEL" to "GE", // Georgia
        "GGP" to "GG", // Guernsey
        "GHS" to "GH", // Ghana
        "GIP" to "GI", // Gibraltar
        "GMD" to "GM", // Gambia
        "GNF" to "GN", // Guinea
        "GTQ" to "GT", // Guatemala
        "GYD" to "GY", // Guyana
        "HKD" to "HK", // Hong Kong
        "HNL" to "HN", // Honduras
        "HRK" to "HR", // Croatia
        "HTG" to "HT", // Haiti
        "HUF" to "HU", // Hungary
        "IDR" to "ID", // Indonesia
        "ILS" to "IL", // Israel
        "IMP" to "IM", // Isle of Man
        "INR" to "IN", // India
        "IQD" to "IQ", // Iraq
        "IRR" to "IR", // Iran
        "ISK" to "IS", // Iceland
        "JEP" to "JE", // Jersey
        "JMD" to "JM", // Jamaica
        "JOD" to "JO", // Jordan
        "JPY" to "JP", // Japan
        "KES" to "KE", // Kenya
        "KGS" to "KG", // Kyrgyzstan
        "KHR" to "KH", // Cambodia
        "KID" to "KI", // Kiribati
        "KMF" to "KM", // Comoros
        "KRW" to "KR", // South Korea
        "KWD" to "KW", // Kuwait
        "KYD" to "KY", // Cayman Islands
        "KZT" to "KZ", // Kazakhstan
        "LAK" to "LA", // Laos
        "LBP" to "LB", // Lebanon
        "LKR" to "LK", // Sri Lanka
        "LRD" to "LR", // Liberia
        "LSL" to "LS", // Lesotho
        "LYD" to "LY", // Libya
        "MAD" to "MA", // Morocco
        "MDL" to "MD", // Moldova
        "MGA" to "MG", // Madagascar
        "MKD" to "MK", // North Macedonia
        "MMK" to "MM", // Myanmar
        "MNT" to "MN", // Mongolia
        "MOP" to "MO", // Macau
        "MRU" to "MR", // Mauritania
        "MUR" to "MU", // Mauritius
        "MVR" to "MV", // Maldives
        "MWK" to "MW", // Malawi
        "MXN" to "MX", // Mexico
        "MYR" to "MY", // Malaysia
        "MZN" to "MZ", // Mozambique
        "NAD" to "NA", // Namibia
        "NGN" to "NG", // Nigeria
        "NIO" to "NI", // Nicaragua
        "NOK" to "NO", // Norway
        "NPR" to "NP", // Nepal
        "NZD" to "NZ", // New Zealand
        "OMR" to "OM", // Oman
        "PAB" to "PA", // Panama
        "PEN" to "PE", // Peru
        "PGK" to "PG", // Papua New Guinea
        "PHP" to "PH", // Philippines
        "PKR" to "PK", // Pakistan
        "PLN" to "PL", // Poland
        "PYG" to "PY", // Paraguay
        "QAR" to "QA", // Qatar
        "RON" to "RO", // Romania
        "RSD" to "RS", // Serbia
        "RUB" to "RU", // Russia
        "RWF" to "RW", // Rwanda
        "SAR" to "SA", // Saudi Arabia
        "SBD" to "SB", // Solomon Islands
        "SCR" to "SC", // Seychelles
        "SDG" to "SD", // Sudan
        "SEK" to "SE", // Sweden
        "SGD" to "SG", // Singapore
        "SHP" to "SH", // Saint Helena
        "SLL" to "SL", // Sierra Leone
        "SOS" to "SO", // Somalia
        "SRD" to "SR", // Suriname
        "SSP" to "SS", // South Sudan
        "STN" to "ST", // São Tomé and Príncipe
        "SYP" to "SY", // Syria
        "SZL" to "SZ", // Eswatini
        "THB" to "TH", // Thailand
        "TJS" to "TJ", // Tajikistan
        "TMT" to "TM", // Turkmenistan
        "TND" to "TN", // Tunisia
        "TOP" to "TO", // Tonga
        "TRY" to "TR", // Turkey
        "TTD" to "TT", // Trinidad and Tobago
        "TVD" to "TV", // Tuvalu
        "TWD" to "TW", // Taiwan
        "TZS" to "TZ", // Tanzania
        "UAH" to "UA", // Ukraine
        "UGX" to "UG", // Uganda
        "USD" to "US", // United States
        "UYU" to "UY", // Uruguay
        "UZS" to "UZ", // Uzbekistan
        "VES" to "VE", // Venezuela
        "VND" to "VN", // Vietnam
        "VUV" to "VU", // Vanuatu
        "WST" to "WS", // Samoa
        "XAF" to "CM", // Central African CFA franc
        "XCD" to "AG", // East Caribbean dollar
        "XOF" to "BJ", // West African CFA franc
        "XPF" to "PF", // CFP franc
        "YER" to "YE", // Yemen
        "ZAR" to "ZA", // South Africa
        "ZMW" to "ZM", // Zambia
        "ZWL" to "ZW"  // Zimbabwe
    )

    fun getFlagEmojiForCurrency(currencyCode: String): String {
        val countryCode = currencyToCountry[currencyCode.uppercase()] ?: return ""
        return countryCode
            .uppercase()
            .map { char ->
                Character.toChars(0x1F1E6 + (char.code - 'A'.code)).concatToString()
            }.joinToString("")
    }
}