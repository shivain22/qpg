"use strict";
exports.__esModule = true;
exports.QuestionMaster = void 0;
var QuestionMaster = /** @class */ (function () {
    function QuestionMaster(id, text, weightage, questionTypeMaster, difficultyTypeMaster, subTopicMaster, parentQuestionMaster, questionMasters, answerMasters) {
        this.id = id;
        this.text = text;
        this.weightage = weightage;
        this.questionTypeMaster = questionTypeMaster;
        this.difficultyTypeMaster = difficultyTypeMaster;
        this.subTopicMaster = subTopicMaster;
        this.parentQuestionMaster = parentQuestionMaster;
        this.questionMasters = questionMasters;
        this.answerMasters = answerMasters;
    }
    return QuestionMaster;
}());
exports.QuestionMaster = QuestionMaster;
//# sourceMappingURL=question-master.model.js.map