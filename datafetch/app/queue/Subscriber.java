package queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

public class Subscriber {
    private String queueName;
    private QueueingConsumer consumer;

    public Subscriber(String queueName) throws IOException, InterruptedException {
        this.queueName = queueName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
    }

    public void receive() throws InterruptedException {
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
        }
    }

    public QueueingConsumer getConsumer() {
        return consumer;
    }
}