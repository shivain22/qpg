package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class TopicMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicMaster.class);
        TopicMaster topicMaster1 = new TopicMaster();
        topicMaster1.setId(1L);
        TopicMaster topicMaster2 = new TopicMaster();
        topicMaster2.setId(topicMaster1.getId());
        assertThat(topicMaster1).isEqualTo(topicMaster2);
        topicMaster2.setId(2L);
        assertThat(topicMaster1).isNotEqualTo(topicMaster2);
        topicMaster1.setId(null);
        assertThat(topicMaster1).isNotEqualTo(topicMaster2);
    }
}
