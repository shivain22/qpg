package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class SubCategoryMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubCategoryMaster.class);
        SubCategoryMaster subCategoryMaster1 = new SubCategoryMaster();
        subCategoryMaster1.setId(1L);
        SubCategoryMaster subCategoryMaster2 = new SubCategoryMaster();
        subCategoryMaster2.setId(subCategoryMaster1.getId());
        assertThat(subCategoryMaster1).isEqualTo(subCategoryMaster2);
        subCategoryMaster2.setId(2L);
        assertThat(subCategoryMaster1).isNotEqualTo(subCategoryMaster2);
        subCategoryMaster1.setId(null);
        assertThat(subCategoryMaster1).isNotEqualTo(subCategoryMaster2);
    }
}
