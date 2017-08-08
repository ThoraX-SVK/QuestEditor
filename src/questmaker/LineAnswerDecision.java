package questmaker;

/**
 *
 * @author Tom
 */
public class LineAnswerDecision {

    AnswerOutput ao;
    DecisionInput di;
    QuestOutput qo;
    QuestInput qi;

    public LineAnswerDecision(AnswerOutput ao, DecisionInput di) {
        this.ao = ao;
        this.di = di;
    }

    public LineAnswerDecision(AnswerOutput ao, QuestOutput qo) {
        this.ao = ao;
        this.qo = qo;
    }

    public LineAnswerDecision(DecisionInput di, QuestInput qi) {
        this.di = di;
        this.qi = qi;
    }

    public void delete() {

        if (qo != null) {
            qo.fromAnswerOutput = null;
        }
        if (ao != null) {
            ao.goingToDecision = null;
        }

        this.qo = null;
        this.qi = null;
        this.ao = null;
        this.di = null;
    }

}
