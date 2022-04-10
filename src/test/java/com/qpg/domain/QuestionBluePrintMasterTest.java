package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class QuestionBluePrintMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionBluePrintMaster.class);
        QuestionBluePrintMaster questionBluePrintMaster1 = new QuestionBluePrintMaster();
        questionBluePrintMaster1.setId(1L);
        QuestionBluePrintMaster questionBluePrintMaster2 = new QuestionBluePrintMaster();
        questionBluePrintMaster2.setId(questionBluePrintMaster1.getId());
        assertThat(questionBluePrintMaster1).isEqualTo(questionBluePrintMaster2);
        questionBluePrintMaster2.setId(2L);
        assertThat(questionBluePrintMaster1).isNotEqualTo(questionBluePrintMaster2);
        questionBluePrintMaster1.setId(null);
        assertThat(questionBluePrintMaster1).isNotEqualTo(questionBluePrintMaster2);
    }
}
