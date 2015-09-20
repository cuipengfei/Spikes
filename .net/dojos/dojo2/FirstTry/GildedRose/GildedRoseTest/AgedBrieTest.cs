using System;
using System.Linq;
using GildedRose;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GildedRoseTest
{
    [TestClass]
    public class AgedBrieTest:TestBase
    {
        [TestMethod]
        public void AgedBrieShouldIncreaseWhenSellInDaysPass()
        {
            //given
            var program = GetProgram();

            //when pass 2 days
            PassNDays(program,2);

            //then increase 2
            Assert.AreEqual(2,AgedBrie(program).Quality);
            Assert.AreEqual(0,AgedBrie(program).SellIn);

            //when pass 5 more days
            PassNDays(program,5);

            //then increase 5 more
            Assert.AreEqual(12,AgedBrie(program).Quality);
        }

        [TestMethod]
        public void AgedBrieShouldNotIncreaseToMoreThan50()
        {
            //given
            var program = GetProgram();

            //when pass 26 days
            PassNDays(program,26);

            //then increase 50
            Assert.AreEqual(50,AgedBrie(program).Quality);

            //when pass 5 more days
            PassNDays(program,5);

            //then increase no more
            Assert.AreEqual(50,AgedBrie(program).Quality);
        }
    }
}
