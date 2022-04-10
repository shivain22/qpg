package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class QbMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QbMaster.class);
        QbMaster qbMaster1 = new QbMaster();
        qbMaster1.setId(1L);
        QbMaster qbMaster2 = new QbMaster();
        qbMaster2.setId(qbMaster1.getId());
        assertThat(qbMaster1).isEqualTo(qbMaster2);
        qbMaster2.setId(2L);
        assertThat(qbMaster1).isNotEqualTo(qbMaster2);
        qbMaster1.setId(null);
        assertThat(qbMaster1).isNotEqualTo(qbMaster2);
    }
}
