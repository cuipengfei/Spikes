﻿using System;
using System.Collections.Generic;
using System.Linq;

namespace GildedRose
{
    public class Program
    {
        public IList<Item> Items;

        private static void Main(string[] args)
        {
            Console.WriteLine("OMGHAI!");

            var app = InitApp();

            app.UpdateQuality();

            Console.ReadKey();
        }

        public static Program InitApp()
        {
            var app = new Program
            {
                Items = new List<Item>
                {
                    new Item {Name = "+5 Dexterity Vest", SellIn = 10, Quality = 20},
                    new Item {Name = "Aged Brie", SellIn = 2, Quality = 0},
                    new Item {Name = "Elixir of the Mongoose", SellIn = 5, Quality = 7},
                    new Item {Name = "Sulfuras, Hand of Ragnaros", SellIn = 0, Quality = 80},
                    new Item
                    {
                        Name = "Backstage passes to a TAFKAL80ETC concert",
                        SellIn = 15,
                        Quality = 20
                    },
                    new Item {Name = "Conjured Mana Cake", SellIn = 3, Quality = 6}
                }
            };
            return app;
        }

        public void UpdateQuality()
        {
            foreach (var item in Items.Where(IsNotLegendaryItem))
            {
                PassOneDay(item);
                Update(item);
            }
        }

        private static void Update(Item item)
        {
            var regularUpdater = UpdaterCenter.GetUpdater(item);
            if (regularUpdater != null)
            {
                regularUpdater.Update(item);
            }
        }

        private static bool IsNotLegendaryItem(Item item)
        {
            return item.Name != "Sulfuras, Hand of Ragnaros";
        }

        private static void PassOneDay(Item item)
        {
            item.SellIn = item.SellIn - 1;
        }
    }

    public class Item
    {
        public string Name { get; set; }

        public int SellIn { get; set; }

        public int Quality { get; set; }
    }
}