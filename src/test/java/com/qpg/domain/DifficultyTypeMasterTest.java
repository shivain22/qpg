package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class DifficultyTypeMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DifficultyTypeMaster.class);
        DifficultyTypeMaster difficultyTypeMaster1 = new DifficultyTypeMaster();
        difficultyTypeMaster1.setId(1L);
        DifficultyTypeMaster difficultyTypeMaster2 = new DifficultyTypeMaster();
        difficultyTypeMaster2.setId(difficultyTypeMaster1.getId());
        assertThat(difficultyTypeMaster1).isEqualTo(difficultyTypeMaster2);
        difficultyTypeMaster2.setId(2L);
        assertThat(difficultyTypeMaster1).isNotEqualTo(difficultyTypeMaster2);
        difficultyTypeMaster1.setId(null);
        assertThat(difficultyTypeMaster1).isNotEqualTo(difficultyTypeMaster2);
    }
}
