package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class SubSubjectMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubSubjectMaster.class);
        SubSubjectMaster subSubjectMaster1 = new SubSubjectMaster();
        subSubjectMaster1.setId(1L);
        SubSubjectMaster subSubjectMaster2 = new SubSubjectMaster();
        subSubjectMaster2.setId(subSubjectMaster1.getId());
        assertThat(subSubjectMaster1).isEqualTo(subSubjectMaster2);
        subSubjectMaster2.setId(2L);
        assertThat(subSubjectMaster1).isNotEqualTo(subSubjectMaster2);
        subSubjectMaster1.setId(null);
        assertThat(subSubjectMaster1).isNotEqualTo(subSubjectMaster2);
    }
}
