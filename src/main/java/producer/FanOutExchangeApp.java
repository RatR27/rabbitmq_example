package producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class FanOutExchangeApp {

    private static final String EXCHANGE_NAME = "fan1";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localHost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            String msg = "This is fanout_exchanger";

            channel.basicPublish(EXCHANGE_NAME, "",null, msg.getBytes("UTF-8"));
            System.out.println(" [x] sent "+msg);
        }
    }
}
