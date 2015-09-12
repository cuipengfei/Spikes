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
    }
}
