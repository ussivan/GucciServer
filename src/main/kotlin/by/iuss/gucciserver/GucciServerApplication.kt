package by.iuss.gucciserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GucciServerApplication

fun main(args: Array<String>) {
    runApplication<GucciServerApplication>(*args)
}
