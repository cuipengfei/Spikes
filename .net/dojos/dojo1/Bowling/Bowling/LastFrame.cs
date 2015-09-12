namespace Bowling
{
    public class LastFrame : Frame
    {
        public int ThirdTry { get; set; }

        protected override int CalculateScore()
        {
            return FirstTry + SecondTry + ThirdTry;
        }
    }
}