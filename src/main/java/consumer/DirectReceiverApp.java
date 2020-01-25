package consumer;

import com.rabbitmq.client.*;

public class DirectReceiverApp {
    private static final String EXCHANGE_NAME = "direct1";

    public static void main(String[] args)throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        String directKey = "orange";

        channel.queueBind(queueName, EXCHANGE_NAME, directKey);
        System.out.println(" [*] Waiting for messages");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + msg + "'");
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
