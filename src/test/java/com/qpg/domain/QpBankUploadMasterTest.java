package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class QpBankUploadMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QpBankUploadMaster.class);
        QpBankUploadMaster qpBankUploadMaster1 = new QpBankUploadMaster();
        qpBankUploadMaster1.setId(1L);
        QpBankUploadMaster qpBankUploadMaster2 = new QpBankUploadMaster();
        qpBankUploadMaster2.setId(qpBankUploadMaster1.getId());
        assertThat(qpBankUploadMaster1).isEqualTo(qpBankUploadMaster2);
        qpBankUploadMaster2.setId(2L);
        assertThat(qpBankUploadMaster1).isNotEqualTo(qpBankUploadMaster2);
        qpBankUploadMaster1.setId(null);
        assertThat(qpBankUploadMaster1).isNotEqualTo(qpBankUploadMaster2);
    }
}
