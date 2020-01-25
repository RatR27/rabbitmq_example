package producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DirectExchangeApp {
    private static final String EXCHANGE_NAME = "direct1";

    public static void main(String[] args)throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String key = "black";
            String msg = "wts bro!";

            channel.basicPublish(EXCHANGE_NAME, key, null, msg.getBytes("UTF-8"));
            System.out.println(" [x] sent "+msg+" only for "+key);
        }
    }
}
