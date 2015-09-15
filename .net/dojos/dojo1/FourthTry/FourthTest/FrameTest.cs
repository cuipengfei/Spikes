using System;
using FourthTry;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace FourthTest
{
    [TestClass]
    public class FrameTest
    {
        [TestMethod]
        public void FrameShouldCountScoreWhenThereIsNoBonus()
        {
            //given
            var frame = new Frame(2, 3);

            //when
            var score = frame.Score;

            //then
            Assert.AreEqual(5, score);
        }

        [TestMethod]
        public void FrameShouldIncludeNextOneBallAsBonusWhenThereIsSpare()
        {
            //given
            var frame = new Frame(2, 8);
            var frame2 = new Frame(3, 4);
            frame.Next = frame2;

            //when
            var score = frame.Score;

            //then
            Assert.AreEqual(13, score);
        }

        [TestMethod]
        public void FrameShouldIncludeNextTwoBallsAsBonusWhenThereIsStrike()
        {
            //given
            var frame = new Frame(10, 0);
            var frame2 = new Frame(3, 4);
            frame.Next = frame2;

            //when
            var score = frame.Score;

            //then
            Assert.AreEqual(17, score);
        }

        [TestMethod]
        public void FrameShouldIncludeNextTwoBallsAsBonusWhenThereAre2StrikesInARow()
        {
            //given
            var frame = new Frame(10, 0);
            var frame2 = new Frame(10, 0);
            var frame3 = new Frame(3, 4);
            frame.Next = frame2;
            frame2.Next = frame3;

            //when
            var score = frame.Score;

            //then
            Assert.AreEqual(23, score);
        }

        [TestMethod]
        public void FrameShouldIncludeNextTwoBallsAsBonusWhenNextToStrikeIsLastFrame()
        {
            //given
            var frame = new Frame(10, 0);
            var frame2 = new LastFrame(10, 3, 4);
            frame.Next = frame2;

            //when
            var score = frame.Score;

            //then
            Assert.AreEqual(23, score);
        }

        [TestMethod]
        [ExpectedException(typeof (ArgumentOutOfRangeException))]
        public void FrameShouldNotHaveAnyBallBiggerThan10()
        {
            var frame = new Frame(11, 12);
            var score = frame.Score;
        }

        [TestMethod]
        [ExpectedException(typeof (ArgumentException))]
        public void FrameShouldNotHaveTwoBallsSumBiggerThan10()
        {
            var frame = new Frame(7, 5);
            var score = frame.Score;
        }
    }
}