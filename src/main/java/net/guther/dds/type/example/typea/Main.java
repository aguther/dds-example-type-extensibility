/*
 * Copyright (c) 2016 Andreas Guther - Refer to protection notice ISO 16016
 */

package net.guther.dds.type.example.typea;

import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.domain.DomainParticipantQos;
import com.rti.dds.infrastructure.InstanceHandle_t;
import com.rti.dds.publication.DataWriter;
import com.rti.dds.publication.DataWriterQos;
import com.rti.dds.topic.Topic;
import com.rti.dds.topic.TopicQos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Dds.Example.Type.PayloadA;
import Dds.Example.Type.Type;
import Dds.Example.Type.TypeTypeSupport;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static boolean shouldTerminate = false;

    private Main() {
    }

    public static void main(String[] args) throws InterruptedException {

        log.info("Get default domain participant QoS");
        DomainParticipantQos dpQos = new DomainParticipantQos();
        DomainParticipantFactory.get_instance().get_default_participant_qos(dpQos);

        log.info("Create DomainParticipant");
        DomainParticipant dp = DomainParticipantFactory.get_instance().create_participant(0, dpQos, null, 0);

        log.info("Register type");
        TypeTypeSupport.get_instance().register_typeI(dp, TypeTypeSupport.get_type_name());

        log.info("Get default topic QoS");
        TopicQos topicQos = new TopicQos();
        dp.get_default_topic_qos(topicQos);

        log.info("Create topic");
        Topic topic = dp.create_topic("TopicType", TypeTypeSupport.get_type_name(), topicQos, null, 0);

        log.info("Get default data writer QoS");
        DataWriterQos dwQos = new DataWriterQos();
        dp.get_default_datawriter_qos(dwQos);

        log.info("Create DataWriter");
        DataWriter dw = dp.create_datawriter(topic, dwQos, null, 0);

        log.info("Adding shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shouldTerminate = true;
            }
        });

        log.info("Starting infinite loop to write samples");
        int counter = 0;
        while (!shouldTerminate) {
            log.info("Writing sample #{}", counter++);

            Type sample = new Type();
            sample.id = 1;
            sample.payloadA = new PayloadA();
            sample.payloadA.myNumber = counter;
            sample.payloadA.myString = "String";

            dw.write_untyped(sample, InstanceHandle_t.HANDLE_NIL);

            Thread.sleep(1000);
        }
    }
}
