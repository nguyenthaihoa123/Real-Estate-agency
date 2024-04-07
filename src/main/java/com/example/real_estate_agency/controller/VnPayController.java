package com.example.real_estate_agency.controller;

import com.example.real_estate_agency.config.vnpay.VNPayService;
import com.example.real_estate_agency.models.payment.PackagePosting;
import com.example.real_estate_agency.models.payment.Payment;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.service.AgentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/agent/payPackage")
public class VnPayController {
    @Autowired
    private VNPayService vnPayService;
    @Autowired
    private AgentService agentService;


    @GetMapping("")
    public String home(){
        return "test/vnpay/index";
    }

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
//        System.out.println(vnpayUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model, @AuthenticationPrincipal UserDetails userDetails){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        double totalPriceDouble = Double.parseDouble(totalPrice)/100.0;
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPriceDouble);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        PackagePosting packagePosting = agentService.findPackageByPrice(totalPriceDouble);
        System.out.println(totalPriceDouble);
        Agent agent = agentService.findByEmail(userDetails.getUsername());
//        Tạo payment và luu du lieu
        Payment payment = new Payment();
        payment.setAgent(agent);
        payment.setPackagePosting(packagePosting);
        payment.setStatus("accept");
//        Luu payment
        if(paymentStatus ==1 ){
            int curNum = agent.getNumOfPost() + packagePosting.getQuantity();
            agent.setNumOfPost(curNum);
            
            agentService.savePaymet(payment);
        }
        else {
            System.out.println("can not save payment");
        }
        return paymentStatus == 1 ? "test/vnpay/ordersuccess" : "test/vnpay/orderfail";
    }
}