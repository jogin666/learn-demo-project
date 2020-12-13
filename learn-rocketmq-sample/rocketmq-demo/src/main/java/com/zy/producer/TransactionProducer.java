package com.zy.producer;

import com.zy.RocketMQConfig;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionProducer {

    public static void main(String[] args) {
        try {
            sendTransactionMessage();
        } catch (Exception e) {

        }
    }

    static void sendTransactionMessage() throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("transactionProducerGroup");
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2000), r -> {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");
            return thread;
        });
        producer.setNamesrvAddr(RocketMQConfig.ROCKETMQ_IP);
        producer.setVipChannelEnabled(false);
        producer.setExecutorService(executorService);
        producer.setTransactionListener(new TransactionListenerImpl());
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {
                Message msg = new Message("TopicTest1234", tags[i % tags.length], "KEY" + i,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                TransactionSendResult transactionSendResult = producer.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", transactionSendResult);
                Thread.sleep(10);
            } catch (MQClientException | UnsupportedEncodingException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        Thread.sleep(120 * 1000);
        producer.shutdown();
    }

    static class TransactionListenerImpl implements TransactionListener {
        private AtomicInteger transactionIndex = new AtomicInteger(0);
        private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

        /**
         * 回调本地事务，返回事务的状态
         *
         * @param message
         * @param o
         * @return
         */
        @Override
        public LocalTransactionState executeLocalTransaction(Message message, Object o) {
            int value = transactionIndex.getAndIncrement();
            int status = value % 3;
            localTrans.put(message.getTransactionId(), status);
            return LocalTransactionState.UNKNOW;
        }

        /**
         * 回调检查本地事务的状态
         *
         * @param messageExt
         * @return
         */
        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
            Integer status = localTrans.get(messageExt.getTransactionId());
            if (status != null) {
                switch (status) {
                    case 0:
                        return LocalTransactionState.UNKNOW;
                    case 1:
                        return LocalTransactionState.COMMIT_MESSAGE;
                    case 2:
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
            return LocalTransactionState.COMMIT_MESSAGE;
        }
    }
}
