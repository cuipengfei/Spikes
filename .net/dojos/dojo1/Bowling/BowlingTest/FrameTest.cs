using System;
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
            Frame frame=new Frame() {FirstTry=3,SecondTry=4};

            //when
            int score = frame.Score;

            //then
            Assert.AreEqual(7,score);
        }

        [TestMethod]
        public void FrameShouldCountNextTwoBallsAsScoreWhenThereIsStrike()
        {
            //given
            Frame frame1 = new Frame() {FirstTry = 10,SecondTry = 0};
            Frame frame2 = new Frame() {FirstTry = 5,SecondTry = 2};
            frame1.Next = frame2;

            //when
            int score1=frame1.Score;

            //then
            Assert.AreEqual(17,score1);
        }
        
    }
}
