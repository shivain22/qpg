package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class CourseMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseMaster.class);
        CourseMaster courseMaster1 = new CourseMaster();
        courseMaster1.setId(1L);
        CourseMaster courseMaster2 = new CourseMaster();
        courseMaster2.setId(courseMaster1.getId());
        assertThat(courseMaster1).isEqualTo(courseMaster2);
        courseMaster2.setId(2L);
        assertThat(courseMaster1).isNotEqualTo(courseMaster2);
        courseMaster1.setId(null);
        assertThat(courseMaster1).isNotEqualTo(courseMaster2);
    }
}
