package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Reservation;
import be.icc.Pid_Reservations_2024.Models.User;
import be.icc.Pid_Reservations_2024.Repositories.UserRepository;
import be.icc.Pid_Reservations_2024.Services.ReservationService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
@CrossOrigin("http://localhost:63342/")
public class StripeController {

    @Autowired
    ReservationService reservationService;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create-checkout-session")
    public RedirectView createCheckoutSession(@RequestParam String pictureShow, @RequestParam String nameShow, @RequestParam String dateShow, @RequestParam("priceShow") String priceShow, @RequestParam("quantityShow") String quantityShow) throws StripeException, StripeException {

        System.out.println(pictureShow);
        // Convert price show from String to long
        double priceDouble = Double.parseDouble(priceShow);
        long price = (long) priceDouble;

        // Convert quantity place for a show from String to long
        double quantityDouble = Double.parseDouble(quantityShow);
        long quantity = (long) quantityDouble;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("EUR")
                                                .setUnitAmount(price * 100)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                //.addImage("http://bobleponge.fr/photos/humour-bob-eponge/Linux-patrick.jpg")
                                                                .addImage(pictureShow)
                                                                .setName(nameShow)
                                                                .setDescription("Le " + dateShow)
                                                                .build()
                                                )
                                                .build()
                                ).setQuantity(quantity)
                                .build()
                ).build();

        Session session = Session.create(params);

        Map<String, Object> result = new HashMap<>();
        result.put("sessionId", session.getId());

        String url = session.getUrl();

        return new RedirectView(url);
    }

    /**
     * This method handles when a payment is successful and creates a reservation for the logged-in user.
     *
     * The method is mapped to the "/success" URL. It gets the current user's information, creates a new
     * reservation with a "Confirmed" status, and saves it in the database.
     *
     * @return A message saying "payment successful" after the reservation is saved.
     *
     */
    @GetMapping("/success")
    public String success() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userID = userRepository.findByLogin(username);

        Reservation reservation = new Reservation();
        reservation.setBookingDate(LocalDateTime.now());
        reservation.setStatus("Confirmed");
        reservation.setUser(userID);
        reservationService.save(reservation);
        return "Stripe/success";
    }

    @GetMapping("/cancel")
    public String cancel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userID = userRepository.findByLogin(username);

        Reservation reservation = new Reservation();
        reservation.setBookingDate(LocalDateTime.now());
        reservation.setStatus("Canceled");
        reservation.setUser(userID);
        reservationService.save(reservation);
        return "Stripe/cancel";
    }
}
