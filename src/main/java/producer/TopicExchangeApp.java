package producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class TopicExchangeApp {
    private static final String EXCHANGE_NAME = "topic";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String msg = "this is example by topic";
            String topicKey = "Rost2";

            channel.basicPublish(EXCHANGE_NAME, topicKey, null, msg.getBytes("UTF-8"));
            System.out.println(" [x] sent "+topicKey+" : "+msg);
        }
    }
}
