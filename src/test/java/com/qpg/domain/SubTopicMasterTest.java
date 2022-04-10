package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class SubTopicMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubTopicMaster.class);
        SubTopicMaster subTopicMaster1 = new SubTopicMaster();
        subTopicMaster1.setId(1L);
        SubTopicMaster subTopicMaster2 = new SubTopicMaster();
        subTopicMaster2.setId(subTopicMaster1.getId());
        assertThat(subTopicMaster1).isEqualTo(subTopicMaster2);
        subTopicMaster2.setId(2L);
        assertThat(subTopicMaster1).isNotEqualTo(subTopicMaster2);
        subTopicMaster1.setId(null);
        assertThat(subTopicMaster1).isNotEqualTo(subTopicMaster2);
    }
}
