package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class AnswerMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerMaster.class);
        AnswerMaster answerMaster1 = new AnswerMaster();
        answerMaster1.setId(1L);
        AnswerMaster answerMaster2 = new AnswerMaster();
        answerMaster2.setId(answerMaster1.getId());
        assertThat(answerMaster1).isEqualTo(answerMaster2);
        answerMaster2.setId(2L);
        assertThat(answerMaster1).isNotEqualTo(answerMaster2);
        answerMaster1.setId(null);
        assertThat(answerMaster1).isNotEqualTo(answerMaster2);
    }
}
