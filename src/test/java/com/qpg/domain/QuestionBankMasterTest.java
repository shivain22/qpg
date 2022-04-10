package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class QuestionBankMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionBankMaster.class);
        QuestionBankMaster questionBankMaster1 = new QuestionBankMaster();
        questionBankMaster1.setId(1L);
        QuestionBankMaster questionBankMaster2 = new QuestionBankMaster();
        questionBankMaster2.setId(questionBankMaster1.getId());
        assertThat(questionBankMaster1).isEqualTo(questionBankMaster2);
        questionBankMaster2.setId(2L);
        assertThat(questionBankMaster1).isNotEqualTo(questionBankMaster2);
        questionBankMaster1.setId(null);
        assertThat(questionBankMaster1).isNotEqualTo(questionBankMaster2);
    }
}
