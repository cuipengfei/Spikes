using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using ThirdTry;

namespace ThirdTryTest
{
    [TestClass]
    public class FrameTest
    {
        [TestMethod]
        public void FrameShouldCountItsOwnScoreWithoutBonus()
        {
            //given
            var frame = new Frame(1,2);

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(3,score);
        }
    }
}
