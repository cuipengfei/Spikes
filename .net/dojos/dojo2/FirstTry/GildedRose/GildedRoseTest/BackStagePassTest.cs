using System;
using System.Linq;
using GildedRose;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GildedRoseTest
{
    [TestClass]
    public class BackStagePassTest:TestBase
    {
        [TestMethod]
        public void BackStagePassShouldIncreaseByOneBefore10DaysSellIn()
        {
            //given
            var program = GetProgram();

            //when pass 5 days
            PassNDays(program,5);

            //then increase one each day
            Assert.AreEqual(BackStagePass(program).Quality,25);
            Assert.AreEqual(BackStagePass(program).SellIn,10);
        }

        [TestMethod]
        public void BackStagePassShouldIncreaseBy2Before10DaysSellIn()
        {
            //given
            var program = GetProgram();

            //when pass 5 days
            PassNDays(program,5);

            //then 10 days left
            Assert.AreEqual(BackStagePass(program).SellIn,10);

            //when
            PassNDays(program,5);
            Assert.AreEqual(BackStagePass(program).Quality,35);
        }
    }
}
