using System;
using System.Linq;
using GildedRose;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GildedRoseTest
{
    [TestClass]
    public class GildedRoseTest
    {
        [TestMethod]
        public void NormalItemsShouldDecreaseOneEachDay()
        {
            //given
            Program program=Program.InitApp();

            //when
            PassNDays(program,1);

            //then
            Assert.AreEqual(Vest(program).Quality,19);

            //when
            PassNDays(program,5);

            //then
            Assert.AreEqual(Vest(program).Quality,14);
        }

        [TestMethod]
        public void NormalItemsShouldDecreaseTwoEachDayAfterSellInDays()
        {
            //given
            Program program=Program.InitApp();

            //when
            PassNDays(program,10);

            //then
            Assert.AreEqual(Vest(program).Quality,10);

            //when
            PassNDays(program,1);

            //then
            Assert.AreEqual(Vest(program).Quality,8);
        }

        private static void PassNDays(Program program,int n)
        {
            for (int i = 0; i < n; i++)
            {
                program.UpdateQuality();
            }
        }

        private Item Vest(Program program)
        {
            return program.Items.FirstOrDefault(i=>i.Name== "+5 Dexterity Vest");
        }
    }
}
