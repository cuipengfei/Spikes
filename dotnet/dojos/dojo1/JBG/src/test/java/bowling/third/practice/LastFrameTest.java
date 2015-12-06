package bowling.third.practice;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LastFrameTest {
    @Test
    public void shouldOnlyCountScoreOfItsOwnRolls() throws Exception {
        //given
        LastFrame frame = new LastFrame(1, 2, 3);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(6));
    }
}
