package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class CollegeMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollegeMaster.class);
        CollegeMaster collegeMaster1 = new CollegeMaster();
        collegeMaster1.setId(1L);
        CollegeMaster collegeMaster2 = new CollegeMaster();
        collegeMaster2.setId(collegeMaster1.getId());
        assertThat(collegeMaster1).isEqualTo(collegeMaster2);
        collegeMaster2.setId(2L);
        assertThat(collegeMaster1).isNotEqualTo(collegeMaster2);
        collegeMaster1.setId(null);
        assertThat(collegeMaster1).isNotEqualTo(collegeMaster2);
    }
}
