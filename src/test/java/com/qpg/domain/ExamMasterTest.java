package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class ExamMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamMaster.class);
        ExamMaster examMaster1 = new ExamMaster();
        examMaster1.setId(1L);
        ExamMaster examMaster2 = new ExamMaster();
        examMaster2.setId(examMaster1.getId());
        assertThat(examMaster1).isEqualTo(examMaster2);
        examMaster2.setId(2L);
        assertThat(examMaster1).isNotEqualTo(examMaster2);
        examMaster1.setId(null);
        assertThat(examMaster1).isNotEqualTo(examMaster2);
    }
}
