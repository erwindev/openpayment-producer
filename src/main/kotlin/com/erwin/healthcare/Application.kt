package com.erwin.healthcare

import com.erwin.healthcare.domain.OpenPayment
import com.erwin.healthcare.processor.OpenPaymentProducer
import com.google.gson.Gson
import com.opencsv.CSVReaderBuilder
import com.opencsv.enums.CSVReaderNullFieldIndicator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.FileReader

/**
 * Created by erwinalberto on 1/22/18.
 */
@SpringBootApplication
class Application: CommandLineRunner {

    @Value("\${erwindev.payment_file_name}") lateinit var paymentFileName: String

    @Autowired
    lateinit var openPaymentProducer: OpenPaymentProducer

    override fun run(args: Array<String>){
        val csvReader = CSVReaderBuilder(FileReader(paymentFileName))
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                // Skip the header
                .withSkipLines(1)
                .build()

        var gson: Gson = Gson()

        csvReader.forEachIndexed{ i, line ->
            line[5]?.let {
                var openPayment: OpenPayment = OpenPayment(
                        providerId = line[5],
                        providerName = line[6] + " " + line[7] + " " + line[8],
                        paymentAmount = line[30].toDouble(),
                        payerId = line[26],
                        payerName = line[27])

                var data: String = gson.toJson(openPayment)

                println(data)

                openPaymentProducer.runProducer(i, data)
            }

        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}