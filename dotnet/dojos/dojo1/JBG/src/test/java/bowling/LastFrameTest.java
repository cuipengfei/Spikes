package bowling;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LastFrameTest {
    @Test
    public void shouldOnlyCountTwoRollsWhenThirdNotPresent() {
        //given
        LastFrame lastFrame = new LastFrame(3, 2, 0);

        //when
        int score = lastFrame.countScore();

        //then
        assertThat(score, is(5));
    }

    @Test
    public void shouldOnlyCountOwnThreeRolls() {
        //given
        LastFrame lastFrame = new LastFrame(1, 9, 3);

        //when
        int score = lastFrame.countScore();

        //then
        assertThat(score, is(13));
    }

    @Test
    public void shouldNotHaveBonusWhenStrike() {
        //given
        LastFrame lastFrame = new LastFrame(10, 8, 3);

        //when
        int score = lastFrame.countScore();

        //then
        assertThat(score, is(21));
    }

}
