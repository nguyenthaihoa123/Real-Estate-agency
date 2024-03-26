package com.example.real_estate_agency.api;


import com.example.real_estate_agency.DTO.FeedBackDTO;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.FeedBack;
import com.example.real_estate_agency.service.AgentService;
import com.example.real_estate_agency.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserAPI {

    @Autowired
    private AgentService agentService;

    @Autowired
    private ClientService clientService;
    @GetMapping("/agents")
    public ResponseEntity<List<Agent>> getAllAgents() {
        try {
            List<Agent> agents = agentService.getAll();
            if (agents != null && !agents.isEmpty()) {
                return new ResponseEntity<>(agents, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/agents")
    public ResponseEntity<Agent> addAgent(@RequestBody Agent agent) {
        try {
            Agent savedAgent = agentService.save(agent);
            System.out.println(agent.getEmail());
            if (savedAgent != null) {
                return new ResponseEntity<>(savedAgent, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/feedback/clients/{clientId}")
    public ResponseEntity<FeedBack> addFeedBack(@PathVariable Long clientId, @RequestBody FeedBack feedBack) {
        FeedBack savedFeedBack = clientService.addFeedBack(clientId, feedBack);
        return new ResponseEntity<>(savedFeedBack, HttpStatus.CREATED);
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<FeedBackDTO>> getAllFeedBack() {
        List<FeedBackDTO> feedbackList = clientService.getAllFeedBack();
        if (feedbackList != null && !feedbackList.isEmpty()) {
            return new ResponseEntity<>(feedbackList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}