package com.erwin.healthcare.domain

/**
 * Created by erwinalberto on 1/22/18.
 */
data class OpenPayment (
    var providerId: String,
    var providerName: String,
    var paymentAmount: Number,
    var payerId: String,
    var payerName: String
)