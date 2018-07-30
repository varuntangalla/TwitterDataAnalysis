package queue;

import com.rabbitmq.client.*;
import helper.Serializer;
import helper.TweetWrapper;

import java.io.IOException;

public class Publisher {

    private String queueName;
    private Channel channel;
    private Connection connection;

    public Publisher(String queueName) throws IOException {
        this.queueName = queueName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
    }

    public void pushToQueue(TweetWrapper tweetWrapper) throws IOException {
        channel.basicPublish("", queueName, null, Serializer.serialize(tweetWrapper));
    }

    public void publish(String text) throws IOException {
        channel.basicPublish("", queueName, null, text.getBytes());
    }

    public void closeQueue() throws IOException {
        channel.close();
        connection.close();
    }
}