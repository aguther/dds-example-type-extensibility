/*
 * Copyright (c) 2016 Andreas Guther - Refer to protection notice ISO 16016
 */

package net.guther.dds.type.example.typeC;

import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.domain.DomainParticipantQos;
import com.rti.dds.infrastructure.InstanceHandle_t;
import com.rti.dds.infrastructure.RETCODE_ERROR;
import com.rti.dds.subscription.DataReader;
import com.rti.dds.subscription.DataReaderQos;
import com.rti.dds.subscription.SampleInfo;
import com.rti.dds.topic.Topic;
import com.rti.dds.topic.TopicQos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        DataReaderQos drQos = new DataReaderQos();
        dp.get_default_datareader_qos(drQos);

        log.info("Create DataWriter");
        DataReader dr = dp.create_datareader(topic, drQos, null, 0);

        log.info("Adding shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shouldTerminate = true;
            }
        });

        log.info("Starting infinite loop to read samples");
        while (!shouldTerminate) {

            try {
                // create sample and info
                // (hint: in the general case do not allocate within loop)
                Type sample = new Type();
                SampleInfo sampleInfo = new SampleInfo();

                // try to get next sample
                dr.take_next_sample_untyped(sample, sampleInfo);

                // check if data is valid and print the sample
                if (sampleInfo.valid_data) {
                    log.info("Sample received: {}", sample.toString().replace('\n', ' '));
                }

            } catch (RETCODE_ERROR ex) {
                log.debug(ex.toString());
            }

            Thread.sleep(100);
        }
    }
}
