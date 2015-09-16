using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LiveDemo;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace LiveDemoTest
{
    [TestClass]
    public class LastFrameTest
    {
        [TestMethod]
        public void LastFrameShouldSimplyAddUpAllItsBalls()
        {
            //given
            var lastFrame=new LastFrame(1,9,2);

            //when
            int score= lastFrame.Score;

            //then
            Assert.AreEqual(12,score);
        }
    }
}