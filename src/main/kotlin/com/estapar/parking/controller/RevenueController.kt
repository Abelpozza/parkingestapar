import com.estapar.parking.dto.RevenueRequestDTO
import com.estapar.parking.repository.RevenueRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/revenue")
class RevenueController(private val revenueRepo: RevenueRepository) {

    @GetMapping
    fun getRevenue(@RequestBody request: RevenueRequestDTO) =
        revenueRepo.findBySectorAndDate(request.sector, LocalDate.parse(request.date))
            ?: mapOf("amount" to 0.0, "currency" to "BRL", "timestamp" to request.date)
}