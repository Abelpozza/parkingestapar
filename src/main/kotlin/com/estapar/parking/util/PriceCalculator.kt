
package com.estapar.parking.util

fun calculatePrice(basePrice: Double, occupancyRate: Double): Double {
    return when {
        occupancyRate < 0.25 -> basePrice * 0.9
        occupancyRate < 0.5  -> basePrice
        occupancyRate < 0.75 -> basePrice * 1.1
        else                  -> basePrice * 1.25
    }
}