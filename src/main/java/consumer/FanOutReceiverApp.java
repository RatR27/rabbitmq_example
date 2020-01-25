package consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanOutReceiverApp {

    private static final String EXCHANGE_NAME = "fan1";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localHost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueName = channel.queueDeclare().getQueue();
        System.out.println(queueName);
        channel.queueBind(queueName, EXCHANGE_NAME, "");            //подвязали очередь к определенному типу exchenger'а

        System.out.println(" [*] Waiting for messages");

        DeliverCallback deliverCallback = (consumerTag, deliver ) -> {
            String msg = new String(deliver.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + msg + "'");
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

    }
}
