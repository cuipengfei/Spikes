using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using ThirdTry;

namespace ThirdTryTest
{
    [TestClass]
    public class LastFrameTest
    {
        [TestMethod]
        public void LastFrameShouldSimplyCountItsScoreAsThreeBallsSum()
        {
            //given
            var lastFrame = new LastFrame(1,2,3);

            //when
            var score = lastFrame.Score;

            //then
            Assert.AreEqual(6,score);
        }
    }
}
