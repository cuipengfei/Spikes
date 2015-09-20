using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GildedRoseTest
{
    [TestClass]
    public class ConjuredItemTest : TestBase
    {
        [TestMethod]
        public void ConjuredItemShouldDoubleDecrease()
        {
            //given
            var program = GetProgram();

            //when pass 1 days
            PassNDays(program, 1);

            //then decrease 2
            Assert.AreEqual(4, ManaCake(program).Quality);
        }
    }
}