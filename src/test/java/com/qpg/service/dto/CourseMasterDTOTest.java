package com.qpg.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class CourseMasterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseMasterDTO.class);
        CourseMasterDTO courseMasterDTO1 = new CourseMasterDTO();
        courseMasterDTO1.setId(1L);
        CourseMasterDTO courseMasterDTO2 = new CourseMasterDTO();
        assertThat(courseMasterDTO1).isNotEqualTo(courseMasterDTO2);
        courseMasterDTO2.setId(courseMasterDTO1.getId());
        assertThat(courseMasterDTO1).isEqualTo(courseMasterDTO2);
        courseMasterDTO2.setId(2L);
        assertThat(courseMasterDTO1).isNotEqualTo(courseMasterDTO2);
        courseMasterDTO1.setId(null);
        assertThat(courseMasterDTO1).isNotEqualTo(courseMasterDTO2);
    }
}
