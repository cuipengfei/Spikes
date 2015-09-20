using System.Linq;
using GildedRose;

namespace GildedRoseTest
{
    public class TestBase
    {
        protected static void PassNDays(Program program, int n)
        {
            for (var i = 0; i < n; i++)
            {
                program.UpdateQuality();
            }
        }

        protected static Program GetProgram()
        {
            var program = Program.InitApp();
            return program;
        }

        protected Item Vest(Program program)
        {
            return FindItem(program, "+5 Dexterity Vest");
        }

        protected Item AgedBrie(Program program)
        {
            return FindItem(program, "Aged Brie");
        }

        protected Item BackStagePass(Program program)
        {
            return FindItem(program, "Backstage passes to a TAFKAL80ETC concert");
        }

        private static Item FindItem(Program program, string itemName)
        {
            return program.Items.FirstOrDefault(i => i.Name == itemName);
        }

        protected Item Sulfras(Program program)
        {
            return FindItem(program, "Sulfuras, Hand of Ragnaros");
        }

        protected Item ManaCake(Program program)
        {
            return FindItem(program, "Conjured Mana Cake");
        }
    }
}