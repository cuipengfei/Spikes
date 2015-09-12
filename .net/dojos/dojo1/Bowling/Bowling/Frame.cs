namespace Bowling
{
    public class Frame
    {
        public int FirstTry { get; set; }
        public int SecondTry { get; set; }

        public int Score => FirstTry + SecondTry;
    }
}