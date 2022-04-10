package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class QuestionMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionMaster.class);
        QuestionMaster questionMaster1 = new QuestionMaster();
        questionMaster1.setId(1L);
        QuestionMaster questionMaster2 = new QuestionMaster();
        questionMaster2.setId(questionMaster1.getId());
        assertThat(questionMaster1).isEqualTo(questionMaster2);
        questionMaster2.setId(2L);
        assertThat(questionMaster1).isNotEqualTo(questionMaster2);
        questionMaster1.setId(null);
        assertThat(questionMaster1).isNotEqualTo(questionMaster2);
    }
}
