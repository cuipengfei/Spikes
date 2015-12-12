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

    @Test
    public void shouldCountScoreWithSpareBonus() throws Exception {
        //given
        Frame spareFrame = new Frame(3, 7);
        Frame nextFrame = new Frame(5, 4);
        spareFrame.setNext(nextFrame);

        //when
        int score = spareFrame.countScore();

        //then
        assertThat(score, is(15));
    }

    @Test
    public void shouldCountScoreWithStrikeBonus() throws Exception {
        //given
        Frame strikeFrame = new Frame(10, 0);
        Frame nextFrame = new Frame(5, 4);
        strikeFrame.setNext(nextFrame);

        //when
        int score = strikeFrame.countScore();

        //then
        assertThat(score, is(19));
    }

    @Test
    public void shouldCountScoreWith2StrikesBonus() throws Exception {
        //given
        Frame strikeFrame = new Frame(10, 0);
        Frame nextStrikeFrame = new Frame(10, 0);
        Frame yetNextFrame = new Frame(5, 4);
        strikeFrame.setNext(nextStrikeFrame);
        nextStrikeFrame.setNext(yetNextFrame);

        //when
        int score = strikeFrame.countScore();

        //then
        assertThat(score, is(25));
    }

    @Test
    public void shouldCountScoreWith2StrikesBonusWhenSecondStrikeHasNoNext() throws Exception {
        //given
        Frame strikeFrame = new Frame(10, 0);
        Frame nextStrikeFrame = new Frame(10, 0);
        strikeFrame.setNext(nextStrikeFrame);

        //when
        int score = strikeFrame.countScore();

        //then
        assertThat(score, is(20));
    }
}
