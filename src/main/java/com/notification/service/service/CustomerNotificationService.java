package com.notification.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.notification.service.model.Customer;

@Service
public class CustomerNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerNotificationService.class);
	/*
	 * @Value("${kafka.topic.customer-topic}") private String customerTopic;
	 */
    
    
    @KafkaListener(topics = "customer-topic", groupId = "notification-service-group")
    public void consumeCustomer(Customer customer) {
    	System.out.println("-------------ConsumerFactory---------------");
        logger.info("Consumer received message. Customer: {}", customer);
        if (customer != null) {
            logger.info("Processing customer: {}", customer);
            sendWelcomeNotification(customer);
            // Your processing logic
        } else {
            logger.warn("Received a null customer due to deserialization error.");
        }
    }

    private void sendWelcomeNotification(Customer customer) {
        // In a real application, you would integrate with an email or SMS service here
        logger.info("Sending welcome notification to hostel customer: Name={}, Email={}, Phone={}",
                    customer.getName(), customer.getEmail(), customer.getPhone());
        // Add your notification sending logic here
    }
}