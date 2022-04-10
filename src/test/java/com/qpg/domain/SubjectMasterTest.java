package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class SubjectMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubjectMaster.class);
        SubjectMaster subjectMaster1 = new SubjectMaster();
        subjectMaster1.setId(1L);
        SubjectMaster subjectMaster2 = new SubjectMaster();
        subjectMaster2.setId(subjectMaster1.getId());
        assertThat(subjectMaster1).isEqualTo(subjectMaster2);
        subjectMaster2.setId(2L);
        assertThat(subjectMaster1).isNotEqualTo(subjectMaster2);
        subjectMaster1.setId(null);
        assertThat(subjectMaster1).isNotEqualTo(subjectMaster2);
    }
}
