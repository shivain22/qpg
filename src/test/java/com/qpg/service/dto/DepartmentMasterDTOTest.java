package com.qpg.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class DepartmentMasterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentMasterDTO.class);
        DepartmentMasterDTO departmentMasterDTO1 = new DepartmentMasterDTO();
        departmentMasterDTO1.setId(1L);
        DepartmentMasterDTO departmentMasterDTO2 = new DepartmentMasterDTO();
        assertThat(departmentMasterDTO1).isNotEqualTo(departmentMasterDTO2);
        departmentMasterDTO2.setId(departmentMasterDTO1.getId());
        assertThat(departmentMasterDTO1).isEqualTo(departmentMasterDTO2);
        departmentMasterDTO2.setId(2L);
        assertThat(departmentMasterDTO1).isNotEqualTo(departmentMasterDTO2);
        departmentMasterDTO1.setId(null);
        assertThat(departmentMasterDTO1).isNotEqualTo(departmentMasterDTO2);
    }
}
