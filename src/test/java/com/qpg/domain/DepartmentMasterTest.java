package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class DepartmentMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentMaster.class);
        DepartmentMaster departmentMaster1 = new DepartmentMaster();
        departmentMaster1.setId(1L);
        DepartmentMaster departmentMaster2 = new DepartmentMaster();
        departmentMaster2.setId(departmentMaster1.getId());
        assertThat(departmentMaster1).isEqualTo(departmentMaster2);
        departmentMaster2.setId(2L);
        assertThat(departmentMaster1).isNotEqualTo(departmentMaster2);
        departmentMaster1.setId(null);
        assertThat(departmentMaster1).isNotEqualTo(departmentMaster2);
    }
}
