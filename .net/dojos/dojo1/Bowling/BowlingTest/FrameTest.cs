using Bowling;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BowlingTest
{
    [TestClass]
    public class FrameTest
    {
        [TestMethod]
        public void FrameShouldCountItsOwnScore()
        {
            //given
            var frame = new Frame {FirstTry = 3, SecondTry = 4};

            //when
            var score = frame.Score;

            //then
            Assert.AreEqual(7, score);
        }

        [TestMethod]
        public void FrameShouldCountNextTwoBallsAsScoreWhenThereIsStrike()
        {
            //given
            var frame1 = new Frame {FirstTry = 10, SecondTry = 0};
            var frame2 = new Frame {FirstTry = 5, SecondTry = 2};
            frame1.Next = frame2;

            //when
            var score1 = frame1.Score;

            //then
            Assert.AreEqual(17, score1);
        }

        [TestMethod]
        public void FrameShouldCountNextBallAsScoreWhenThereIsSpare()
        {
            //given
            var frame1 = new Frame {FirstTry = 4, SecondTry = 6};
            var frame2 = new Frame {FirstTry = 5, SecondTry = 2};
            frame1.Next = frame2;

            //when
            var score1 = frame1.Score;

            //then
            Assert.AreEqual(15, score1);
        }

        [TestMethod]
        public void ThreeStrikesShouldScore30()
        {
            //given
            var frame1 = new Frame {FirstTry = 10, SecondTry = 0};
            var frame2 = new Frame {FirstTry = 10, SecondTry = 0};
            var frame3 = new Frame {FirstTry = 10, SecondTry = 0};
            frame1.Next = frame2;
            frame2.Next = frame3;

            //when
            var score1 = frame1.Score;

            //then
            Assert.AreEqual(30, score1);
        }
    }
}