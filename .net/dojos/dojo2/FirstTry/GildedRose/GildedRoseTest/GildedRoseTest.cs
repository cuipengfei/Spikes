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
            program.UpdateQuality();

            //then
            Assert.AreEqual(Vest(program).Quality,19);
        }

        private Item Vest(Program program)
        {
            return program.Items.FirstOrDefault(i=>i.Name== "+5 Dexterity Vest");
        }
    }
}
