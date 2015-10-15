namespace BowlingSecondPractice
{
    public class LastFrame : Frame
    {
        public LastFrame(int firstBall, int secondBall, int thirdBall) : base(firstBall, secondBall)
        {
            ThirdBall = thirdBall;
        }

        public int ThirdBall { get; }

        protected override int CalculateScore()
        {
            return FirstBall + SecondBall + ThirdBall;
        }
    }
}