package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class CategoryMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryMaster.class);
        CategoryMaster categoryMaster1 = new CategoryMaster();
        categoryMaster1.setId(1L);
        CategoryMaster categoryMaster2 = new CategoryMaster();
        categoryMaster2.setId(categoryMaster1.getId());
        assertThat(categoryMaster1).isEqualTo(categoryMaster2);
        categoryMaster2.setId(2L);
        assertThat(categoryMaster1).isNotEqualTo(categoryMaster2);
        categoryMaster1.setId(null);
        assertThat(categoryMaster1).isNotEqualTo(categoryMaster2);
    }
}
