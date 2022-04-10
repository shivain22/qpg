package com.qpg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.qpg.web.rest.TestUtil;

public class QuestionTypeMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionTypeMaster.class);
        QuestionTypeMaster questionTypeMaster1 = new QuestionTypeMaster();
        questionTypeMaster1.setId(1L);
        QuestionTypeMaster questionTypeMaster2 = new QuestionTypeMaster();
        questionTypeMaster2.setId(questionTypeMaster1.getId());
        assertThat(questionTypeMaster1).isEqualTo(questionTypeMaster2);
        questionTypeMaster2.setId(2L);
        assertThat(questionTypeMaster1).isNotEqualTo(questionTypeMaster2);
        questionTypeMaster1.setId(null);
        assertThat(questionTypeMaster1).isNotEqualTo(questionTypeMaster2);
    }
}
