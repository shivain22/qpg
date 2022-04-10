package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class QuestionBluePrintDetailTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionBluePrintDetail.class);
        QuestionBluePrintDetail questionBluePrintDetail1 = new QuestionBluePrintDetail();
        questionBluePrintDetail1.setId(1L);
        QuestionBluePrintDetail questionBluePrintDetail2 = new QuestionBluePrintDetail();
        questionBluePrintDetail2.setId(questionBluePrintDetail1.getId());
        assertThat(questionBluePrintDetail1).isEqualTo(questionBluePrintDetail2);
        questionBluePrintDetail2.setId(2L);
        assertThat(questionBluePrintDetail1).isNotEqualTo(questionBluePrintDetail2);
        questionBluePrintDetail1.setId(null);
        assertThat(questionBluePrintDetail1).isNotEqualTo(questionBluePrintDetail2);
    }
}
