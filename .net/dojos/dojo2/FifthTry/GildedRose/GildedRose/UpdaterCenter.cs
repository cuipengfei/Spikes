using System.Collections.Generic;
using System.Linq;
using GildedRose;

static internal class UpdaterCenter
{
    private static readonly List<ItemUpdater> Updaters = new List<ItemUpdater>
    {
        new RegularItemUpdater(),
        new TimeLimitedItemUpdater(),
        new ValueAddingItemUpdater()
    };

    internal static ItemUpdater GetUpdater(Item item)
    {
        return Updaters.FirstOrDefault(updater => updater.CanUpdate(item));
    }
}