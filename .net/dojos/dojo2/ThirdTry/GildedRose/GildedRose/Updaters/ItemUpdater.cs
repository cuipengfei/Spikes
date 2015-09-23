namespace GildedRose.Updaters
{
    public interface ITemUpdater
    {
        bool CanUpdate(Item item);

        void Update(Item item);
    }
}