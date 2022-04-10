package com.qpg.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class CollegeMasterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollegeMasterDTO.class);
        CollegeMasterDTO collegeMasterDTO1 = new CollegeMasterDTO();
        collegeMasterDTO1.setId(1L);
        CollegeMasterDTO collegeMasterDTO2 = new CollegeMasterDTO();
        assertThat(collegeMasterDTO1).isNotEqualTo(collegeMasterDTO2);
        collegeMasterDTO2.setId(collegeMasterDTO1.getId());
        assertThat(collegeMasterDTO1).isEqualTo(collegeMasterDTO2);
        collegeMasterDTO2.setId(2L);
        assertThat(collegeMasterDTO1).isNotEqualTo(collegeMasterDTO2);
        collegeMasterDTO1.setId(null);
        assertThat(collegeMasterDTO1).isNotEqualTo(collegeMasterDTO2);
    }
}
