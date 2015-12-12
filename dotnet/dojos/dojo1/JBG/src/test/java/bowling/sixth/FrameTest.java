package bowling.sixth;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FrameTest {
    @Test
    public void shouldCountItsOwnScoreWithoutBonus() throws Exception {
        //given
        Frame noBonusFrame = new Frame(3, 4);

        //when
        int score = noBonusFrame.countScore();

        //then
        assertThat(score, is(7));
    }
}
